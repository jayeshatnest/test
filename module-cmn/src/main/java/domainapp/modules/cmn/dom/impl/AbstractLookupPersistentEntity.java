/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;

import domainapp.modules.cmn.dom.api.ILookupEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class representing abstract lookup persistence capable entity.
 * 
 * @author Jayesh
 * @see BasePersistableEntity
 * @see AbstractActivableLookupPersistentEntity
 */
@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class AbstractLookupPersistentEntity extends BasePersistableEntity implements ILookupEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@javax.jdo.annotations.Column(allowsNull = "false", length = 100)
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private String code;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 500)
    @Property(editing = Editing.ENABLED)
    @Getter @Setter
    private String name;
    
    // FIXME: [JP] This is not the right way
    private String prepend = "";
    
    /**
     * 
     */
    public AbstractLookupPersistentEntity() {
    	this(null);
    }
    
    /**
     * @param prepend
     */
    public AbstractLookupPersistentEntity(String prepend) {
    	this.prepend = prepend;
    }
    
    public String title() {
    	return prepend != null ? prepend + name : name;
    }
	
}
