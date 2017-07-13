/**
 * 
 */
package domainapp.modules.cmn.dom.api.jcl;

import org.apache.isis.applib.value.Blob;

import domainapp.modules.cmn.dom.api.IContext;
import domainapp.modules.cmn.dom.api.IResult;
import domainapp.modules.cmn.dom.impl.Plugin;
import domainapp.modules.cmn.dom.impl.PluginType;

/**
 * @author Jayesh
 *
 */
public interface IPluginService {
	
	/**
	 * @author Jayesh
	 *
	 */
	public interface Callback {
		
		/**
		 * @param context
		 */
		void success(Plugin plugin, IContext context);
		
		/**
		 * @param context
		 * @param e
		 */
		void error(Plugin plugin, IContext context, PluginException e);
	}
	
	/**
	 * @param context
	 * @return context
	 * @throws ServiceException
	 */
	IContext executePlugin(Plugin plugin, IContext context) throws PluginException;
	
	/**
	 * @param context
	 * @param callback
	 */
	void executePluginAsync(Plugin plugin, IContext context, Callback callback);

	/**
	 * @param jarFile
	 * @return
	 */
	IResult savePluginJarAndReadMetadata(Blob jarFile) throws PluginException;

	/**
	 * @param pluginMetadata
	 * @return
	 */
	IResult validatePluginClass(IResult pluginMetadata);

	/**
	 * @param pluginMetadata
	 * @param pluginType 
	 * @return
	 */
	Plugin addPlugin(IResult pluginMetadata, PluginType pluginType);

	/**
	 * @param plugin
	 * @return
	 */
	<T extends IContext> IPlugin<T> loadPlugin(Plugin plugin) throws PluginException;

	/**
	 * @param plugin
	 * @return
	 */
	void unloadPlugin(Plugin plugin) throws PluginException;
}
