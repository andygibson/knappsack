#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ${package}.model.Bookmark;
import ${package}.model.Tag;
import ${package}.model.User;


/**
 * 
 * Simple class that is used to generate the test data for the application. It
 * provides the bookmark count and if it is zero, it generates the data and
 * re-calculates the count.
 * 
 * @author Andy Gibson
 * 
 */
@RequestScoped
@Named("dataFactory")
public class DataFactory {

	@DataRepository
	@Inject
	private EntityManager entityManager;
	private List<User> users = new ArrayList<User>();
	private List<Tag> tags = new ArrayList<Tag>();
	private static Random random = new Random();

	private Long count;

	public void createData() {
		createUsers();
		createTags();
		entityManager.getTransaction().begin();
		createBookmarks();
		entityManager.getTransaction().commit();
	}

	private void createTags() {
		createTag("Java");
		createTag("SQL");
		createTag("JSF");
		createTag("JPA");
		createTag("JEE");
		createTag("JBoss");
		createTag("Oracle");
		createTag("Web");
		createTag("Spring");
		createTag("Swing");
		createTag("JavaFX");
	}

	public void createTag(String name) {
		Tag t = new Tag(name);
		entityManager.persist(t);
		tags.add(t);
	}

	private void createUsers() {
		entityManager.getTransaction().begin();
		User u = new User();
		u.setUsername("andygibson");
		u.setFirstName("Andy");
		u.setLastName("Gibson");
		u.setPassword("mypass");
		entityManager.persist(u);
		users.add(u);

		u = new User();
		u.setUsername("user1");
		u.setFirstName("Some");
		u.setLastName("User");
		u.setPassword("pass1");
		entityManager.persist(u);
		users.add(u);

		u = new User();
		u.setUsername("user2");
		u.setFirstName("Another");
		u.setLastName("User");
		u.setPassword("pass2");
		entityManager.persist(u);
		users.add(u);

		entityManager.getTransaction().commit();

	}

	@SuppressWarnings("unchecked")
	public List<User> getAvailableUsers() {
		return entityManager.createQuery("select u from User u")
				.getResultList();
	}

	private void addBookmark(String url, String title, String description) {
		addBookmark(url, title, description, false);
	}

	private void addBookmark(String url, String title, String description,
			boolean today) {
		Bookmark b = new Bookmark();
		b.setUrl("http://www." + url);
		b.setTitle(title);
		b.setUser(users.get(random.nextInt(users.size())));
		b.setDescription(description);
		int count = 2 + random.nextInt(4);
		for (int i = 0; i < count; i++) {
			Tag tag = tags.get(random.nextInt(tags.size()));
			if (!b.getTags().contains(tag)) {
				b.getTags().add(tag);
			}
		}
		if (today) {
			b.setAddedOn(new Date());
		} else {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -random.nextInt(200));
			b.setAddedOn(c.getTime());
		}
		entityManager.persist(b);

	}

	private void createBookmarks() {
		addBookmark("address.com", "Speed up finding your house",
				"Make getting home easy with this address locator");
		addBookmark("google.com", "Speed up finding your info",
				"Here's a new search engine I just found, but keep it to yourself");
		addBookmark("javalobby.com", "Read up on Java",
				"Here's where I go for my java reading");
		addBookmark("gmail.com", "Go check your mail",
				"Online mail makes things so much easier");
		addBookmark("yahoo.com", "Do You Yahoo",
				"Try out an alternative search engine");
		addBookmark("weather.com", "weather.com",
				"Don't forget to check the weather before you leave home");
		addBookmark(
				"andygibson.net",
				"Visit my blog.",
				"On my blog there are more articles, tutorials, and writings, plus read about the Knappsack Java EE Maven Archetypes",
				true);
		addBookmark(
				"andygibson.net/blog/projects/knappsack",
				"Knappsack now available in the central maven repository",
				"After some work, I've put the Knappsack Java EE 6 maven archetypes in the the central repository",
				true);
		addBookmark(
				"andygibson.net/blog/news/try-java-ee-6-without-the-commitment/",
				"Take Java EE 6 for a spin without the commitment",
				"The Knappsack Java EE 6 Maven Archetypes now support creating Java EE 6 projects using JSF, CDI and JPA in Jetty and Tomcat",
				true);
		addBookmark(
				"andygibson.net/blog/article/how-to-do-10-common-tasks-in-jsf-2-0/",
				"How to do 10 Common Tasks in JSF 2.0",
				"This article shows you how to perform 10 common web development tasks, such as templating and AJAX, in JSF.",true);
		addBookmark(
				"andygibson.net/blog/tutorial/accessing-and-paginating-csv-files-with-datavalve/",
				"Accessing and Paginating CSV Files with DataValve",
				"While DataValve is mostly used to paginate database driven data, this tutorial shows you how to create a Data Provider to access and paginate a comma delimited text file. We will then use the same provider to page through the data in a Console, Swing and JSF application using the DataValve client/provider interface components.",true);
		addBookmark("lmgtfy.com/?q=java+web+services",
				"Everything you wanted to know about Java Web Services",
				"Here's the fountain of all knowledge on web services");

	}

	@Produces
	@Named("bookmarkCount")
	public Long getBookmarkCount() {
		if (count == null) {
			count = (Long) entityManager.createQuery(
					"select count(b) from Bookmark b").getSingleResult();
			if (count.intValue() == 0) {
				createData();
				count = (Long) entityManager.createQuery(
						"select count(b) from Bookmark b").getSingleResult();
			}
		}
		return count;
	}

}
