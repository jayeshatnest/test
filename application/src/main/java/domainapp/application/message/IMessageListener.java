package domainapp.application.message;

/**
 * @author Jayesh
 *
 */
public interface IMessageListener {

	/**
	 * @param context
	 */
	void onMessage(Message message);
	
}
