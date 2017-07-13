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
import org.apache.isis.applib.annotation.Where;
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
       table = "CMN_DOCUMENT_DEF"
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
                       + "FROM domainapp.modules.cmn.dom.impl.Document "
                       + "WHERE code.indexOf(:code) >= 0 "),
       @javax.jdo.annotations.Query(
               name = "findByUser",
               value = "SELECT "
                       + "FROM domainapp.modules.cmn.dom.impl.Document "
                       + "WHERE userCreation == :user "
   	)
})
@javax.jdo.annotations.Unique(name="Document_code_UNQ", members = {"code"})
@DomainObject(
       objectType = "cmn.Document",
       auditing = Auditing.ENABLED,
       bounded = true,
       updatedLifecycleEvent = Document.UpdatedEvent.class
)
@Slf4j
public class Document extends AbstractActivableLookupPersistentEntity implements Comparable<Document> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3779236464924505306L;

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
	
	@javax.jdo.annotations.Column(allowsNull = "true", name = "TEMPLATE_ID")
	@Property(editing = Editing.ENABLED)
	@MemberOrder(sequence="30")
	@Getter @Setter
	Template template;

	@javax.jdo.annotations.Column(allowsNull = "true", name = "FILENAME")
	@Property(editing = Editing.ENABLED)
	@MemberOrder(sequence="40")
	@Getter @Setter
	String filename;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(Document doc) {
        return ObjectContracts.compare(this, doc, "code");
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<Document> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<Document> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<Document> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<Document> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<Document> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<Document> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<Document> {}
    //endregion

	
}
