#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ${package}.event.StudentCoursePayload;
import ${package}.model.BaseEntity;
import ${package}.model.Course;
import ${package}.model.Person;
import ${package}.model.Student;
import ${package}.model.Teacher;
import ${package}.qualifier.DataRepository;
import ${package}.qualifier.StudentAdded;
import ${package}.qualifier.StudentRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the {@link SchoolDao} as a {@link Stateless} EJB to take advantage
 * of transaction handling.
 * 
 * @author Andy Gibson
 * 
 */
@Stateless
public class SchoolJpaDao implements SchoolDao {

	private static Logger log = LoggerFactory.getLogger(SchoolJpaDao.class);

	@DataRepository
	@Inject
	private EntityManager entityManager;

	@Inject
	@StudentAdded
	private Event<StudentCoursePayload> addStudentCourseEvent;

	@Inject
	@StudentRemoved
	private Event<StudentCoursePayload> removeStudentCourseEvent;

	@Override
	public Course getCourse(Long id) {
		Course c = entityManager.find(Course.class, id);
		if (c.getTeacher() != null) {
			c.getTeacher().getName();// fetch the teacher to avoid LIEs
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getCourses() {
		return entityManager.createQuery(
				"select c from Course c order by c.title").getResultList();
	}

	@Override
	public Student getStudent(Long id) {
		return entityManager.find(Student.class, id);
	}

	@Override
	public Teacher getTeacher(Long id) {
		return entityManager.find(Teacher.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getTeachers() {
		String ql = "select t from Teacher t order by t.lastName,t.firstName";
		return entityManager.createQuery(ql).getResultList();
	}

	/* (non-Javadoc)
	 * @see ${package}.bean.SchoolDao${symbol_pound}searchStudents(${package}.bean.SearchCriteria, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> searchStudents(SearchCriteria criteria,
			int firstResult, int pageSize) {
		log.debug("Searching students with criteria {}", criteria);
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> clauses = new ArrayList<String>();
		String sql = "select distinct s from Student s left JOIN s.enrolled c left join c.teacher t";

		if (criteria.getFirstName() != null
				&& criteria.getFirstName().length() != 0) {
			clauses.add("upper(s.firstName) like :firstName");
			params
					.put("firstName", criteria.getFirstName().toUpperCase()
							+ "%");
		}

		if (criteria.getLastName() != null
				&& criteria.getLastName().length() != 0) {
			clauses.add("upper(s.lastName) like :lastName");
			params.put("lastName", criteria.getLastName().toUpperCase() + "%");
		}

		if (criteria.getTeacherId() != null && criteria.getTeacherId() != 0) {
			clauses.add("t.id = :teacherId");
			params.put("teacherId", criteria.getTeacherId());
		}

		String restriction = "";
		for (String s : clauses) {
			if (restriction.length() != 0) {
				restriction += " AND ";
			}
			restriction += s;
		}
		if (restriction.length() != 0) {
			sql = sql + " WHERE " + restriction;
		}

		// load parameters
		Query qry = entityManager.createQuery(sql);
		for (Entry<String, Object> entry : params.entrySet()) {
			qry.setParameter(entry.getKey(), entry.getValue());
		}

		qry.setFirstResult(firstResult);

		if (pageSize <= 0) {
			pageSize = 10;
		}
		qry.setMaxResults(pageSize);

		return qry.getResultList();

	}

	@Override
	public <T extends BaseEntity> T save(T entity) {
		return entityManager.merge(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudentsForCourse(Course c) {
		String ql = "select s from Course c left join c.students s where c.id = :courseId order by s.lastName,s.firstName";
		return entityManager.createQuery(ql)
				.setParameter("courseId", c.getId()).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getCoursesForStudent(Student student) {
		String ql = "select c from Course c left join c.students s join fetch c.teacher where s.id = :studentId order by c.title";
		return entityManager.createQuery(ql).setParameter("studentId",
				student.getId()).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getCoursesForTeacher(Teacher teacher) {
		String ql = "select c from Course c where c.teacher.id = :teacherId order by c.teacher.lastName,c.teacher.firstName";
		return entityManager.createQuery(ql).setParameter("teacherId",
				teacher.getId()).getResultList();
	}

	@Override
	public <T extends BaseEntity> void refresh(T entity) {
		if (entityManager.contains(entity)) {
			entityManager.refresh(entity);
		}
	}

	@Override
	public Person getPerson(Long id) {
		return entityManager.find(Person.class, id);
	}

	@Override
	public void notifyCourseChanges(Student student,
			List<Course> originalCourseList) {

		for (Course course : student.getEnrolled()) {
			if (!originalCourseList.contains(course)) {
				notifyStudentAddedToCourse(student, course);
			}
		}

		for (Course course : originalCourseList) {
			if (!student.getEnrolled().contains(course)) {
				notifyStudentRemovedFromCourse(student, course);
			}
		}
	}

	private void notifyStudentRemovedFromCourse(Student student, Course course) {
		removeStudentCourseEvent
				.fire(new StudentCoursePayload(student, course));
	}

	private void notifyStudentAddedToCourse(Student student, Course course) {
		addStudentCourseEvent.fire(new StudentCoursePayload(student, course));
	}

	@Override
	public List<TeacherCourseCount> getTeachCourseCounts() {
		List<TeacherCourseCount> results = new ArrayList<TeacherCourseCount>();
		List<Teacher> teachers = getTeachers();
		for (Teacher t : teachers) {
			results.add(new TeacherCourseCount(t.getId(), t.getOrderedName(), t
					.getCoursesTaught().size()));
		}
		return results;
	}
}
