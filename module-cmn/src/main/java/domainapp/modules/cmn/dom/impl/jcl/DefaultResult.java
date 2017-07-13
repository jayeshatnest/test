/**
 * 
 */
package domainapp.modules.cmn.dom.impl.jcl;

import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.modules.cmn.dom.api.IContext;
import domainapp.modules.cmn.dom.api.IResult;

/**
 * Default implementation of IResult
 * 
 * @author Jayesh
 */
public class DefaultResult extends DefaultContext implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param name
	 * @param descrption
	 * @return
	 */
	public static IResult newResult(String name, String descrption) {
		DefaultResult result = new DefaultResult();
		result.setName(name);
		result.setDescription(descrption);
		return result;
	}

	/* (non-Javadoc)
	 * @see domainapp.modules.cmn.dom.api.IResult#isSuccess()
	 */
	@Override
	public Boolean isSuccess() {
		return get("success", Boolean.TRUE);
	}

	/* (non-Javadoc)
	 * @see domainapp.modules.cmn.dom.api.IResult#setSuccess(java.lang.Boolean)
	 */
	@Override
	public void setSuccess(Boolean success) {
		set("success", success);
	}

	/* (non-Javadoc)
	 * @see domainapp.modules.cmn.dom.api.IResult#getError()
	 */
	@Override
	public TranslatableString getError() {
		return get("error", null);
	}

	/* (non-Javadoc)
	 * @see domainapp.modules.cmn.dom.api.IResult#seError(org.apache.isis.applib.services.i18n.TranslatableString)
	 */
	@Override
	public void setError(TranslatableString error) {
		set("error", error);
	}

	/* (non-Javadoc)
	 * @see domainapp.modules.cmn.dom.api.IResult#seError(java.lang.String)
	 */
	@Override
	public void setError(String error) {
		setError(TranslatableString.tr(error));
	}

	public static IResult newFailedResult(IContext context, Throwable e) {
		IResult result = DefaultResult.newResult(context.getName(), String.format("Result of %s(%s)", context.getName(), context.getId()));
		result.setSuccess(false);
		result.setError(String.format("Failed result for context: %s(%s)", context.getName(), context.getId()));
		result.setException(e);
		result.setContext(context);
		return result;
	}

	@Override
	public void setContext(IContext context) {
		set("context", context);
	}
	
	@Override
	public IContext getContext() {
		return get("context", null, IContext.class);
	}

	@Override
	public void setException(Throwable e) {
		set("exception", e);
	}
	
	@Override
	public Throwable getException() {
		return get("exception", null, Throwable.class);
	}

}
