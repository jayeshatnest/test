/**
 * 
 */
package domainapp.modules.cmn.dom.api;

/**
 * Active-able entity specification for entities like static data, configurations, etc. that must not be deleted.
 * 
 * @author Jayesh
 */
public interface IActivableEntity {

	/**
	 * @param active to set
	 */
	void setActive(Boolean active);
	
	/**
	 * @return true if active
	 */
	Boolean isActive();
}
