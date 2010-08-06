#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.List;

import javax.ejb.Local;

import ${package}.model.Person;

/**
 * {@link Local} interface for the {@link PersonDao} EJB bean.
 * Shouldn't be needed under EJB 3.1 except Weld cannot lookup 
 * no-interface EJBs currently.
 * 
 */
@Local
public interface PersonDaoLocal {

	public abstract void savePerson(Person p);

	public abstract List<Person> getPeople();

}
