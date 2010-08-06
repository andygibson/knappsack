#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.bean.SchoolDao;
import ${package}.model.Course;
import ${package}.model.Person;
import ${package}.model.Student;
import ${package}.model.Teacher;

/**
 * PersonHome is responsible for fetching the {@link Person} entity in the form
 * of a {@link Student} or {@link Teacher} entity. Most of the methods are
 * shared across the two subclasses but some functions aren't. We have the
 * {@link PersonHome${symbol_pound}isStudent()} method to indicate if this person is a student
 * and {@link PersonHome${symbol_pound}isTeacher()} to indicate if it is a teacher.
 * <p/>
 * This class is also based off the {@link EntityHome} class which provides most
 * of the functions for managing the entity except for the loading and creating
 * which is done using the {@link SchoolDao} implementation which is injected.
 * <p/>
 * When creating a new entity, we have to know whether to create a new student
 * or teacher instance. In order to get this information, we have the
 * <code>newType</code> property which is either 'student' or 'teacher' to
 * indicate which kind to create in the {@link PersonHome${symbol_pound}doCreateEntity()}
 * method.
 * 
 * @author Andy Gibson
 * 
 */
@Named("personHome")
@ConversationScoped
public class PersonHome extends EntityHome<Person> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SchoolDao schoolDao;

	private List<Course> courses;

	// field for the type parameter indicating what type of person (student or
	// teacher) to create
	private String newType;

	// snapshot of the students enrolled courses is captured at load time so we
	// can determine any changes later on.
	private List<Course> initialEnrollement = new ArrayList<Course>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see ${package}.view.EntityHome${symbol_pound}doInit()
	 */
	@Override
	protected boolean doInit() {
		boolean validNewType = "student".equals(getNewType())
				|| "teacher".equals(getNewType());
		if (getId() == null && !validNewType) {
			ViewUtil.redirect("home.jsf");
			return false;
		}
		return true;
	}

	public boolean getIsTeacher() {
		return getEntity() instanceof Teacher;
	}

	public boolean getIsStudent() {
		return getEntity() instanceof Student;
	}

	@Override
	protected Person doCreateEntity() {
		// create a new Person based on the value of newType
		if ("student".equals(newType)) {
			return new Student();
		}

		if ("teacher".equals(newType)) {
			return new Teacher();
		}
		throw new IllegalStateException(
				"Type of new person is undefined in person home");
	}

	@Override
	protected Person doLoadEntity() {
		Person p = schoolDao.getPerson(getId());
		// grab a snapshot of courses taken by the student so we know what
		// changed when we save it so we can notify the student.
		if (p instanceof Student) {
			Student s = (Student) p;
			initialEnrollement.addAll(s.getEnrolled());
		}
		return p;
	}

	/**
	 * Returns a list of courses for this person. If the person is a
	 * {@link Student}, it returns the courses enrolled, and if it is a
	 * {@link Teacher} it is the list of courses they teach.
	 * 
	 * @return List of {@link Course} objects
	 */
	public List<Course> getCourses() {
		if (courses == null) {
			if (getIsStudent()) {
				courses = schoolDao.getCoursesForStudent((Student) getEntity());
			} else if (getIsTeacher()) {
				courses = schoolDao.getCoursesForTeacher((Teacher) getEntity());
			}
		}
		return courses;
	}

	/**
	 * Save the person and if it is a teacher, it notifies the student of any
	 * course changes via email using the CDI event mechanism.
	 * 
	 * Also ends the current conversation and redirects to the person view page.
	 */
	public void save() {

		schoolDao.save(getEntity());
		if (getIsStudent()) {
			schoolDao.notifyCourseChanges((Student) getEntity(),
					initialEnrollement);
		}
		getConversation().end();
		ViewUtil.redirect("personView.jsf?personId=" + getEntity().getId());
	}

	/**
	 * Called when the user cancel changes to the person. Ends the conversation
	 * and redirects the user to another page. If this entity is a new one (and
	 * therefore doesn't exist since we cancelled changes) then the user is
	 * redirected home. Otherwise they are redirected to the view page for the
	 * person.
	 */
	public void cancel() {
		getConversation().end();
		if (isManaged()) {
			ViewUtil.redirect("personView.jsf?personId=" + getEntity().getId());
		} else {
			ViewUtil.redirect("home.jsf");
		}
	}

	/**
	 * Value to indicate whether the {@link PersonHome} bean should create a new
	 * {@link Teacher} or {@link Student} instance when it creates a new one.
	 * 
	 * @return returns the value of new type
	 */
	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}

	/**
	 * This method just makes things more readable so we can use
	 * <code>getPerson()</code> instead of <code>getEntity</code>.
	 * 
	 * @return The entity as a person
	 */
	public Person getPerson() {
		return getEntity();
	}

	/**
	 * Casts the person as a student if needed to appease the JSF validators,
	 * even though you can bind to properties on subclasses.
	 * 
	 * @return The person as a {@link Student}
	 */
	public Student getStudent() {
		return getPerson() instanceof Student ? (Student) getPerson() : null;
	}
}
