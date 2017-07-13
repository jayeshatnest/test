/**
 * 
 */
package domainapp.modules.cmn.dom.api;

import org.apache.isis.applib.services.i18n.TranslatableString;

/**
 * @author Jayesh
 *
 */
public interface IResult extends IContext {

	/**
	 * @return
	 */
	Boolean isSuccess();
	
	/**
	 * @param success
	 */
	void setSuccess(Boolean success);
	
	/**
	 * @return
	 */
	TranslatableString getError();
	
	/**
	 * 
	 */
	void setError(TranslatableString error);
	
	/**
	 * 
	 */
	void setError(String error);

	/**
	 * @param context
	 */
	void setContext(IContext context);

	/**
	 * @param e
	 */
	void setException(Throwable e);

	/**
	 * @return
	 */
	IContext getContext();

	/**
	 * @return
	 */
	Throwable getException();
}
