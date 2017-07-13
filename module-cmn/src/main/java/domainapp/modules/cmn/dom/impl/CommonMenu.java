/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.io.IOException;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.cmn.dom.api.IResult;
import domainapp.modules.cmn.dom.api.jcl.IPluginService;
import domainapp.modules.cmn.dom.api.jcl.PluginException;
import lombok.extern.slf4j.Slf4j;

/**
 * Admin menu contributed by Common module
 * 
 * @author Jayesh
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Admin",
        menuOrder = "1",
        menuBar = MenuBar.SECONDARY
)
@Slf4j
public class CommonMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Profile> profiles() {
        return profileRepository.listAll();
    }

    /*@Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<DistributionProfile> distributonProfile() {
        return distributionProfileRepository.listAll();
    }*/

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<Plugin> plugins() {
        return pluginRepository.listAll();
    }

    @SuppressWarnings("serial")
	public static class CreatePluginDomainEvent extends ActionDomainEvent<CommonMenu> {}
    
    @Action(domainEvent = CreatePluginDomainEvent.class, typeOf = Plugin.class, semantics = SemanticsOf.NON_IDEMPOTENT)
    @MemberOrder(sequence = "5")
    public Plugin addPlugin(
            @ParameterLayout(named="Select plugin jar file to load", labelPosition = LabelPosition.TOP)
            final Blob jarFile
            
            ) throws IOException {
    	final String contextMethod = "addPlugin";
    	try {
    		// first save jar file
    		IResult pluginMetadata = pluginService.savePluginJarAndReadMetadata(jarFile);
    		if(!pluginMetadata.isSuccess()) {
    			messageService.raiseError(pluginMetadata.getError(), CommonMenu.class, contextMethod);
    			return null;
    		}
    		String type = pluginMetadata.get("pluginType", null);
    		PluginType pluginType = pluginTypeRepository.findByCode(type);
    		if(pluginType == null) {
    			messageService.raiseError(TranslatableString.tr("Plugin type with code '{plugin-type}' cannot be found", "plugin-type", type), CommonMenu.class, contextMethod);
    			log.error(String.format("Plugin type with code '%s' cannot be found in system", type)); 
    			return null;
    		}
    		pluginMetadata = pluginService.validatePluginClass(pluginMetadata);
    		if(!pluginMetadata.isSuccess()) {
    			messageService.raiseError(pluginMetadata.getError(), CommonMenu.class, contextMethod);
    			return null;
    		}
    		Plugin plugin = pluginService.addPlugin(pluginMetadata, pluginType);
    		messageService.informUser(TranslatableString.tr("New plugin '{class}' of type ${plugin-type} added successfully with below detail:", "plugin-type", type, "class", plugin.getClassName()), CommonMenu.class, contextMethod);
    		return plugin;
    	} catch (PluginException e) {
    		log.error("Plugin exception occurred while adding plugin", e);
			messageService.raiseError(TranslatableString.tr("Unexpeced plugin error has occurred while adding plugin. Error: {error}", "error", e.getMessage()), CommonMenu.class, contextMethod);
			return null;
    	}
    }

    @javax.inject.Inject
    ProfileRepository profileRepository;

//    @javax.inject.Inject
//    private DistributionProfileRepository distributionProfileRepository;

    @javax.inject.Inject
    private PluginRepository pluginRepository;

    @javax.inject.Inject
    private PluginTypeRepository pluginTypeRepository;

    @javax.inject.Inject
    private IPluginService pluginService;

    @javax.inject.Inject
    private MessageService messageService;

}
