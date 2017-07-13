/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.InvokedOn;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;

import domainapp.modules.cmn.dom.api.IActivableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class representing base persistence capable entity containing common
 * properties presenting snapshot of activity detail like creation and
 * modification.
 * 
 * @author Jayesh
 *
 */
@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@Slf4j
public abstract class AbstractActivablePersistentEntity extends BasePersistableEntity implements IActivableEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "Enabled")
    @Getter @Setter
	private Boolean active;
    
    @Override
    public Boolean isActive() {
    	return getActive() != null ? getActive() : Boolean.TRUE;
    }
    
    //region > completed (action)
    @SuppressWarnings("serial")
	public static class EnableOrDisableDomainEvent extends org.apache.isis.applib.services.eventbus.ActionDomainEvent<AbstractActivablePersistentEntity> { }

    // TODO: @Jayesh :- Can we please move this somewhere else. Because of ActionInvocationContext 
    // is not allowing me to store objects in database since it is not serializable
    @Action(
            domainEvent = EnableOrDisableDomainEvent.class,
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public AbstractActivablePersistentEntity enableOrDisable() {
        setActive(!isActive());
        
        //
        // remainder of method just demonstrates the use of the ActionInvocationContext service
        //
        @SuppressWarnings("unused")
        final List<Object> allObjects = actionInvocationContext.getDomainObjects();

        log.debug("Enabled: "
                + actionInvocationContext.getIndex() +
                " [" + actionInvocationContext.getSize() + "]"
                + (actionInvocationContext.isFirst() ? " (first)" : "")
                + (actionInvocationContext.isLast() ? " (last)" : ""));

        // if invoked as a regular action, return this object;
        // otherwise return null (so go back to the list)
        return actionInvocationContext.getInvokedOn() == InvokedOn.COLLECTION? null: this;
    }

	protected <T extends AbstractActivablePersistentEntity> void initializeForCreate(T bean) {
		bean.setActive(Boolean.TRUE);
	}

	@Override
	public void jdoPreDelete() {
		throw new UnsupportedOperationException(String.format("Delete is not allowed on activable entity - %s", getClass().getName())); //$NON-NLS-1$
	}

    /**
     * public only so can be injected from integ tests
     */
    @javax.inject.Inject
    public transient ActionInvocationContext actionInvocationContext;
}
