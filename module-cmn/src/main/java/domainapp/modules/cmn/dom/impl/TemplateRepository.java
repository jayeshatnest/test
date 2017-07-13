/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.modules.cmn.util.DistributionFrequency;
import domainapp.modules.cmn.util.DistributionType;
import domainapp.modules.cmn.util.ReportType;
import domainapp.modules.cmn.util.TemplateType;

/**
 * @author Jayesh
 *
 */
@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Template.class
)
public class TemplateRepository extends AbstractActivableLookupByCodeRepository<Template> {

    public TemplateRepository() {
		super(Template.class);
	}
    
    public List<Template> findByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<Template>(
                		Template.class,
                        "findByUser",
                        "user", user));
    }
    
	@Override
	protected Template create() {
		return new Template();
	}

	public Template create(String code, String name,String subject,String templateFilename, Plugin plugin, 
			TemplateType templateType,String templateEngineCode,String user) {
		
		Template template = super.createNoPersist(code, name);
		template.setSubject(subject);
		template.setTemplateFilename(templateFilename);
		template.setPlugin(plugin);
		template.setTemplateType(templateType);
		template.setUserCreation(user);
		
		TemplateEngine templateEngine = templateEngineRepository.findByCode(templateEngineCode);
		template.setTemplateEngine(templateEngine);
		
		return create(template);
	}
	
	@Inject
	TemplateEngineRepository templateEngineRepository;
}
