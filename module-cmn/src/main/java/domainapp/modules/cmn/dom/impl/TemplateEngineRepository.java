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
        repositoryFor = TemplateEngine.class
)
public class TemplateEngineRepository extends AbstractActivableLookupByCodeRepository<TemplateEngine> {

    public TemplateEngineRepository() {
		super(TemplateEngine.class);
	}
    
	@Override
	protected TemplateEngine create() {
		return new TemplateEngine();
	}

	public TemplateEngine create(String code, String name) {
		
		TemplateEngine templateEngine = super.createNoPersist(code, name);
		
		return create(templateEngine);
	}
	
}
