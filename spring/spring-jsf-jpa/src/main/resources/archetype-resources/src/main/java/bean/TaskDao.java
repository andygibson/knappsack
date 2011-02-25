#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${package}.model.Task;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(Task task) {
		entityManager.persist(task);
	}

	@SuppressWarnings("unchecked")
	public List<Task> list() {
		return entityManager.createQuery("select t from Task t")
				.getResultList();
	}

}
