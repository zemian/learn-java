package jspwebapp.common.siteminder;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.http.MimeHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A Tomcat valve to auto inject and mimic identify provider token for testing.
 */
public class SiteMinderTestValve extends ValveBase {
    private static final Logger LOG = LoggerFactory.getLogger(SiteMinderTestValve.class);
    public static final String SESSION_TOKEN_KEY = "SiteMinderTest.SiteMinderToken";

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        setupSiteMinderToken(request);
        getNext().invoke(request, response);
    }

    public void setupSiteMinderToken(Request request) {
        HttpServletRequest req = request.getRequest();
        LOG.debug("Checking request {}", req.getRequestURI());
        HttpSession session = req.getSession(false);
        if (session != null) {
            SiteMinderToken token = (SiteMinderToken) session.getAttribute(SESSION_TOKEN_KEY);
            if (token != null) {
                LOG.info("Found SiteMinderAuth token. Setting headers for token: " + token);
                MimeHeaders headers = request.getCoyoteRequest().getMimeHeaders();
                headers.setValue(SiteMinderAuthenticator.SM_USER).setString(token.getSmuser());
                headers.setValue(SiteMinderAuthenticator.APP_USER).setString(token.getAppuser());
            } else {
                LOG.debug("No SiteMinderAuth token found in session.");
            }
        }
    }
}
