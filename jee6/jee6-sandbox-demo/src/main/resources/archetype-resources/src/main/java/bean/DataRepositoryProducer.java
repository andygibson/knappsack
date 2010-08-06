#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import javax.ejb.Stateless;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${package}.qualifier.DataRepository;

/**
 * This is the stateless EJB that produced our {@link EntityManager} instance.
 * This EJB has an entity manager injected and we return it to CDI in our
 * {@link Produces} annotated method. We qualify the producer with the
 * {@link DataRepository} qualifier.
 * 
 * The Local interface isn't needed in a typical EJB 3.1 environment but CDI
 * cannot find no-local interface beans.
 * 
 */
@Stateless
public class DataRepositoryProducer implements DataRepositoryProducerLocal {
	
	
	private EntityManager entityManager;
	
	@Produces @DataRepository @ConversationScoped
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
