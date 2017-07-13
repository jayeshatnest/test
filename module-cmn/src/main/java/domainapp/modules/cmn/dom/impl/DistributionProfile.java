/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.modules.cmn.util.DistributionFrequency;
import domainapp.modules.cmn.util.DistributionType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jayesh
 *
 */

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@Slf4j
public abstract class DistributionProfile extends AbstractActivableLookupPersistentEntity implements Comparable<DistributionProfile> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MemberOrder(sequence="20")
	@Override
	public String getName() {
		return super.getName();
	}
	
	@PropertyLayout(hidden=Where.ALL_TABLES)
	@Override
	public String getCode() {
		return super.getCode();
	}
	
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Owner")
	@MemberOrder(sequence="30")
	@Override
	public String getUserCreation() {
		return super.getUserCreation();
	};

	@javax.jdo.annotations.Column(allowsNull = "false", name = "PROFILE_ID")
    @Property(editing = Editing.DISABLED)
    @MemberOrder(sequence="40")
    @Getter @Setter
    private Profile profile;
   
    @javax.jdo.annotations.Column(allowsNull = "true", length = 255)
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(multiLine=3,hidden=Where.ALL_TABLES)
    @MemberOrder(sequence="50")
    @Getter @Setter
    private String description;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @MemberOrder(sequence="60")
    @Getter @Setter
    private DistributionFrequency frequency;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @MemberOrder(sequence="70")
    @Getter @Setter
    private DistributionType distributionType;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @MemberOrder(sequence="80")
    @Getter @Setter
    private DestinationProfile destinationProfile;
    
    @PropertyLayout(hidden=Where.ALL_TABLES)
    public Boolean getActive() {
    	return super.getActive();
    }
    
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(DistributionProfile distribution) {
        return ObjectContracts.compare(this, distribution, "code");
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<DistributionProfile> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<DistributionProfile> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<DistributionProfile> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<DistributionProfile> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<DistributionProfile> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<DistributionProfile> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<DistributionProfile> {}
    //endregion
    
	//region > object-level validation
    /**
     * Prevent user from viewing another user's data.
     */
    public boolean hidden() {
        // previously we manually checked that the user couldn't modify an object owned by some other user.
        // however, with application tenancy support this is automatically taken care of by Isis.
        return false;
    }

    /**
     * Prevent user from modifying any other user's data.
     */
    public String disabled(final Identifier.Type identifierType){
        // previously we manually checked that the user couldn't modify an object owned by some other user.
        // however, with application tenancy support this is automatically taken care of by Isis.
        return null;
    }

    /**
     * In a real app, if this were actually a rule, then we'd expect that
     * invoking the {@link #completed() done} action would clear the {@link #getDueBy() dueBy}
     * property (rather than require the user to have to clear manually).
     */
    public String validate() {
//        if(isComplete() && getDueBy() != null) {
//            return "Due by date must be set to null if item has been completed";
//        }
        return null;
    }
    //endregion
    
    //region > lifecycle callbacks
    public void created() {
        log.debug("lifecycle callback: created: " + this.toString());
    }
    public void loaded() {
    	log.debug("lifecycle callback: loaded: " + this.toString());
    }
    public void persisting() {
    	log.debug("lifecycle callback: persisting: " + this.toString());
    }
    public void persisted() {
    	log.debug("lifecycle callback: persisted: " + this.toString());
    }
    public void updating() {
    	log.debug("lifecycle callback: updating: " + this.toString());
    }
    public void updated() {
    	log.debug("lifecycle callback: updated: " + this.toString());
    }
    public void removing() {
    	log.debug("lifecycle callback: removing: " + this.toString());
    }
    public void removed() {
    	log.debug("lifecycle callback: removed: " + this.toString());
    }
    //endregion
	
    /*@Action(invokeOn=InvokeOn.OBJECT_AND_COLLECTION)
    @ActionLayout(named="Create")
    public DistributionProfile create(
    		@org.apache.isis.applib.annotation.Parameter
    		@ParameterLayout(named="Profile")
    		Profile profile, 
    		@org.apache.isis.applib.annotation.Parameter
    		@ParameterLayout(named="Code")
    		String code, 
    		@org.apache.isis.applib.annotation.Parameter
    		@ParameterLayout(named="Name")
    		String name, 
    		@org.apache.isis.applib.annotation.Parameter(optionality=Optionality.OPTIONAL)
    		@ParameterLayout(named="Description", labelPosition=LabelPosition.TOP, multiLine=3)
    		String description, 
    		@org.apache.isis.applib.annotation.Parameter(regexPattern="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    		@ParameterLayout(named="E-mail")
    		String email,
    		@org.apache.isis.applib.annotation.Parameter(regexPattern="^(sftp|ftp|ftps)://", optionality=Optionality.OPTIONAL)
    		@ParameterLayout(named="FTP URL")
    		String url,
    		@org.apache.isis.applib.annotation.Parameter(optionality=Optionality.OPTIONAL)
    		@ParameterLayout(named="Task to be done")
    		String taskToBeDone
    		) {
    	
    	String code = profile.getCode()+"_"+taskToBeDone;
    	distributionProfileRepository.create(profile, code, name, description, email, url,taskToBeDone);
    	return this;
    }
    
    @Inject
    DistributionProfileRepository distributionProfileRepository;
    */
}
