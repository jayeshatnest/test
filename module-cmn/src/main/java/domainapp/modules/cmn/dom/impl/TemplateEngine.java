package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
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
       table = "CMN_TEMPLATE_ENGINE_DEF"
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
                       + "FROM domainapp.modules.cmn.dom.impl.TemplateEngine "
                       + "WHERE code.indexOf(:code) >= 0 ")
})
@javax.jdo.annotations.Unique(name="TemplateEngine_code_UNQ", members = {"code"})
@DomainObject(
       objectType = "cmn.TemplateEngine",
       auditing = Auditing.ENABLED,
       bounded = true,
       updatedLifecycleEvent = TemplateEngine.UpdatedEvent.class
)
@Slf4j
public class TemplateEngine extends AbstractActivableLookupPersistentEntity implements Comparable<TemplateEngine> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO: AJ: Not sure about this field
	/*@javax.jdo.annotations.Column(allowsNull = "true", name = "TEMPLATE_ENGINE_TOOL")
	@MemberOrder(sequence="30")
	@Getter @Setter
	String templateEngineTool;*/
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(TemplateEngine templateEngine) {
        return ObjectContracts.compare(this, templateEngine, "code");
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<TemplateEngine> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<TemplateEngine> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<TemplateEngine> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<TemplateEngine> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<TemplateEngine> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<TemplateEngine> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<TemplateEngine> {}
    //endregion

	
}
