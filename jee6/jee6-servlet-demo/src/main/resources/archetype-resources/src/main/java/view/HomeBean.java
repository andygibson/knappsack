#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.bean.BookmarkDao;
import ${package}.model.Bookmark;


/**
 * Home bean that is used to back the home view page. This bean deals with the
 * page parameters and fetches the list of bookmarks based on those parameters.
 * It also includes attributes for dealing with pagination
 * (firstResult,hasNext,hasPrevious,previousIndex,nextIndex)
 * 
 * @author Andy Gibson
 * 
 */
@Named
@RequestScoped
public class HomeBean {

	private List<Bookmark> bookmarks;
	private static int PAGE_SIZE = 5;

	@Inject
	private BookmarkDao bookmarkDao;

	private Integer firstResult;
	private String tag;
	private String username;
	private boolean hasNext;

	/**
	 * Lazy loads the bookmarks
	 * 
	 * @return The bookmarks
	 */
	public List<Bookmark> getBookmarks() {
		if (bookmarks == null) {
			bookmarks = fetchBookmarks();
		}
		return bookmarks;
	}

	/**
	 * Uses the {@link BookmarkDao} to fetch the results using the tag,
	 * firstResult, username and pagination info stored in the home bean
	 * attributes.
	 * 
	 * @return The list of bookmarks based on the home bean attributes
	 */
	private List<Bookmark> fetchBookmarks() {
		// fetch list plus one to see if we have more results
		List<Bookmark> temp = bookmarkDao.getBookmarks(tag, firstResult,
				PAGE_SIZE + 1, username);
		// do we have another page
		hasNext = temp.size() == PAGE_SIZE + 1;

		if (hasNext) {
			// take off the last item so we return the actual page size.
			temp.remove(PAGE_SIZE);
		}
		return temp;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getFirstResult() {
		return firstResult == null ? 0 : firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void refresh() {

	}

	/**
	 * @return indicates whether the bookmark list is filtered by a tag
	 */
	public boolean isTagView() {
		return tag != null && tag.length() != 0;
	}

	/**
	 * @return indicates whether the bookmark list is filtered by username
	 */
	public boolean isUserView() {
		return username != null && username.length() != 0;
	}

	/**
	 * @return flag indicating whether there is a next page
	 */

	public boolean getHasNext() {
		// make sure we get the bookmark list first
		getBookmarks();
		return hasNext;
	}

	/**
	 * @return the firstResult index of the previous page
	 */
	public int getPreviousIndex() {
		return Math.max(0, getFirstResult() - PAGE_SIZE);
	}

	/**
	 * @return the firstResult index of the next page
	 */
	public int getNextIndex() {
		return getFirstResult() + PAGE_SIZE;
	}

	/**
	 * @return Flag indicating whether we can go back a page
	 */
	public boolean getHasPrevious() {
		return getFirstResult() > 0;
	}

}
