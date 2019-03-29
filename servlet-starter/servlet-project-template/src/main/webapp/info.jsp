<%@ page session="false" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<!DOCTYPE html>
<html>
<body>
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
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            Enumeration<String> names = httpSession.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                out.println("Session Attribute: " + name + " = " + httpSession.getAttribute(name) + "");
            }
        }
    }
%>

== Session Objects
<%
    {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            out.println("Current Server Timestamp  = " + new Date());
            out.println("Session identityHashCode()  = " + System.identityHashCode(httpSession));
            out.println("Session.getId()  = " + httpSession.getId());
            out.println("Session.getCreationTime()  = " + httpSession.getCreationTime() + " (" + new Date(httpSession.getCreationTime()) + ") or " + Duration.between(Instant.ofEpochMilli(httpSession.getCreationTime()), Instant.now())+ " ago.");
            out.println("Session.getLastAccessedTime()  = " + httpSession.getLastAccessedTime()+ " (" + new Date(httpSession.getLastAccessedTime()) + ")");
            out.println("Session.getMaxInactiveInterval()  = " + httpSession.getMaxInactiveInterval() + " (" + Duration.ofSeconds(httpSession.getMaxInactiveInterval()) + ") and will expire on " + new Date(httpSession.getLastAccessedTime() + httpSession.getMaxInactiveInterval() * 1000));
            out.println("Session.getClass()  = " + httpSession.getClass());
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
application.identityHashCode()  = <%= System.identityHashCode(application) %>
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

JSP Version = <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %>
Java Version = <%= System.getProperty("java.version") %>
Java Vendor = <%= System.getProperty("java.vendor") %>

== API Implementation Classes
application = <%= application.getClass() %>
request = <%= request.getClass() %>
pageContext = <%= pageContext.getClass() %>

== ClassLoaders
<%
String indent = "";
ClassLoader cl = Thread.currentThread().getContextClassLoader();
out.println(indent + "" + cl);
indent += "  ";
while (cl.getParent() != null) {
    cl = cl.getParent();    
    out.println(indent + "" + cl);
    indent += "  ";
}
%>

</pre>
</body>
</html>
