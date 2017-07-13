package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.util.ObjectContracts;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@javax.jdo.annotations.PersistenceCapable(
       identityType = IdentityType.DATASTORE,
       table = "CMN_DESTINATION_PROFILE_DEF"
)
@javax.jdo.annotations.DatastoreIdentity(
       strategy=javax.jdo.annotations.IdGeneratorStrategy.INCREMENT,
       column="id")
@javax.jdo.annotations.Version(
       strategy = VersionStrategy.VERSION_NUMBER,
       column = "version")
@javax.jdo.annotations.Inheritance(
		strategy = InheritanceStrategy.NEW_TABLE
		)
@javax.jdo.annotations.Queries({
       @javax.jdo.annotations.Query(
               name = "findByCode",
               value = "SELECT "
                       + "FROM domainapp.modules.cmn.dom.impl.DestinationProfile "
                       + "WHERE code.indexOf(:code) >= 0 "),
       @javax.jdo.annotations.Query(
               name = "findByUser",
               value = "SELECT "
                       + "FROM domainapp.modules.cmn.dom.impl.DestinationProfile "
                       + "WHERE user == :user "
   	)
})
@javax.jdo.annotations.Unique(name="DestinationProfile_code_UNQ", members = {"code"})
@DomainObject(
       objectType = "cmn.DestinationProfile",
       auditing = Auditing.ENABLED,
       bounded = true,
       updatedLifecycleEvent = DestinationProfile.UpdatedEvent.class
)
@Slf4j
public class DestinationProfile extends AbstractActivableLookupPersistentEntity implements Comparable<DestinationProfile> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3779236464924505306L;
	
	@MemberOrder(sequence="20")
	@Override
	public String getName() {
		return super.getName();
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true", name = "EMAIL")
	@Property(editing = Editing.DISABLED)
	@MemberOrder(sequence="30")
	@Getter @Setter
	String email;

	@javax.jdo.annotations.Column(allowsNull = "true", name = "FTP_URL")
	@PropertyLayout(named="FTP Server")
	@Property(editing = Editing.DISABLED)
	@MemberOrder(sequence="40")
	@Getter @Setter
	String url;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(DestinationProfile destinationProfile) {
        return ObjectContracts.compare(this, destinationProfile, "code");
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<DestinationProfile> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<DestinationProfile> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<DestinationProfile> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<DestinationProfile> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<DestinationProfile> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<DestinationProfile> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<DestinationProfile> {}
    //endregion

	
}
