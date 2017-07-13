/**
 * 
 */
package domainapp.modules.cmn.dom.api.jcl;

import domainapp.modules.cmn.dom.api.IContext;

/**
 * 
 * @author Jayesh
 *
 */
public interface IPlugin<T extends IContext> {
	
	/**
	 * 
	 * @param context
	 * @throws PluginException
	 */
	void execute(T context) throws PluginException;
}
