package jspwebapp.common.siteminder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A web filter to auto inject and mimic identify provider token for testing.
 * NOTE: This will not work since filter comes AFTER Valve and failed to intercept the requrest!
 * Use SiteMinderTestValve instead.
 */
public class SiteMinderTestFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SiteMinderTestFilter.class);
    public static final String SESSION_TOKEN_KEY = "SiteMinderTest.sessionToken";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Initializing {}", this);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest wrappedReq = request;
        HttpServletRequest req = (HttpServletRequest) request;
        LOG.debug("Checking request {}", req.getRequestURI());
        HttpSession session = req.getSession(false);
        if (session != null) {
            SiteMinderToken authToken = (SiteMinderToken) session.getAttribute(SESSION_TOKEN_KEY);
            if (authToken != null) {
                // Add siteminder auth token into header
                wrappedReq = new SiteMinderRequestWrapper(req, authToken);
                LOG.debug("Found and wrap SiteMinderAuth token with request for SM headers.");
            } else {
                LOG.debug("No SiteMinderAuth token found in session.");
            }
        } else {
            LOG.debug("No session available.");
        }
        chain.doFilter(wrappedReq, response);
    }

    @Override
    public void destroy() {
        LOG.info("Destroying {}", this);
    }
}
