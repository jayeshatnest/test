/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.security.UserMemento;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.services.user.UserService;

import domainapp.modules.cmn.util.ReportType;
import domainapp.modules.cmn.util.Util;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        table = "CMN_REPORTING_PROFILE_DEF"
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
                        + "FROM domainapp.modules.cmn.dom.impl.ReportingProfile "
                        + "WHERE code.indexOf(:code) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "findByUser",
                value = "SELECT "
                        + "FROM domainapp.modules.cmn.dom.impl.ReportingProfile "
                        + "WHERE userCreation == :user "
    	)
})
@javax.jdo.annotations.Unique(name="ReportingProfile_code_UNQ", members = {"code"})
@DomainObject(
        objectType = "cmn.ReportingProfile",
        auditing = Auditing.ENABLED,
        bounded = true,
        updatedLifecycleEvent = ReportingProfile.UpdatedEvent.class
)
@Slf4j
public class ReportingProfile extends DistributionProfile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@javax.jdo.annotations.Column(allowsNull = "true", name = "TEMPLATE")
	@MemberOrder(sequence="90")
	@Getter @Setter
	Template template;


	@javax.jdo.annotations.Persistent(table="CMN_REPORTING_PROFILE_DOCUMENT_MAP")
    @javax.jdo.annotations.Join(column="REPORTING_PROFILE_ID")
    @javax.jdo.annotations.Element(column="DOCUMENT_ID")
	@CollectionLayout(render=RenderType.EAGERLY)
    @Getter @Setter
    private List<Document> documents;
	
	@Property(notPersisted=true,editing = Editing.ENABLED)
	@Getter @Setter
	public ReportType reportType;
	
	@Action(invokeOn=InvokeOn.OBJECT_ONLY)
    public ReportingProfile addDocuments(
            @ParameterLayout(named="Document")
            final Document document) {
		List<Document> docList = this.getDocuments();
		if(docList==null) {
			docList = new ArrayList<>();
		}
		docList.add(document);
        return this;
    }
	
	public List<Document> choices0AddDocuments() {
		List<Document> documents = new ArrayList<>();
		
		UserMemento user = userService.getUser();
		
		if(Util.isAdmin(user)) {
			documents = docRepository.listAll();
		} else {
			documents = docRepository.findByUser(user.getName());
		}
		
		return documents;
	}

	@Inject
	UserService userService;
    
	@Inject
	DocumentRepository docRepository;
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<ReportingProfile> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<ReportingProfile> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<ReportingProfile> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<ReportingProfile> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<ReportingProfile> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<ReportingProfile> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<ReportingProfile> {}
    //endregion
}
