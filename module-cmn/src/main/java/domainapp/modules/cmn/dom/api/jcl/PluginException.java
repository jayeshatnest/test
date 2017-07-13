/**
 * 
 */
package domainapp.modules.cmn.dom.api.jcl;

/**
 * Plugin exception is thrown in unhandled error situation with-in plugin execution.
 * 
 * @author Jayesh
 */
public class PluginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public PluginException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PluginException(String message, Throwable cause) {
		super(message, cause);
	}
}
