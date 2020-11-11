<%@ page import="java.util.Enumeration" %>
<%@ page import="java.time.Duration" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<pre>
== Request Objects
request.getContextPath() =  <%= request.getContextPath() %>
request.getServletPath() =  <%= request.getServletPath() %>
request.getRequestURI() =  <%= request.getRequestURI() %>
request.getRequestURL() =  <%= request.getRequestURL() %>
request.getQueryString() =  <%= request.getQueryString() %>
request.getPathInfo() =  <%= request.getPathInfo() %>
request.getPathTranslated() =  <%= request.getPathTranslated() %>
request.getContentType() =  <%= request.getContentType() %>
request.getContentLength() =  <%= request.getContentLength() %>
request.getCharacterEncoding() =  <%= request.getCharacterEncoding() %>
request.getLocale() =  <%= request.getLocale() %>
request.getLocalName() =  <%= request.getLocalName() %>
request.getLocalAddr() =  <%= request.getLocalAddr() %>
request.getMethod() =  <%= request.getMethod() %>
request.getRemoteUser() =  <%= request.getRemoteUser() %>
request.getRemoteAddr() =  <%= request.getRemoteAddr() %>
request.getRemoteHost() =  <%= request.getRemoteHost() %>
request.getScheme() =  <%= request.getScheme() %>
request.getProtocol() =  <%= request.getProtocol() %>
request.getServerName() =  <%= request.getServerName() %>
request.getServerPort() =  <%= request.getServerPort() %>
request.getAuthType() =  <%= request.getAuthType() %>
request.getUserPrincipal() =  <%= request.getUserPrincipal() %>
request.getRequestedSessionId() =  <%= request.getRequestedSessionId() %>

== Headers
<%
    {
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            out.println("Header: " + name + " = " + request.getHeader(name) + "");
        }
    }
%>

== Cookies
<%
    {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            out.println("Cookie: name=" + name +
                    ", value= " + cookie.getValue() +
                    ", domain= " + cookie.getDomain() +
                    ", path= " + cookie.getPath() +
                    ", version= " + cookie.getVersion() +
                    ", comment= " + cookie.getComment() +
                    ", maxAge= " + cookie.getMaxAge() +
                    ", secure= " + cookie.getSecure() +
                    "");
        }
    }
%>

== Parameters
<%
    {
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            out.println("Request Param: " + name + " = " + request.getParameter(name) + "");
        }
    }
%>

== Request Attributes
<%
    {
        Enumeration<String> names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            out.println("Request Attribute: " + name + " = " + request.getAttribute(name) + "");
        }
    }
%>

== Session Attributes
<%
    {
        if (session != null) {
            Enumeration<String> names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                out.println("Session Attribute: " + name + " = " + session.getAttribute(name) + "");
            }
        }
    }
%>

== Session Objects
<%
    {
        if (session != null) {
            out.println("Current Server Timestamp  = " + new Date());
            out.println("Session identityHashCode()  = " + System.identityHashCode(session));
            out.println("session.getId()  = " + session.getId());
            out.println("session.getCreationTime()  = " + session.getCreationTime() + " (" + new Date(session.getCreationTime()) + ") or " + Duration.between(Instant.ofEpochMilli(session.getCreationTime()), Instant.now())+ " ago.");
            out.println("session.getLastAccessedTime()  = " + session.getLastAccessedTime()+ " (" + new Date(session.getLastAccessedTime()) + ")");
            out.println("session.getMaxInactiveInterval()  = " + session.getMaxInactiveInterval() + " (" + Duration.ofSeconds(session.getMaxInactiveInterval()) + ") and will expire on " + new Date(session.getLastAccessedTime() + session.getMaxInactiveInterval() * 1000));
            out.println("session.getClass()  = " + session.getClass());
        }
    }
%>

== Application Attributes
<%
    {
        Enumeration<String> names = application.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            out.println("Context: " + name + " = " + application.getAttribute(name) + "");
        }
    }
%>

== Application (ServletContext) Objects
Application identityHashCode()  = <%= System.identityHashCode(application) %>
application.getServerInfo() =  <%= application.getServerInfo() %>
application.getContextPath() =  <%= application.getContextPath() %>
application.getClassLoader() =  <%= application.getClassLoader() %>
application.getMajorVersion() =  <%= application.getMajorVersion() %>
application.getMinorVersion() =  <%= application.getMinorVersion() %>
application.getEffectiveMajorVersion() =  <%= application.getEffectiveMajorVersion() %>
application.getEffectiveMinorVersion() =  <%= application.getEffectiveMinorVersion() %>
application.getVirtualServerName() =  <%= application.getVirtualServerName() %>
application.getResource("/") =  <%= application.getResource("/") %>
application.getRealPath("/") =  <%= application.getRealPath("/") %>
application.getResourcePaths("/") =  <%= application.getResourcePaths("/") %>

</pre>
</html>
