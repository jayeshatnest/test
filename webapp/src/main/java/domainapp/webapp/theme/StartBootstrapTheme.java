/**
 * 
 */
package domainapp.webapp.theme;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ITheme;

/**
 * @author Jayesh
 *
 */
public enum StartBootstrapTheme implements ITheme {
    Creative;

    /**
     * The placeholders are:
     * - the version
     * - the theme name
     * Example: //cdnjs.cloudflare.com/ajax/libs/startbootstrap-creative/3.3.7/css/creative.css
     */
    private static final String CDN_PATTERN = "//cdnjs.cloudflare.com/ajax/libs/startbootstrap-%s/%s/css/%s.css";

    private String cdnUrl;
    private final ResourceReference reference;
    
    public class StartBootstrapCssReference extends CssResourceReference {
        private static final long serialVersionUID = 1L;

        /**
         * Private constructor.
         */
        public StartBootstrapCssReference(final String swatchName) {
            super(StartBootstrapCssReference.class, "css/" + swatchName + ".css");
        }

    }
    
    public class StartBootstrapJsReference extends JavaScriptResourceReference {
        private static final long serialVersionUID = 1L;

        /**
         * Private constructor.
         */
        public StartBootstrapJsReference(final String swatchName) {
            super(StartBootstrapCssReference.class, "js/" + swatchName + ".js");
        }

    }
    /**
     * Construct.
     */
    private StartBootstrapTheme() {
        this.reference = new StartBootstrapCssReference(name().toLowerCase());
        // TODO
//        this.reference = new StartBootstrapCssReference(name().toLowerCase());
    }

    @Override
    public Iterable<String> getCdnUrls() {
        return Arrays.asList(cdnUrl);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        return null ; // TODO: Collections.<HeaderItem>unmodifiableList(CssHeaderItem.forReference(reference), JavaScriptHeaderItem.forReference(reference));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        if (useCdnResources()) {
            if (cdnUrl == null) {
                cdnUrl = String.format(CDN_PATTERN, getVersion(), name().toLowerCase());
            }
            response.render(CssHeaderItem.forReference(new UrlResourceReference(Url.parse(cdnUrl))));
        }
        else {
            for (HeaderItem headerItem : getDependencies()) {
                response.render(headerItem);
            }
        }
    }

    /**
     * @return true, if cdn resources should be used instead of webjars.
     */
    private boolean useCdnResources() {
        boolean result = false;

        if (Application.exists()) {
            IBootstrapSettings settings = Bootstrap.getSettings();
            if (settings != null) {
                result = settings.useCdnResources();
            }
        }

        return result;
    }

    /**
     * @return The configured version of Bootstrap
     */
    private String getVersion() {
        String version = IBootstrapSettings.VERSION;
        if (Application.exists()) {
            IBootstrapSettings settings = Bootstrap.getSettings();
            if (settings != null) {
                version = settings.getVersion();
            }
        }

        return version;
    }
}
