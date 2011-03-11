#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import ${package}.qualifier.DataRepository;
import ${package}.model.Person;

/**
 * Simple PersonDao bean that handles persisting a person entity and returning a
 * list of people in the database. Implemented as an EJB for the transaction
 * facilities. 
 * 
 */
@Stateless
public class PersonDao {

	@Inject
	@DataRepository
	private EntityManager entityManager;

	public void savePerson(Person p) {
		entityManager.persist(p);
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPeople() {
		return entityManager.createQuery("select p from Person p")
				.getResultList();
	}
}
