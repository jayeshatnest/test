/**
 * 
 */
package domainapp.modules.cmn.dom;


/**
 * @author Jayesh
 *
 */
public interface ConfigurationConstants {

    String ISIS_ROOT = "isis."; // see org.apache.isis.core.commons.config.ConfigurationConstants
    
    // see org.apache.isis.core.webserver.WebServerCostants for below constants
    String ROOT = ISIS_ROOT + "embedded-web-server" + ".";

    String EMBEDDED_WEB_SERVER_PORT_KEY = ROOT + "port";
    int EMBEDDED_WEB_SERVER_PORT_DEFAULT = 8080;

    String EMBEDDED_WEB_SERVER_ADDRESS_KEY = ROOT + "address";
    String EMBEDDED_WEB_SERVER_ADDRESS_DEFAULT = "localhost";

    String EMBEDDED_WEB_SERVER_RESOURCE_BASE_KEY = ROOT + "webapp";
    String EMBEDDED_WEB_SERVER_RESOURCE_BASE_DEFAULT = ""; // or

}
