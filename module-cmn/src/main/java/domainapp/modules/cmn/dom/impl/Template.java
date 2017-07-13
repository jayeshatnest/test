package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.modules.cmn.util.TemplateType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@javax.jdo.annotations.PersistenceCapable(
       identityType = IdentityType.DATASTORE,
       table = "CMN_TEMPLATE_DEF"
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
                       + "FROM domainapp.modules.cmn.dom.impl.Template "
                       + "WHERE code.indexOf(:code) >= 0 "),
       @javax.jdo.annotations.Query(
               name = "findByUser",
               value = "SELECT "
                       + "FROM domainapp.modules.cmn.dom.impl.Template "
                       + "WHERE userCreation == :user "
   	)
})
@javax.jdo.annotations.Unique(name="Template_code_UNQ", members = {"code"})
@DomainObject(
       objectType = "cmn.Template",
       auditing = Auditing.ENABLED,
       bounded = true,
       updatedLifecycleEvent = Template.UpdatedEvent.class
)
@Slf4j
public class Template extends AbstractActivableLookupPersistentEntity implements Comparable<Template> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MemberOrder(sequence="20")
	@Override
	public String getName() {
		return super.getName();
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true", name = "TEMPLATE_TYPE")
	@MemberOrder(sequence="30")
	@Getter @Setter
	TemplateType templateType;
	
	// TODO: AJ: Not sure about this field
	@javax.jdo.annotations.Column(allowsNull = "true", name = "TEMPLATE_FILE_NAME")
	@MemberOrder(sequence="40")
	@Getter @Setter
	String templateFilename;
	
	@javax.jdo.annotations.Column(allowsNull = "true", name = "TEMPLATE_ENGINE_ID")
	@MemberOrder(sequence="50")
	@Getter @Setter
	TemplateEngine templateEngine;
	
	@javax.jdo.annotations.Column(allowsNull = "true", name = "EMAIL_SUBJECT")
	@MemberOrder(sequence="60")
	@PropertyLayout(named="Email Subject")
	@Getter @Setter
	String subject;
	
	@javax.jdo.annotations.Column(allowsNull = "true", name = "PLUGIN")
	@MemberOrder(sequence="70")
	@Getter @Setter
	Plugin plugin;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(Template template) {
        return ObjectContracts.compare(this, template, "code");
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<Template> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<Template> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<Template> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<Template> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<Template> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<Template> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<Template> {}
    //endregion

	
}
