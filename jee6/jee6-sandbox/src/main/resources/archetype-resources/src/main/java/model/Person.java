#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Person class is the superclass for the {@link Student} and
 * {@link Teacher} classes that extend the person by adding information relevant
 * to those sub classes. This class implements the name information and includes
 * helper methods for displaying the name.
 * <p/>
 * It also demonstrates JPA inheritance using the joined table mechanism and a
 * discriminator column value.
 * 
 * @author Andy Gibson
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, length = 3, name = "PERSON_TYPE")
public abstract class Person extends BaseEntity {

	@Column(length = 25, nullable = false)
	@Size(max = 25)
	@NotEmpty(message = "First name is required")
	private String firstName;

	@Column(length = 25, nullable = false)
	@Size(max = 25)
	@NotEmpty(message = "Last name is required")
	private String lastName;

	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

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

	@Override
	public String getDisplayText() {
		return getName();
	}

	@Override
	public String toString() {

		String exp = "%s [id = %d firstName = %s ,lastName = %s]";
		return String.format(exp, super.toString(), getId(), getFirstName(),
				getLastName());
	}

	public String getOrderedName() {
		return lastName + ", " + firstName;
	}

}
