/**
 * 
 */
package domainapp.modules.cmn.dom.api;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jayesh
 *
 */
@Slf4j
public class ConverterRegistry {

	public static ConverterRegistry INSTANCE = new ConverterRegistry();
	
	private Map<Class<?>, ITypeConverter<?>> registry;
	
	private ConverterRegistry() {
		registry = new HashMap<>();
		log.info("ConverterRegistry is successfully created");
	}
	
	/**
	 * 
	 * @param type
	 * @param converter
	 * @return null if no converter is associated previously with this key
	 */
	@SuppressWarnings("unchecked")
	public <T> ITypeConverter<T> register(Class<T> type, ITypeConverter<T> converter) {
		log.info(String.format("Register '%s' as '%s' in ConverterRegistry", converter, type));
		ITypeConverter<T> previousConverter = (ITypeConverter<T>) registry.put(type, converter);
		return previousConverter;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ITypeConverter<T> get(Class<T> type) {
		ITypeConverter<T> converter = (ITypeConverter<T>) registry.get(type);
		return converter;
	}
}
