/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.cmn.dom.api.ILookupEntity;

/**
 * @author Jayesh
 *
 */
public abstract class AbstractLookupByCodeRepository<T extends ILookupEntity> extends AbstractRepository<T> {

    public AbstractLookupByCodeRepository(Class<T> clazz) {
		super(clazz);
	}

    public T findByCode(final String code) {
        return repositoryService.firstMatch(
                new QueryDefault<T>(
                		getClazz(),
                        "findByCode",
                        "code", code));
    }

    public T create(String code, String name) {
        final T bean = super.create();
        bean.setCode(code);
        bean.setName(name);
        return super.create(bean);
    }

    public T createNoPersist(String code, String name) {
        final T bean = super.create();
        bean.setCode(code);
        bean.setName(name);
        return bean;
    }

    @javax.inject.Inject
    protected RepositoryService repositoryService;

}
