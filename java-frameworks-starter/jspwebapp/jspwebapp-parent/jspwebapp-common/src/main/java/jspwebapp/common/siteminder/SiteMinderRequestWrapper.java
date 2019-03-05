package jspwebapp.common.siteminder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SiteMinderRequestWrapper extends HttpServletRequestWrapper {
    private SiteMinderToken token;
    public SiteMinderRequestWrapper(HttpServletRequest request, SiteMinderToken token) {
        super(request);
        this.token = token;
    }

    @Override
    public String getHeader(String name) {
        if (token != null) {
            if (SiteMinderAuthenticator.SM_USER.equals(name)) {
                return token.getSmuser();
            } else if (SiteMinderAuthenticator.APP_USER.equals(name)) {
                return token.getAppuser();
            }
        }
        return super.getHeader(name);
    }
}
