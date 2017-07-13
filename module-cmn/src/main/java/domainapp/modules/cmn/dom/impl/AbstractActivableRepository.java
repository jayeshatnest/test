/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.cmn.dom.api.IActivableEntity;

/**
 * Abstract repository implementation for Active-able entities e.g. defintion, static data, etc.
 * 
 * @author Jayesh
 */
public abstract class AbstractActivableRepository<T extends IActivableEntity> extends AbstractRepository<T> {

    public AbstractActivableRepository(Class<T> clazz) {
		super(clazz);
	}

    /**
     * @return all active entities
     */
    public List<T> findAllActive() {
        return repositoryService.allMatches(
                new QueryDefault<T>(
                		getClazz(),
                        "findByActive",
                        "active", Boolean.TRUE));
    }
    
    /**
     * If given param active=null then it is default to true
     * 
     * @param active true|false
     * @return IActivableEntity
     */
    public T create(Boolean active) {
        final T bean = super.create();
        bean.setActive(active == null ? Boolean.TRUE : active);
        return create(bean);
    }
    
    /**
     * If given param active=null then it is default to true
     * 
     * @param active true|false
     * @return IActivableEntity
     */
    public T createNoPersist(Boolean active) {
        final T bean = super.create();
        bean.setActive(active == null ? Boolean.TRUE : active);
        return bean;
    }

    @javax.inject.Inject
    protected RepositoryService repositoryService;
    
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

}
