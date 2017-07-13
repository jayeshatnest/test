/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.cmn.dom.api.IActivableEntity;
import domainapp.modules.cmn.dom.api.ILookupEntity;


/**
 * @author Jayesh
 *
 */
public abstract class AbstractActivableLookupByCodeRepository<T extends ILookupEntity & IActivableEntity> extends AbstractActivableRepository<T> {

    public AbstractActivableLookupByCodeRepository(Class<T> clazz) {
		super(clazz);
	}

    public T findByCode(final String code) {
        return repositoryService.firstMatch(
                new QueryDefault<T>(
                		getClazz(),
                        "findByCode",
                        "code", code));
    }
    
    public T findByName(final String name) {
        return repositoryService.firstMatch(
                new QueryDefault<T>(
                		getClazz(),
                        "findByName",
                        "name", name));
    }
    
//	public T findByExchangeCode(final String exchangeCode) {
//        return repositoryService.firstMatch(
//        		new QueryDefault<T>(
//                		getClazz(),
//                        "findByExchangeCode",
//                        "exchangecode", exchangeCode));
//    }

    public T create(String code, String name) {
        final T bean = super.createNoPersist((Boolean)null);
        bean.setCode(code);
        bean.setName(name);
        return super.create(bean);
    }

    public T createNoPersist(String code, String name) {
        final T bean = super.createNoPersist((Boolean)null);
        bean.setCode(code);
        bean.setName(name);
        return bean;
    }

    @javax.inject.Inject
    protected RepositoryService repositoryService;

}
