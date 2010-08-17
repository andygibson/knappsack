#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity class for courses that can be taken. Consists of a title and a code
 * and is assigned a teacher object that teaches that course. It also has a set
 * of students that are enrolled in that course.
 * 
 * @author Andy Gibson
 * 
 */
@Entity
public class Course extends BaseEntity {

	@Column(length = 32, nullable = false)
	@Size(max = 32)
	@NotEmpty(message = "title is required")
	private String title;

	@Column(length = 8, nullable = false)
	@Size(max = 8)
	@NotEmpty(message = "code is required")
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "Teacher is required")
	private Teacher teacher;

	@ManyToMany(mappedBy = "enrolled")
	private List<Student> students = new ArrayList<Student>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getDisplayText() {
		return getTitle();
	}
}
