package jspwebapp.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * A simple logger to log session create/destroy event.
 */
public class SessionLoggerListener implements HttpSessionListener {
    private static final Logger LOG = LoggerFactory.getLogger(SessionLoggerListener.class);


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        LOG.info("Session {} created: {}", session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        LOG.info("Session {} destroyed: {}", session.getId(), session);
    }
}
