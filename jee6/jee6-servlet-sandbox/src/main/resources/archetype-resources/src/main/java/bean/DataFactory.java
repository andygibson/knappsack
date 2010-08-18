#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import ${package}.qualifier.DataRepository;
import ${package}.model.Course;
import ${package}.model.Student;
import ${package}.model.Teacher;

/**
 * This class initializes the data in the database for the demo applications. It
 * is invoked when the JSF page calls for the value of the
 * <code>${symbol_pound}{dataStatus}</code> expression. This bean produces that value using
 * the CDI {@link Produces} annotation. When invoked, it checks to see if the
 * data already exists and if not, it generates the data before returning the
 * value. 
 * 
 * @author Andy Gibson
 * 
 */
public class DataFactory {

	private static Random random;


	@Inject
	@DataRepository
	private EntityManager entityManager;

	private List<Student> students = new ArrayList<Student>();
	private List<Course> courses = new ArrayList<Course>();
	private List<Teacher> teachers = new ArrayList<Teacher>();

	private void hookUp() {
		random = new Random(102405);
		for (Course c : courses) {
			int count = 0;
			while ((count < 20 || random.nextInt(100) < 70) && count < 40) {
				Student s = getRandomItem(students);
				if (!s.getEnrolled().contains(c)) {
					s.getEnrolled().add(c);
					count++;
				}
			}
		}
		for (Student s : students) {
			entityManager.merge(s);
		}

	}

	public void createData() {
		if (!isDataCreated()) {
			entityManager.getTransaction().begin();
			try {
				random = new Random(102405);

				buildStudents();
				buildTeachers();
				buildCourses();

				hookUp();
				doCustomData();
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				e.printStackTrace();
			}
		}
	}

	private Teacher createTeacher(String firstName, String lastName) {
		Teacher item = new Teacher(firstName, lastName);
		item.setCreatedOn(createDate(200));
		entityManager.persist(item);
		return item;
	}

	private void buildTeachers() {
		teachers.add(createTeacher("NICHOLAS", "MCINTYRE"));
		teachers.add(createTeacher("MARCUS", "PUGH"));
		teachers.add(createTeacher("NORMA", "TYLER"));
		teachers.add(createTeacher("EMILY", "MCINTYRE"));
		teachers.add(createTeacher("JEAN", "EWING"));
		teachers.add(createTeacher("WYATT", "TYLER"));
		teachers.add(createTeacher("ZACHARY", "FOSTER"));
		teachers.add(createTeacher("DIANE", "ENGLISH"));
		teachers.add(createTeacher("GARY", "HAYES"));
		teachers.add(createTeacher("SABRINA", "LOWE"));
		teachers.add(createTeacher("RANDI", "BARRON"));
		teachers.add(createTeacher("JEFFREY", "BARNES"));
		teachers.add(createTeacher("LEWIS", "HOPPER"));
		teachers.add(createTeacher("JANET", "MACIAS"));
		teachers.add(createTeacher("DEAN", "CASTRO"));
		teachers.add(createTeacher("TRENT", "COPELAND"));
		teachers.add(createTeacher("CHRISTINE", "EDWARDS"));
		teachers.add(createTeacher("DORIS", "CASTILLO"));
		teachers.add(createTeacher("HUNTER", "ROGERS"));
		teachers.add(createTeacher("VINCENT", "PHELPS"));
		teachers.add(createTeacher("WESLEY", "SHIELDS"));
		teachers.add(createTeacher("GREGORY", "HUGHES"));

	}

	public Student createStudent(String firstName, String lastName) {
		Student item = new Student(firstName, lastName);
		item.setCreatedOn(createDate(200));
		item.setGpa(1 + random.nextInt(30) / 10f);
		entityManager.persist(item);
		return item;

	}

