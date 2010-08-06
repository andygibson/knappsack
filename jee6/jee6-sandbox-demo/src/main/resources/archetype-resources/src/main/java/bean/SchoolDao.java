#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import ${package}.model.BaseEntity;
import ${package}.model.Course;
import ${package}.model.Person;
import ${package}.model.Student;
import ${package}.model.Teacher;

/**
 * This is the interface for the SchoolDao implementations. In our case, it
 * doubles as the {@link Local} EJB interface for our {@link Stateless} bean.
 * Currently, this is only implemented in {@link SchoolJpaDao}.
 * 
 * @author Andy Gibson
 * 
 */
@Local
public interface SchoolDao {

	public Person getPerson(Long id);

	public Teacher getTeacher(Long id);

	public Student getStudent(Long id);

	public Course getCourse(Long id);

	public List<Teacher> getTeachers();

	public List<Course> getCourses();

	public List<Student> getStudentsForCourse(Course course);

	public List<Course> getCoursesForStudent(Student student);

	public List<Course> getCoursesForTeacher(Teacher teacher);

	/**
	 * Returns a list of {@link Student} objects based on the search and
	 * pagination criteria.
	 * 
	 * @param criteria
	 *            The criteria for which we get the results
	 * @param firstResult
	 *            Index of the first result to return
	 * @param pageSize
	 *            The number of results to return
	 * @return List of {@link Student} entities matching the criteria
	 */
	public List<Student> searchStudents(SearchCriteria criteria,
			int firstResult, int pageSize);

	public <T extends BaseEntity> T save(T entity);

	public <T extends BaseEntity> void refresh(T entity);

	public void notifyCourseChanges(Student student,
			List<Course> originalCourseList);

	/**
	 * Returns a list of teachers with the number of courses they teach.
	 * 
	 * @return List of {@link TeacherCourseCount} objects
	 */
	public List<TeacherCourseCount> getTeachCourseCounts();
}
