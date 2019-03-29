package zemian.servletstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HelloFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(HelloFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Init {}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String testParam = req.getParameter("test");
        LOG.info("Filtering method={}, uri={}, testParam={}", req.getMethod(), req.getRequestURI(), testParam);
        if ("filterReject".equals(testParam)) {
            LOG.info("Filter rejecting request.");
            return;
        } else {
            LOG.info("Filter pass.");
            // Pass to next filter
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOG.info("Destroying.");
    }
}
