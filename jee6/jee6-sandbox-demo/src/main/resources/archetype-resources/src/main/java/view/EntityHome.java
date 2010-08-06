#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import ${package}.model.BaseEntity;

/**
 * Responsible for loading/creating and keeping hold of an entity instance. This
 * is a minimalist version of the Seam EntityHome class.
 * 
 * @author Andy Gibson
 * 
 * @param <T>
 *            Type of entity
 */
public abstract class EntityHome<T extends BaseEntity> {

	private Long id;
	private T entity;

	@Inject
	private Conversation conversation;

	/**
	 * Helper init method called on page loads. Calls the
	 * {@link EntityHome${symbol_pound}doInit()} method which can be overriden by subclases to
	 * provide further initialization. If the custom initialization failes
	 * (returns false) then we don't proceed with starting the conversation if
	 * requested.
	 * 
	 * @param startConversation
	 *            Flag to indicate whether to start the conversion once
	 *            initialized.
	 */
	public void init(boolean startConversation) {
		if (doInit()) {
			if (startConversation && conversation.isTransient()) {
				conversation.begin();
			}
		}
	}

	/**
	 * Override this method to implement your own initialization.
	 * 
	 * @return true if initialization was successful
	 */
	protected boolean doInit() {
		return true;
	}

	public boolean isIdAvailable() {
		return id != null;
	}

	/**
	 * Indicates whether this is a new entity or an existing (managed) one. If
	 * there is no Id assigned to the entity then we assume it has not yet been
	 * saved to the database.
	 * 
	 * @return true if this entity has already been persisted
	 */
	public boolean isManaged() {

		return getEntity().getId() != null;
	}

	/**
	 * Lazy loads the entity instance using the {@link EntityHome${symbol_pound}fetchEntity()}
	 * method.
	 * 
	 * @return The entity
	 */
	public T getEntity() {
		if (entity == null) {
			entity = fetchEntity();
		}
		return entity;
	}

	/**
	 * Fetches the entity or creates a new one depending on whether the Id
	 * property is set or not. The {@link EntityHome${symbol_pound}doLoadEntity()} and
	 * {@link EntityHome${symbol_pound}doCreateEntity()} methods should be overriden in
	 * subclasses to provide those functions.
	 * 
	 * @return the entity of type T
	 */
	protected T fetchEntity() {
		if (isIdAvailable()) {
			return doLoadEntity();
		} else {
			return doCreateEntity();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Override this method to create and initialize a new entity which is
	 * returned to the called.
	 * 
	 * @return The created entity
	 */
	protected abstract T doCreateEntity();

	/**
	 * Override this method to load an entity from a database or other mechanism
	 * and return the entity from this method.
	 * 
	 * @return The loaded entity
	 */
	protected abstract T doLoadEntity();

	/**
	 * We provide this getter to for the benefit of subclasses.
	 * 
	 * @return The injected conversation instance
	 */
	public Conversation getConversation() {
		return conversation;
	}
}
