#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.bean.SchoolDao;
import ${package}.model.Course;
import ${package}.model.Student;
import ${package}.model.Teacher;

/**
 * This bean is used to provide the backing data and events for adding and
 * removing courses from a students list using a shuttle mechanism to swap items
 * from one list to another.
 * <p/>
 * We inject the {@link PersonHome} bean to access the current person. Bear in
 * mind the person could be a student or a teacher, but this bean shouldn't be
 * used for teachers since they don't sign up for courses.
 * <p/>
 * This is a conversational bean to be used within a long running conversation.
 * The lists of enrolled and available courses are held between requests on the
 * server. We do not re-generate it each time and persist between each call. If
 * you change the courses and click cancel, the changes are undone. When courses
 * are moved from one list to another, we sort them server side before they are
 * returned back to the view.
 * 
 * @author Andy Gibson
 * 
 */
@Named("courseAssignment")
@ConversationScoped
public class CourseAssignment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PersonHome personHome;

	@Inject
	private SchoolDao schoolDao;

	private List<Course> availableCourses;

	// courses that can be added to the student
	private List<SelectItem> availableCoursesItems;
	private List<SelectItem> enrolledCoursesItems;

	// courses that the user selected to be added
	private List<String> selectedAvailableCourses;
	private List<String> selectedEnrolledCourses;

	/**
	 * Helper method that casts the user being edited to a {@link Student}. This
	 * just makes it easier to access the courses enrolled list. If the person
	 * is a {@link Teacher} then the method returns null, but since this is only
	 * used when adding students to courses, it shouldn't be a problem.
	 * 
	 * @return The person we are editing as a {@link Student}
	 */
	public Student getStudent() {
		if (personHome.getIsStudent()) {
			return (Student) personHome.getEntity();
		}
		return null;
	}

	/**
	 * Creates the list of available courses a student can enroll in as a list
	 * of select items. This needs to be in the backing bean since it will be
	 * recalculated as courses are added to a student but not yet saved to the
	 * database (in the conversation)
	 * 
	 * @return List of courses a student can enroll in
	 */
	public List<Course> getAvailableCourses() {
		if (availableCourses == null) {
			availableCourses = schoolDao.getCourses();
			List<Course> enrolled = getStudent().getEnrolled();
			for (Course c : enrolled) {
				availableCourses.remove(c);
			}
		}
		return availableCourses;
	}

	/**
	 * @return List of courses available to the student as a list of
	 *         {@link SelectItem} instances
	 */
	public List<SelectItem> getAvailableCoursesItems() {
		if (availableCoursesItems == null) {
			availableCoursesItems = ViewUtil
					.wrapInSelectItems(getAvailableCourses());
			ViewUtil.sortSelectItems(availableCoursesItems);
		}
		return availableCoursesItems;
	}

	public List<SelectItem> getEnrolledCoursesItems() {
		if (enrolledCoursesItems == null) {
			enrolledCoursesItems = ViewUtil.wrapInSelectItems(getStudent()
					.getEnrolled());
			ViewUtil.sortSelectItems(enrolledCoursesItems);
		}
		return enrolledCoursesItems;
	}

	public void invalidateItemLists() {
		availableCoursesItems = null;
		enrolledCoursesItems = null;
	}

	public void addStudentToSelectedCourses() {
		for (String id : selectedAvailableCourses) {
			Course course = schoolDao.getCourse(Long.valueOf(id));
			getStudent().getEnrolled().add(course);
			availableCourses.remove(course);
			invalidateItemLists();
		}
	}

	public void removeStudentFromSelectedCourses() {
		for (String id : selectedEnrolledCourses) {
			Course course = schoolDao.getCourse(Long.valueOf(id));
			getStudent().getEnrolled().remove(course);
			availableCourses.add(course);
			invalidateItemLists();
		}
	}

	public List<String> getSelectedAvailableCourses() {
		return selectedAvailableCourses;
	}

	public void setSelectedAvailableCourses(
			List<String> selectedAvailableCourses) {
		this.selectedAvailableCourses = selectedAvailableCourses;
	}

	public List<String> getSelectedEnrolledCourses() {
		return selectedEnrolledCourses;
	}

	public void setSelectedEnrolledCourses(List<String> selectedEnrolledCourses) {
		this.selectedEnrolledCourses = selectedEnrolledCourses;
	}
}