	private Date createDate(int minDaysBack) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -random.nextInt(minDaysBack));
		return cal.getTime();
	}

	private void buildStudents() {
		students.add(createStudent("ROSE", "PATTERSON"));
		students.add(createStudent("ROD", "WALTON"));
		students.add(createStudent("BOBBY", "BRIDGES"));
		students.add(createStudent("CHARLENE", "PITTS"));
		students.add(createStudent("BRUCE", "DOTSON"));
		students.add(createStudent("JACOB", "DANIEL"));
		students.add(createStudent("LARRY", "SERRANO"));
		students.add(createStudent("BRIANNA", "BOND"));
		students.add(createStudent("ABIGAIL", "GILLIAM"));
		students.add(createStudent("MOLLY", "ESTRADA"));
		students.add(createStudent("TRENT", "IRWIN"));
		students.add(createStudent("BROOKE", "COHEN"));
		students.add(createStudent("JOSH", "SMITH"));
		students.add(createStudent("CRAIG", "SWEET"));
		students.add(createStudent("JANET", "WYNN"));
		students.add(createStudent("TYLER", "GARZA"));
		students.add(createStudent("CLIFFORD", "SWANSON"));
		students.add(createStudent("MASON", "CLINE"));
		students.add(createStudent("MARILYN", "SNIDER"));
		students.add(createStudent("JOHN", "PARKER"));
		students.add(createStudent("JOHN", "JOYNER"));
		students.add(createStudent("SHAUN", "RUTLEDGE"));
		students.add(createStudent("PEGGY", "DAVIS"));
		students.add(createStudent("LAKEN", "GOULD"));
		students.add(createStudent("HEIDI", "MARSHALL"));
		students.add(createStudent("JASMINE", "REILLY"));
		students.add(createStudent("CHAD", "COPELAND"));
		students.add(createStudent("FLOYD", "PETERSON"));
		students.add(createStudent("WHITNEY", "HAYNES"));
		students.add(createStudent("JEREMIAH", "PITTMAN"));
		students.add(createStudent("ROB", "CLEVELAND"));
		students.add(createStudent("RONDA", "GRIFFITH"));
		students.add(createStudent("KATELYN", "RICHMOND"));
		students.add(createStudent("CARMEN", "ALVARADO"));
		students.add(createStudent("JOANN", "GUTHRIE"));
		students.add(createStudent("DERRICK", "SALAS"));
		students.add(createStudent("COURTNEY", "GARRETT"));
		students.add(createStudent("AMBER", "MICHAEL"));
		students.add(createStudent("MARY ANN", "SWEENEY"));
		students.add(createStudent("TRACY", "HOWARD"));
		students.add(createStudent("ALICE", "ROCHA"));
		students.add(createStudent("BECKY", "ABBOTT"));
		students.add(createStudent("RUSS", "THOMAS"));
		students.add(createStudent("DENISE", "DUNCAN"));
		students.add(createStudent("ALEXIS", "MAYNARD"));
		students.add(createStudent("RANDI", "HUDSON"));
		students.add(createStudent("VIVIAN", "BEARD"));
		students.add(createStudent("ROBERT", "OLIVER"));
		students.add(createStudent("WILLIAM", "JEFFERSON"));
		students.add(createStudent("CHRISTINA", "WEBB"));
		students.add(createStudent("DERRICK", "RODGERS"));
		students.add(createStudent("KIMBERLY", "NOEL"));
		students.add(createStudent("DEREK", "HOUSE"));
		students.add(createStudent("MARION", "ELLIOTT"));
		students.add(createStudent("BILL", "LLOYD"));
		students.add(createStudent("TARA", "HENSLEY"));
		students.add(createStudent("JEREMY", "NOEL"));
		students.add(createStudent("JIMMY", "DAVIS"));
		students.add(createStudent("BRIAN", "MCKEE"));
		students.add(createStudent("TABITHA", "WELCH"));
		students.add(createStudent("MARIE", "SMITH"));
		students.add(createStudent("ERICA", "AYERS"));
		students.add(createStudent("MYRON", "GILLESPIE"));
		students.add(createStudent("BRANDON", "RIVERA"));
		students.add(createStudent("HELEN", "WILSON"));
		students.add(createStudent("DANIELLE", "CALDERON"));
		students.add(createStudent("GERALD", "HAHN"));
		students.add(createStudent("ZACHARY", "KEY"));
		students.add(createStudent("WILLIE", "RAMOS"));
		students.add(createStudent("MONICA", "LOWE"));
		students.add(createStudent("ALLISON", "ROSA"));
		students.add(createStudent("SHELLY", "GOLDEN"));
		students.add(createStudent("DAKOTA", "FROST"));
		students.add(createStudent("SHANNA", "BUCK"));
		students.add(createStudent("VICTOR", "COMPTON"));
		students.add(createStudent("DAWN", "SYKES"));
		students.add(createStudent("THOMAS", "RIVAS"));
		students.add(createStudent("EARL", "GOOD"));
		students.add(createStudent("HEATHER", "JOHNSON"));
		students.add(createStudent("TASHA", "ROSS"));
		students.add(createStudent("VERNON", "JACKSON"));
		students.add(createStudent("GINGER", "POPE"));
		students.add(createStudent("TIMOTHY", "LYNCH"));
		students.add(createStudent("ROCHELLE", "VALENTINE"));
		students.add(createStudent("DAWN", "MICHAEL"));
		students.add(createStudent("STEVEN", "FORBES"));
		students.add(createStudent("RODNEY", "WALTER"));
		students.add(createStudent("DERRICK", "MILLER"));
		students.add(createStudent("DEBBIE", "GILES"));
		students.add(createStudent("DIANE", "LOTT"));
		students.add(createStudent("SANDRA", "ROBERSON"));
		students.add(createStudent("JEFFREY", "ANDREWS"));
		students.add(createStudent("HEATHER", "CRAWFORD"));
		students.add(createStudent("KYLIE", "GALLEGOS"));
		students.add(createStudent("DARLENE", "CLAYTON"));
		students.add(createStudent("SAVANNAH", "LYNCH"));
		students.add(createStudent("HOPE", "OLSON"));
		students.add(createStudent("MANDY", "MOSLEY"));
		students.add(createStudent("GLEN", "CAMPBELL"));
		students.add(createStudent("REGINA", "HARDY"));
		createStudent("MIKE", "HARDY");// don't add this to the list

	}

	public Course createCourse(String code, String title) {
		Course course = new Course();
		course.setTitle(title);
		course.setCode(code);
		course.setCreatedOn(createDate(200));
		course.setTeacher(getRandomItem(teachers));
		entityManager.persist(course);
		courses.add(course);
		return course;
	}

	private void buildCourses() {
		createCourse("CS101", "Computing for Beginners");
		createCourse("BZ101", "Business for Beginners");
		createCourse("EC101", "Economics for Beginners");
		createCourse("EX101", "Expertism for Beginners");
		createCourse("HIS101", "History for Beginners");
		createCourse("GEO101", "Geography for Beginners");
		createCourse("LIT101", "Literature for Beginners");
		createCourse("MAT101", "Math for Beginners");

		createCourse("LIT301", "Dickensian Parodies");
		createCourse("ECO324", "Keynsian Economics");
		createCourse("HIS311", "The Middle Ages");
		createCourse("GEO411", "Locating things on Earth");
		createCourse("CS393", "Optical Computing");
		createCourse("CS253", "Networking");
		createCourse("CS253", "Database Administration");
		createCourse("CS325", "3D Graphics");
		createCourse("CS250", "Computing in Business");
		createCourse("HIS243", "Early European History");
		createCourse("MAT253", "Advanced Calculus");
		createCourse("BZ245", "Principles of Profit");
		createCourse("EC382", "Managing Societal Costs");
		createCourse("BZ322", "Advertising for Profit");
		createCourse("BZ895", "Strategies for Winning");
		createCourse("CS134", "Assembly Language");
		createCourse("CS289", "Artificial Intelligence");

	}

	private <T> T getRandomItem(List<T> items) {
		return items.get(random.nextInt(items.size()));
	}

	private boolean isDataCreated() {
		return entityManager.createQuery("select c from Course c").getResultList().size() != 0;
	}

	@Produces
	@Named("dataStatus")
	public String getDataMessage() throws NotSupportedException,
			SystemException {
		createData();
		return "Data Created";
	}

	@SuppressWarnings("unchecked")
	@Produces
	@Named("demoCourseList")
	public List<Course> getCourses() {
		List<Course> courses = entityManager.createQuery(
				"select distinct c from Course c left join fetch c.teacher left join fetch c.students")
				.getResultList();
		return courses;
	}

	private void doCustomData() {
		// do nothing, users can add additional data creation here
	}
}
