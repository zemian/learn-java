<%@ page import="jspwebapp.common.siteminder.SiteMinderAuthenticator" %>
<!DOCTYPE html>
<html>
<pre>
== Extra Headers
<%
out.println(SiteMinderAuthenticator.SM_USER + " = " + request.getHeader(SiteMinderAuthenticator.SM_USER));
out.println(SiteMinderAuthenticator.APP_USER + " = " + request.getHeader(SiteMinderAuthenticator.APP_USER));
%>
</pre>

<%@ include file="/info.jsp" %>

</html>
