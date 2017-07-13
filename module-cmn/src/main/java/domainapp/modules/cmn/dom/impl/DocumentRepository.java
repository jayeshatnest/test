/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Document.class
)
public class DocumentRepository extends AbstractActivableLookupByCodeRepository<Document> {

    public DocumentRepository() {
		super(Document.class);
	}

    public List<Document> findByUser(final String user) {
        return repositoryService.allMatches(
                new QueryDefault<Document>(
                		Document.class,
                        "findByUser",
                        "user", user));
    }
    
	@Override
	protected Document create() {
		return new Document();
	}

	public Document create(String code, String name, String filename, Template template, String user) {
		
		Document document = super.createNoPersist(code, name);
		document.setFilename(filename);
		document.setTemplate(template);
		document.setUserCreation(user);
		return create(document);
	}
	
}
