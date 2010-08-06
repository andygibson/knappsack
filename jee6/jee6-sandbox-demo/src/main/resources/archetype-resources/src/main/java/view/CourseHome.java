#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import ${package}.bean.SchoolDao;
import ${package}.model.Course;
import ${package}.model.Student;

/**
 * Home bean for the {@link Course} entity type. This class is written in full
 * without the benefit of using the {@link EntityHome} class to give you an idea
 * of all the pieces needed to manage CRUD. Inversely it also demonstrates how
 * much you save by using a common {@link EntityHome} type of class.
 * 
 * @author Andy Gibson
 * 
 */
@Named("courseHome")
@ConversationScoped
public class CourseHome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SchoolDao schoolDao;

	@Inject
	private Conversation conversation;

	private Long id;

	private Course course;

	private List<Student> students;

	/**
	 * Init method which checks the parameters and starts the conversation if
	 * necessary. If this is the view page, we don't want to start the
	 * conversation. If it is the edit page, then we probably do. We call this
	 * method from the course view and edit pages in the pre-render event.
	 * 
	 * We don't create new courses here so we require an id parameter.
	 * 
	 * @param beginConversation
	 *            flag to show whether to start the conversation or not
	 */
	public void init(boolean beginConversation) {
		if (id == null) {
			ViewUtil.redirect("home.jsf");
		}

		if (beginConversation && conversation.isTransient()) {
			conversation.begin();
		}
	}

	/**
	 * Creates a new course and starts the conversation
	 */
	public void createNew() {
		if (course == null) {
			course = new Course();
		}
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public Course getCourse() {
		
		if (course == null) {
			if (id == null) {
				throw new IllegalStateException("Cannot load a course without an Id");
			}
			
			course = schoolDao.getCourse(id);
		}
		
		return course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Lazy loads the list of students for this course.
	 * 
	 * @return List of Students enrolled in this course.
	 */
	public List<Student> getStudents() {
		if (students == null) {
			students = schoolDao.getStudentsForCourse(getCourse());
		}
		return students;
	}

	/**
	 * Indicates whether this course instance has already been saved or not
	 * based on whether it has an Id or not.
	 * 
	 * @return whether the object has been saved or not.
	 */
	public boolean isManaged() {
		return getCourse().getId() == null;
	}

	@NotNull(message="Teacher is required")
	public Long getTeacherId() {
		if (getCourse() != null && getCourse().getTeacher() != null) {
			return getCourse().getTeacher().getId();
		}
		return null;
	}
	
	public void setTeacherId(Long teacherId) {
		if (teacherId != null && teacherId != null) {
			if (!teacherId.equals(getTeacherId())) {
				getCourse().setTeacher(schoolDao.getTeacher(teacherId));
			}
		} else {
			getCourse().setTeacher(null);
		}
	}

	/**
	 * Save the course, end the conversation and redirect to the view page.
	 */
	public void save() {
		schoolDao.save(course);
		conversation.end();
		ViewUtil.redirect("courseView.jsf?courseId=" + id);
	}

	/**
	 * Refresh the course entity, end the conversation and then redirect to a
	 * new page. If the entity is managed we go to the view page for the entity,
	 * otherwise we go to the main home page.
	 * 
	 */
	public void cancel() {
		schoolDao.refresh(course);
		conversation.end();

		if (isManaged()) {
			ViewUtil.redirect("courseView.jsf?courseId=" + id);
		} else {
			ViewUtil.redirectHome();
		}

	}
	
	public Conversation getConversation() {
		return conversation;
	}
}
