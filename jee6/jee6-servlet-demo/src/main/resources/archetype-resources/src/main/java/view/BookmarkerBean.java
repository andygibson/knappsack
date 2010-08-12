#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.bean.BookmarkDao;
import ${package}.model.Bookmark;
import ${package}.model.Tag;


/**
 * Conversation scoped bean that is used to add a bookmark. The user enters the
 * information and selects the applicable tags from a list. New tags can be
 * added on the fly using Ajax.
 * <p/>
 * This is a conversation scoped bean so the user can go back and forth between
 * the client and the server without having to propagate state or store it in
 * the session and since we are using Ajax, we could do this several times.
 * 
 * 
 * @author Andy Gibson
 * 
 */
@Named
@ConversationScoped
public class BookmarkerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Bookmark to be edited
	 */
	private Bookmark bookmark = new Bookmark();

	/**
	 * List of tags (optionally filtered if the user entered something
	 */
	private List<Tag> tags = null;

	/**
	 * List of available tags wrapped in a JSF list data model (available tags =
	 * tag - selectedTags)
	 */
	private ListDataModel<Tag> availableTagsModel;

	/**
	 * List of tags already assigned wrapped in a JSF list data model
	 * 
	 */
	private ListDataModel<Tag> selectedTagsModel;

	private String tagFilter;

	@Inject
	private BookmarkDao bookmarkDao;

	@Inject
	private UserSession userSession;

	@Inject
	private Conversation conversation;

	/**
	 * Init() method is called when entering the page. We start the conversation
	 * here and check that the user is logged in.
	 */
	public void init() {
		if (conversation.isTransient()) {
			conversation.begin();
		}

		// if the user is not logged in, then take them to the home page
		if (!userSession.isLoggedIn()) {

			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("home.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Saves the bookmark after setting some properties. It also ends the
	 * conversation
	 * 
	 * @return The view to go to
	 */
	public String save() {
		bookmark.setAddedOn(new Date());
		bookmark.setUser(userSession.getUser());
		//check the url starts with HTTP:// (not the best way to do it)
		if (!bookmark.getUrl().toUpperCase().startsWith("HTTP://")) {
			bookmark.setUrl("http://"+bookmark.getUrl());
		}
		bookmarkDao.save(bookmark);
		conversation.end();
		return "home?faces-redirect=true";
	}

	public Bookmark getBookmark() {
		return bookmark;
	}

	public void setBookmark(Bookmark bookmark) {
		this.bookmark = bookmark;
	}

	/**
	 * Returns a list of tags from the database filtered by the value in
	 * <code>tagFilter</code>.
	 * 
	 * @return List of tags optionally filtered by the tagFilter value
	 */
	public List<Tag> getTags() {
		if (tags == null) {
			tags = bookmarkDao.findTagsMatching(tagFilter, true);
		}
		return tags;
	}

	public String getTagFilter() {
		return tagFilter;
	}

	public void setTagFilter(String tagFilter) {
		this.tagFilter = tagFilter;
	}

	/**
	 * Adds a new tag to the bookmark by either locating it in the database, or
	 * creating it as a new tag if not found.
	 * 
	 * @param tagname
	 *            Name of tag to add
	 */
	public void addTag(String tagname) {
		Tag tag = bookmarkDao.findOrCreateTag(tagname);
		addTagToBookmark(tag);
	}

	/**
	 * Event method called when the user changes the <code>tagFilter</code>
	 * value. This method invalidates the list of tags so they will be
	 * re-rendered with the new state values.
	 * 
	 * @param event
	 *            Details of what called this even
	 */
	public void tagSearchUpdate(ValueChangeEvent event) {
		invalidateTagLists();
	}

	/**
	 * Creates a {@link ListDataModel} for the list of available tags. Available
	 * tags are tags those which have not already been added and match the
	 * <code>tagFilter</code> value.
	 * 
	 * @return A {@link ListDataModel} wrapped around the list of bookmark tags
	 */
	public ListDataModel<Tag> getAvailableTagModel() {
		if (availableTagsModel == null) {
			List<Tag> tagList = getTags();
			tagList.removeAll(bookmark.getTags());
			availableTagsModel = new ListDataModel<Tag>(tagList);
		}
		return availableTagsModel;
	}

	/**
	 * Creates a {@link ListDataModel} for the list of selected tags.
	 * 
	 * @return A {@link ListDataModel} wrapped around the list of bookmark tags
	 */
	public ListDataModel<Tag> getSelectedTagModel() {
		if (selectedTagsModel == null) {
			selectedTagsModel = new ListDataModel<Tag>(getBookmark().getTags());
		}
		return selectedTagsModel;
	}

	/**
	 * Used to add the clicked tag to the bookmark
	 */
	public void addTagToBookmark() {
		Tag tag = getAvailableTagModel().getRowData();
		addTagToBookmark(tag);
	}

	/**
	 * Used to remove the clicked tag from the bookmark
	 */
	public void removeTagFromBookmark() {
		Tag tag = getSelectedTagModel().getRowData();
		removeTag(tag);
	}

	/**
	 * Invalidates the tag lists, usually called in the Invoke Application stage
	 * so the data is re-fetched in the render response stage and includes the
	 * changes made when the application is invoked (i.e. tags go from being
	 * available to selected)
	 */
	private void invalidateTagLists() {
		tags = null;
		availableTagsModel = null;
		selectedTagsModel = null;
	}

	/**
	 * Adds a new tag to the bookmark based on the value entered in the
	 * <code>tagFilter</code> field.
	 */
	public void addNewTag() {
		addTagNameToBookmark(tagFilter);
		tagFilter = null;
	}

	private void addTagNameToBookmark(String tagName) {
		if (tagName != null && tagName.length() != 0) {
			Tag tag = bookmarkDao.findOrCreateTag(tagName);
			addTagToBookmark(tag);
		}
	}

	/**
	 * Performs the addition of the tag to the bookmark and is used by the other
	 * methods that are used to add a tag to a bookmark. Also performs null
	 * checks on the tag first. Once added the list of available/selected tags
	 * is invalidated.
	 * 
	 * @param tag
	 *            Tag to add to the bookmark (can be null)
	 */
	private void addTagToBookmark(Tag tag) {
		if (tag == null) {
			return;
		}
		if (!getBookmark().getTags().contains(tag)) {
			getBookmark().getTags().add(tag);
			invalidateTagLists();
		}
	}

	/**
	 * Removes the tag from the bookmark and invalidates the necessary lists.
	 * 
	 * @param tag
	 *            Tag to remove from the bookmark (can be null)
	 */
	private void removeTag(Tag tag) {
		if (tag == null) {
			return;
		}
		if (getBookmark().getTags().contains(tag)) {
			getBookmark().getTags().remove(tag);
			invalidateTagLists();
		}
	}
}
