package jspwebapp.common.siteminder;

import java.util.Date;

/**
 * A token to represents a identity provider authentication.
 * There is a 1-to-1 mapping of smuser to application user, and we will use appuser as logged in user throughout
 * the request process.
 */
public class SiteMinderToken {
    private Date createdTs = new Date();
    private String smuser;
    private String appuser;

    public String getSmuser() {
        return smuser;
    }

    public void setSmuser(String smuser) {
        this.smuser = smuser;
    }

    public String getAppuser() {
        return appuser;
    }

    public void setAppuser(String appuser) {
        this.appuser = appuser;
    }

    @Override
    public String toString() {
        return "SiteMinderToken{" +
                "createdTs=" + createdTs +
                ", smuser='" + smuser + '\'' +
                ", appuser='" + appuser + '\'' +
                '}';
    }
}
