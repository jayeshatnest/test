/**
 * 
 */
package domainapp.modules.cmn.dom.impl;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.isis.applib.services.config.ConfigurationService;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract implementation for all repositories.
 * 
 * @author Jayesh
 */
@Slf4j
public abstract class AbstractRepository<T> {

	@Getter
	private Class<T> clazz;

	private int MAX_RECORDS = -1;
	
	/**
	 * @param clazz Class of generic type T
	 */
	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}

    @SuppressWarnings("unchecked")
	public List<T> listAll() {
    	PersistenceManager pm = isisJdoSupport.getJdoPersistenceManager();
    	Query query = pm.newQuery();
    	query.setClass(clazz);
    	query.setResultClass(clazz);
//    	query.addExtension("datanucleus.query.resultSizeMethod", "count");
//    	query.addExtension("datanucleus.query.resultCacheType", "weak");
    	if(MAX_RECORDS == -1) {
    		MAX_RECORDS = Integer.parseInt(confgurationService.getProperty("max.records", "10000"));
    	}
    	query.setRange(0, MAX_RECORDS);
    	return (List<T>) query.execute();
//        return repositoryService.allInstances(clazz, 0, 10);
    }
    
    public Long countAll() {
		PersistenceManager pm = isisJdoSupport.getJdoPersistenceManager();
		Query query = pm.newQuery("SELECT FROM " + getClazz().getName());
		query.setResultClass(Long.class);
		query.setResult("count(this)");
		Object count = query.execute();
		if(count != null) {
			return Long.parseLong(String.valueOf(count));
		}
		return 0L;
//		query.addExtension("datanucleus.query.resultSizeMethod", "count");
    }
    
    protected T create() {
    	try {
			return getClazz().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error(String.format("Unexpected error has occurred while creating instance of class '%s'", getClazz().getName()), e);
			throw new IllegalStateException("Unexpected system error. Check server log for more detail", e);
		}
    }

    /**
	 * Purpose of this method is to necessary service into given bean and
	 * persist as well in store.
	 * 
	 * @param bean
	 * @return
	 */
    public T create(T bean) {
        serviceRegistry.injectServicesInto(bean);
        repositoryService.persist(bean);
        return bean;
    }

	/**
	 * @param bean
	 */
	public void update(T bean) {
		repositoryService.persist(bean);
	}
    
    /**
     * Remove from store
     * @param bean
     */
    public void remove(T bean) {
    	repositoryService.remove(bean);
    }

    @javax.inject.Inject
    protected RepositoryService repositoryService;
    
    @javax.inject.Inject
    protected ServiceRegistry2 serviceRegistry;

	@Inject
	protected IsisJdoSupport isisJdoSupport;
	
	@Inject
	protected ConfigurationService confgurationService;
}
