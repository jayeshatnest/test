package domainapp.modules.cmn.dom.impl;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;

import domainapp.modules.cmn.ICommonDomainConstants;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        table = "CMN_COUNTRY"
)
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
                    + "FROM " + ICommonDomainConstants.PKG_COMMON_MODULE_IMPL + ".Country "
                    + "WHERE code.indexOf(:code) >= 0 "
	),
    @javax.jdo.annotations.Query(
            name = "findByName",
            value = "SELECT "
                    + "FROM " + ICommonDomainConstants.PKG_COMMON_MODULE_IMPL + ".Country "
                    + "WHERE name == :name "
	)
})
@javax.jdo.annotations.Unique(name="Country_code_UNQ", members = {"code"})
@DomainObject(
        objectType = "frd.country",
        auditing = Auditing.ENABLED,
        bounded = true,
        updatedLifecycleEvent = Country.UpdatedEvent.class
)
public class Country extends AbstractActivableLookupPersistentEntity implements Comparable<Country> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compareTo(Country o) {
		return 0;
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<Country> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<Country> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<Country> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<Country> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<Country> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<Country> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<Country> {}
    //endregion


}
