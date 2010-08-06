#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;


/**
 * Holds the search criteria when searching for students by first/last name or
 * the teachers they are being taught by.
 * 
 * @author Andy Gibson
 * 
 */
public class SearchCriteria {

	private String firstName;
	private String lastName;
	private Long teacherId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	@Override
	public String toString() {

		return String.format(
				"Search Criter - firstName = %s, lastName = %s, teacher = %d",
				firstName, lastName, teacherId);
	}

}
