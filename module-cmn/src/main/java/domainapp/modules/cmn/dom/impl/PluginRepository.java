/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

/**
 * @author Jayesh
 *
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Plugin.class
)
public class PluginRepository extends AbstractLookupByCodeRepository<Plugin> {

    public PluginRepository() {
		super(Plugin.class);
	}
    
    public Map<String, Plugin> getAllPlugins() {
		Map<String, Plugin> plugins = new HashMap<String, Plugin>();
		for (Plugin plugin : repositoryService.allInstances(Plugin.class, 0)) {
			plugins.put(plugin.getCode(), plugin);
		}
		return plugins;
	}
    
}
