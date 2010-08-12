#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * This entity contains the bookmark information such as URL, title,
 * description, date added and so on. It belongs to a {@link User} and has a
 * number of {@link Tag} objects assigned to it.
 * 
 * @author Andy Gibson
 * 
 */
@Entity
@Table(name = "BOOKMARKS")
public class Bookmark implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addedOn;

	@Column(length = 255)
	@NotEmpty
	private String url;

	@Column(length = 255)
	@NotEmpty
	private String title;

	@Column(length = 255)
	@NotEmpty
	private String description;

	@ManyToMany
	@JoinTable(name = "BOOKMARK_TAGS_XREF")
	private List<Tag> tags = new ArrayList<Tag>();

	public void removeTag(String tagName) {
		int i = 0;

		while (i < getTags().size()) {
			if (getTags().get(i).getName().equals(tagName)) {
				getTags().remove(i);
			} else {
				i++;
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void getTagList() {

	}

}
