package domainapp.webapp;

import static domainapp.modules.cmn.dom.ConfigurationConstants.EMBEDDED_WEB_SERVER_RESOURCE_BASE_DEFAULT;
import static domainapp.modules.cmn.dom.ConfigurationConstants.EMBEDDED_WEB_SERVER_RESOURCE_BASE_KEY;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.isis.viewer.wicket.viewer.IsisWicketApplication;

import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.google.inject.util.Providers;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;

/**
 * As specified in <tt>web.xml</tt>.
 * 
 * <p>
 * See:
 * <pre>
 * &lt;filter>
 *   &lt;filter-name>wicket&lt;/filter-name>
 *    &lt;filter-class>org.apache.wicket.protocol.http.WicketFilter&lt;/filter-class>
 *    &lt;init-param>
 *      &lt;param-name>applicationClassName&lt;/param-name>
 *      &lt;param-value>domainapp.webapp.DomainApplication&lt;/param-value>
 *    &lt;/init-param>
 * &lt;/filter>
 * </pre>
 * 
 */
public class DomainApplication extends IsisWicketApplication {

    private static final long serialVersionUID = 1L;

    @Override
    protected void init() {
        super.init();

        IBootstrapSettings settings = Bootstrap.getSettings();
        settings.setThemeProvider(new BootswatchThemeProvider(BootswatchTheme.Lumen));
    }

    @Override
    protected Module newIsisWicketModule() {
        final Module isisDefaults = super.newIsisWicketModule();
        
        final Module overrides = new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named("applicationName")).toInstance("Test");
                bind(String.class).annotatedWith(Names.named("applicationCss")).toInstance("css/application.css");
                bind(String.class).annotatedWith(Names.named("applicationJs")).toInstance("scripts/application.js");
                bind(String.class).annotatedWith(Names.named("welcomeMessage")).toInstance(readLines(getClass(), "welcome.html"));
                bind(String.class).annotatedWith(Names.named("aboutMessage")).toInstance("Test");
                bind(InputStream.class).annotatedWith(Names.named("metaInfManifest")).toProvider(
                        Providers.of(getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF")));
                
                // if uncommented, then overrides isis.appManifest in config file.
                // bind(AppManifest.class).toInstance(new DomainAppAppManifest());
            }
        };

        return Modules.override(isisDefaults).with(overrides);
    }
    
    private String getContext() {        
    	return System.getProperty(EMBEDDED_WEB_SERVER_RESOURCE_BASE_KEY, EMBEDDED_WEB_SERVER_RESOURCE_BASE_DEFAULT);
    }

    private static String readLines(final Class<?> contextClass, final String resourceName) {
        try {
            List<String> readLines = Resources.readLines(Resources.getResource(contextClass, resourceName), Charset.defaultCharset());
            final String aboutText = Joiner.on("\n").join(readLines);
            return aboutText;
        } catch (IOException e) {
            return "This is a test app";
        }
    }

}
