/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.ObjectCreatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectLoadedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistedEvent;
import org.apache.isis.applib.services.eventbus.ObjectPersistingEvent;
import org.apache.isis.applib.services.eventbus.ObjectRemovingEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatedEvent;
import org.apache.isis.applib.services.eventbus.ObjectUpdatingEvent;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.modules.cmn.dom.api.ILookupEntity;
import domainapp.modules.cmn.dom.api.jcl.IPluginService;
import domainapp.modules.cmn.dom.api.jcl.PluginException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jayesh
 *
 */
@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        table = "CMN_PLUGIN_DEF"
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
                        + "FROM domainapp.modules.cmn.dom.impl.Plugin "
                        + "WHERE code.indexOf(:code) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "findAll",
                value = "SELECT "
                        + "FROM domainapp.modules.cmn.dom.impl.Plugin")
})
@javax.jdo.annotations.Unique(name="Plugin_code_UNQ", members = {"code"})
@DomainObject(
        objectType = "cmn.Plugin",
        auditing = Auditing.ENABLED,
        bounded = true,
        updatedLifecycleEvent = Plugin.UpdatedEvent.class
)

public class Plugin extends AbstractLookupPersistentEntity implements Comparable<Plugin> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@javax.jdo.annotations.Column(allowsNull = "true", length = 255)
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(multiLine=3)
    @Getter @Setter
    private String description;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(named = "Class", describedAs = "Class name of plugin to be loaded from Jar file")
    @Getter @Setter
	private String className;
	
    @javax.jdo.annotations.Column(allowsNull = "false", length = 255)
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(named = "Jar", describedAs = "Jar file name to be loaded dynamically from where plugin class to be loaded")
    @Getter @Setter
	private String jarFileName;
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(named = "Type", describedAs = "Plugin type")
    @Getter @Setter
	private PluginType type;

    /**
     * 
     */
    public Plugin() {
		super("Plugin: ");
	}

	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(Plugin plugin) {
        return ObjectContracts.compare(this, plugin, "code", "className", "jarFileName", "type");
	}
	
	//region > lifecycle events
	@SuppressWarnings("serial") public static class CreatedEvent extends ObjectCreatedEvent<Profile> {}
	@SuppressWarnings("serial") public static class LoadedEvent extends ObjectLoadedEvent<Profile> {}
	@SuppressWarnings("serial") public static class PersistedEvent extends ObjectPersistedEvent<Profile> {}
	@SuppressWarnings("serial") public static class PersistingEvent extends ObjectPersistingEvent<Profile> {}
	@SuppressWarnings("serial") public static class UpdatedEvent extends ObjectUpdatedEvent<Profile> {}
	@SuppressWarnings("serial") public static class UpdatingEvent extends ObjectUpdatingEvent<Profile> {}
	@SuppressWarnings("serial") public static class RemovingEvent extends ObjectRemovingEvent<Profile> {}
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
        return null;
    }
    //endregion
    
    @Action(semantics = SemanticsOf.SAFE, invokeOn=InvokeOn.COLLECTION_ONLY)
    public void reloadPlugins() {
    	System.out.println("Unload plugin function called. Unload the plugins here and load it again.");
    	final List<Object> selectedRoles = actionInvocationContext.getDomainObjects();
    	for (Object object : selectedRoles) {
			Plugin plugin = (Plugin) object;
			try {
				pluginService.unloadPlugin(plugin);
				pluginService.loadPlugin(plugin);
			} catch (PluginException e) {
				e.printStackTrace();
			}
		}
    }

    @Inject
    IPluginService pluginService;
    
    @javax.inject.Inject
    public ActionInvocationContext actionInvocationContext;
}
