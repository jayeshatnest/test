/**
 * 
 */
package domainapp.modules.cmn.dom.impl.jcl;

import java.util.HashMap;
import java.util.UUID;

import domainapp.modules.cmn.dom.api.ConverterRegistry;
import domainapp.modules.cmn.dom.api.IContext;
import domainapp.modules.cmn.dom.api.ITypeConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Default implementation of IContext
 * 
 * @author Jayesh
 */
public class DefaultContext extends HashMap<String, Object> implements IContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param name
	 * @param descrption
	 * @return
	 */
	public static IContext newContext(String name, String descrption) {
		DefaultContext context = new DefaultContext();
		context.name = name;
		context.description = descrption;
		return context;
	}
	
	@Getter
	private UUID id;
	
	@Getter 
	@Setter(AccessLevel.PROTECTED)
	protected String name;
	
	@Getter
	@Setter(AccessLevel.PROTECTED)
	private String description;
	
	/**
	 * private constructor
	 */
	protected DefaultContext() {
		initializeIdentifier();
	}

	/**
	 * 
	 */
	private void initializeIdentifier() {
		UUID randomUUID = null;
		do {
			randomUUID = UUID.randomUUID();
		}
		while(uuidAlreadyUsed.contains(randomUUID));
		uuidAlreadyUsed.add(randomUUID);
		id = randomUUID;
	}
	
	@Override
	public Object get(String key) {
		return super.get(key);
	}

	/* (non-Javadoc)
	 * @see domainapp.modules.cmn.dom.api.jcl.IContext#get(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, T defaultValue) {
		Object object = super.get(key);
		if(object == null) {
			return defaultValue;
		}
		return (T)object;
	}

	@Override
	public <T> T get(String key, T defaultValue, Class<T> typeClass) {
		try {
			// first attempt with standard API
			T result = get(key, defaultValue);
			return result;
		} catch (ClassCastException e) {
			// if class cast exception observed then make use of typeConverter
			ITypeConverter<T> typeConverter = ConverterRegistry.INSTANCE.get(typeClass);
			if(typeConverter == null) {
				throw e;
			}
			T result = typeConverter.convert(get(key), defaultValue);
			return result;
		}
	}

	@Override
	public Object set(String key, Object value) {
		return put(key, value);
	}

	@Override
	public <T> Object set(String key, Object value, Class<T> typeClass) {
		ITypeConverter<T> converter = ConverterRegistry.INSTANCE.get(typeClass);
		if(converter == null) {
			throw new IllegalStateException(String.format("Converter for '%s' type not found!", typeClass));
		}
		T convertedValue = converter.convert(value);
		return set(key, convertedValue);
	}

}
