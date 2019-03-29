package jspwebapp.common.siteminder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

import org.apache.catalina.Realm;
import org.apache.catalina.Session;
import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.authenticator.FormAuthenticator;
import org.apache.catalina.connector.Request;
import org.apache.commons.lang3.StringUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * A simple Tomcat Authenticator that integrate identity provider (SiteMinder). It will simply check for
 * expected header nammes that presents and assume user has success authenticated. If headers are note found
 * then it will default back to form login authentication process.
 */
public class SiteMinderAuthenticator extends FormAuthenticator {
    public static final Log LOG = LogFactory.getLog(SiteMinderAuthenticator.class);
    public static final String DUMMY_PASSWORD = "";
    public static final String SM_USER = "smuser";
    public static final String APP_USER = "appuser";

    private boolean enableSiteMinder = false;

    public boolean isEnableSiteMinder() {
        return enableSiteMinder;
    }

    public void setEnableSiteMinder(boolean enableSiteMinder) {
        this.enableSiteMinder = enableSiteMinder;
    }

    @Override
    protected boolean doAuthenticate(Request request, HttpServletResponse response) throws IOException {
        if (enableSiteMinder) {
            LOG.debug("Checking for SiteMinder authentication token.");
            String smuser = request.getHeader(SM_USER);
            String appuser = request.getHeader(APP_USER);
            boolean useSSO = true;
            boolean authResult = super.checkForCachedAuthentication(request, response, useSSO);
            if (authResult) {
                LOG.debug("Found existing user session!");
                return true;
            }

            // User has no existing session, so we will now establish one.
            // Let's intercept the form processing and check for SiteMinder headers info data
            // If we find it, we will assume user already authenticated, and we will save the user data
            // into the session, and then let the request continues as if original form authenticator has passed.
            if (validateUsers(smuser, appuser)) {
                LOG.debug("Found SiteMinder smuser=" + smuser + " and appuser=" + appuser);

                // We will by pass the normal FormAuthenticator invocation to Realm.authenticate(user, password)
                // and directly retrieve user using Realm.authenticate(user) instead. We will then register
                // appuser as authenticated user.
                Realm realm = getContainer().getRealm();
                Principal principal = realm.authenticate(appuser);
                if (principal == null) {
                    throw new RuntimeException("App user " + appuser + " not found.");
                }
                if (getCache()) {
                    LOG.debug("Registering principal=" + principal);
                    register(request, response, principal, "FORM", appuser, DUMMY_PASSWORD);
                }

                // Save the authenticated Principal in our session
                Session session = request.getSessionInternal(true);
                session.setNote(Constants.FORM_PRINCIPAL_NOTE, principal);
                // Save the username and password as well
                session.setNote(Constants.SESS_USERNAME_NOTE, appuser);
                session.setNote(Constants.SESS_PASSWORD_NOTE, DUMMY_PASSWORD);

                LOG.info("SiteMinder user=" + smuser + " and appuser=" + appuser + " has logged in.");
                return true;
            }
        }

        // If we are here, then let the original form authenticator process it.
        LOG.debug("Using normal form login authentication.");
        return super.doAuthenticate(request, response);
    }

    private boolean validateUsers(String smuser, String ldapUser) {
        return StringUtils.isNotEmpty(smuser) && StringUtils.isNotEmpty(ldapUser);
    }
}
