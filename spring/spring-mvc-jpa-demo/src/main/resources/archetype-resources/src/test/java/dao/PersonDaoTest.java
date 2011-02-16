#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${package}.controller.DataInitializer;
import ${package}.dao.PersonDao;
import ${package}.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonDaoTest {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PersonDao personDao;

	@Autowired
	private DataInitializer dataInitializer;

	@Before
	public void prepareData() {
		dataInitializer.initData();
	}

	@Test
	public void shouldSaveAPerson() {
		Person p = new Person();
		p.setFirstName("Andy");
		p.setLastName("Gibson");
		personDao.save(p);
		Long id = p.getId();
		Assert.assertNotNull(id);
	}
	 
	@Test
	public void shouldLoadAPerson() {		
		Long template = dataInitializer.people.get(0);
		Person p = personDao.find(template);
		
		Assert.assertNotNull("Person not found!",p);
		Assert.assertEquals(template, p.getId());				
	}
	
	public void shouldListPeople() {
		List<Person> people = personDao.getPeople();
		Assert.assertEquals(DataInitializer.PERSON_COUNT, people.size());
		
	}
	
}
