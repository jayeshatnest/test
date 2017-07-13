/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

/**
 * @author Jayesh
 *
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PluginType.class
)
public class PluginTypeRepository extends AbstractLookupByCodeRepository<PluginType>{

    public PluginTypeRepository() {
		super(PluginType.class);
	}
    
}
