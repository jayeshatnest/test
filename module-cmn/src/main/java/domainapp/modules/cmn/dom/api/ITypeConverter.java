/**
 * 
 */
package domainapp.modules.cmn.dom.api;

import java.io.Serializable;

/**
 * Specification for type converter
 * 
 * @author Jayesh
 */
public interface ITypeConverter<T> extends Serializable {

	/**
	 * @return class representing target type for converter
	 */
	Class<T> getType();
	
	/**
	 * returns null if given object is null
	 * @param value
	 * @return converted value in T
	 * @throws IllegalArgumentException if given object cannot be converted
	 */
	T convert(Object value);
	
	/**
	 * returns defaultValue if given object is null
	 * 
	 * @param value
	 * @param defaultValue
	 * @return converted value in T
	 * @throws IllegalArgumentException if given object cannot be converted
	 */
	T convert(Object value, T defaultValue);
}
