/**
 * 
 */
package domainapp.modules.cmn.dom.api;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Context for processing e.g. plug-in execution.
 * 
 * @author Jayesh
 */
public interface IContext extends Serializable {
	
	Set<UUID> uuidAlreadyUsed = Collections.synchronizedSet(new HashSet<>());
	
	/**
	 * @return unique ID of context
	 */
	UUID getId();
	
	/**
	 * @return name of context
	 */
	String getName();
	
	/**
	 * @return description associated with this context
	 */
	String getDescription();

	/**
	 * @param key
	 * @return value stored against given key
	 */
	Object get(String key);
	
	/**
	 * @param key
	 * @param value
	 * @return previous value associated with given key else null
	 */
	Object set(String key, Object value);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws ClassCastException if value stored against given key belongs to different type
	 */
	<T> T get(String key, T defaultValue);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see ITypeConverter
	 * @see ConvertorFactory
	 */
	<T> T get(String key, T defaultValue, Class<T> typeClass);
	
	/**
	 * @param key
	 * @param value TODO
	 * @param typeClass
	 * @return previous value associated with given key else null
	 */
	<T> Object set(String key, Object value, Class<T> typeClass);
}
