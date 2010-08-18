#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ${package}.model.Bookmark;
import ${package}.model.Tag;
import ${package}.model.User;


/**
 * Simple Dao that
 * 
 * @author Andy Gibson
 * 
 */
@RequestScoped
public class BookmarkDao {

	@DataRepository
	@Inject
	private EntityManager entityManager;

	/**
	 * Attempts to login and locates the {@link User} instance for this logged
	 * in user.
	 * 
	 * @param username
	 *            Username to login with
	 * @param password
	 *            Password to login with
	 * @return The user matching this login or null
	 */
	@SuppressWarnings("unchecked")
	public User login(String username, String password) {
		String ql = "select u from User u where upper(u.username) = upper(:username) and u.password = :password";
		List<User> results = entityManager.createQuery(ql).setParameter(
				"username", username).setParameter("password", password)
				.getResultList();

		return results.size() == 0 ? null : results.get(0);
	}

	/**
	 * Fetches the list of bookmarks to be displayed. The list can be filtered
	 * by user or tag, and can be paginated with the first result and count
	 * parameters.
	 * 
	 * @param tag
	 *            Tag to show or null
	 * @param firstResult
	 *            Index of the first result to show
	 * @param count
	 *            Number of bookmarks to fetch
	 * @param username
	 *            Username of user to show or null
	 * @return List of bookmarks
	 */
	@SuppressWarnings("unchecked")
	public List<Bookmark> getBookmarks(String tag, Integer firstResult,
			Integer count, String username) {
		String ql = "select distinct b from Bookmark b join b.tags t where 1=1";
		boolean hasTag = (tag != null && tag.length() != 0);
		boolean hasUser = (username != null && username.length() != 0);
		if (hasTag) {
			ql = ql + " and t.slug = :tag ";
		}

		if (hasUser) {
			ql = ql + " and b.user.username = :username ";
		}

		// build query
		ql = ql + " order by b.addedOn desc";
		Query qry = entityManager.createQuery(ql);

		if (hasTag) {
			qry.setParameter("tag", tag);
		}

		if (hasUser) {
			qry.setParameter("username", username);
		}
		if (firstResult != null) {
			qry.setFirstResult(firstResult.intValue());
		}
		qry.setMaxResults(count == null ? 10 : Math.max(count.intValue(), 5));
		List<Bookmark> results = qry.getResultList();
		return results;
	}

	/**
	 * Saves an object to the entity manager inside a transaction
	 * 
	 * @param object
	 *            Object to save
	 */
	public void save(Object object) {
		entityManager.getTransaction().begin();
		entityManager.persist(object);
		entityManager.getTransaction().commit();
	}

	/**
	 * Returns a user with a given Id
	 * 
	 * @param userId
	 *            Id of the user to return
	 * @return The {@link User} instance for this Id or an error if the user is
	 *         not found.
	 */
	public User getUser(Long userId) {
		return entityManager.find(User.class, userId);
	}

	/**
	 * Locates a {@link Tag} instance for a given tag name using a case
	 * insensitive query. Optionally, a wildcard can be appended to the name so
	 * you can perform a search for all Tag objects starting with the tagFilter
	 * value.
	 * 
	 * @param tagFilter
	 *            name to search for
	 * @param includeWildcard
	 *            turn the search into a "starts with" query
	 * @return the list of {@link Tag} objects matching this name.
	 */
	@SuppressWarnings("unchecked")
	public List<Tag> findTagsMatching(String tagFilter, boolean includeWildcard) {
		if (tagFilter == null) {
			tagFilter = "";
		}
		tagFilter = tagFilter + (includeWildcard ? "%" : "");
		String ql = "select t from Tag t where upper(t.name) like upper(:name) order by name";
		return entityManager.createQuery(ql).setParameter("name", tagFilter)
				.getResultList();
	}

	/**
	 * Attempts to find a tag matching the tagName value exactly and if not
	 * found, creates and saves a new {@link Tag} and returns that instead.
	 * 
	 * @param tagName
	 *            Name to look for
	 * @return The {@link Tag} matching this name, or a new one set to this
	 *         name.
	 */
	public Tag findOrCreateTag(String tagName) {
		List<Tag> results = findTagsMatching(tagName, false);
		Tag result = null;

		if (results.size() == 0) {
			result = new Tag();
			result.setName(tagName);
			entityManager.getTransaction().begin();
			entityManager.persist(result);
			entityManager.getTransaction().commit();
		} else {
			result = results.get(0);
		}
		return result;
	}
}
