package jspwebapp.common.listener;

import org.apache.catalina.Session;
import org.apache.catalina.authenticator.SingleSignOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

public class SingleSignOnCallback extends SingleSignOn {
    private static final Logger LOG = LoggerFactory.getLogger(SingleSignOnCallback.class);

    private String getSesssionInfo(Session session) {
//        session.getLastAccessedTimeInternal();
//        session.getMaxInactiveInterval();

        ArrayList<String> names = new ArrayList<>();
        Iterator<String> iter = session.getNoteNames();
        while (iter.hasNext()){
            String name = iter.next();
            names.add(name);
        }

        String user = (session.getPrincipal() != null) ? session.getPrincipal().getName() : "null";

        return "Session{hashId=" + System.identityHashCode(session)
                + ", id=" + session.getId()
                + ", user=" + user
                + ", size=" + names.size() + "}";
    }

    @Override
    protected void register(String ssoId, Principal principal, String authType, String username, String password) {
        LOG.info("Register ssoId={}, username={}", ssoId, username);
        super.register(ssoId, principal, authType, username, password);
    }

    @Override
    protected boolean associate(String ssoId, Session session) {
        LOG.info("Associate ssoId={}, session={}", ssoId, getSesssionInfo(session));
        return super.associate(ssoId, session);
    }

    @Override
    protected void deregister(String ssoId) {
        LOG.info("Deregistering ssoId={}", ssoId);
        super.deregister(ssoId);
    }

    @Override
    protected void removeSession(String ssoId, Session session) {
        LOG.info("Removing ssoId={}, session={}", ssoId, getSesssionInfo(session));
        super.removeSession(ssoId, session);
    }
}
