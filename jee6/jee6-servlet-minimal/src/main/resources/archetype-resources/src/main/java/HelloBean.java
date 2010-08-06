#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;


@Named("helloBean")
@RequestScoped
public class HelloBean {

	private Person person = new Person();
	private List<Person> people;

	@DataRepository
	@Inject
	private EntityManager entityManager;
	
	public String getMessage() {
		return "Hello from JSF!";
	}

	public void savePerson() {
		//save the person
		entityManager.getTransaction().begin();		
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		
		//create a new person to show in the view
		person = new Person();
		
		//invalidate the list of people
		people = null;
	}

	public Person getPerson() {
		return person;
	}
	
	@Produces @Named
        @SuppressWarnings("unchecked")
	public List<Person> getPeople() {
		if (people == null) {
			people = entityManager.createQuery("select p from Person p")
					.getResultList();
		}
		return people;
	}	
}
