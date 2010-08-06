#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * {@link Local} interface for the {@link DataRepositoryProducer} EJB bean.
 * Shouldn't be needed under EJB 3.1 except Weld has problems looking up
 * no-interface EJBs.
 * 
 */
@Local
public interface DataRepositoryProducerLocal {

	public EntityManager getEntityManager();

	public void setEntityManager(EntityManager entityManager);
}
