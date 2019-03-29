<%@ page import="java.util.*" %>
<%@ page import="jspwebapp.common.siteminder.SiteMinderTestFilter" %>
<%@ page import="jspwebapp.common.siteminder.SiteMinderToken" %>
<%@ page import="jspwebapp.common.siteminder.SiteMinderRequestWrapper" %>
<!DOCTYPE html>
<html>
<pre>
    
== Test Forward Requests
<%
    // NOTE request forward only for within this webapp. If you need cross webapp forward, you
    // would need to get a hold of the second webapp ServletContext.getRequestDispatcher() to perform
    // forward.

//    // Example: simple forward:
//    request.getRequestDispatcher("/info.jsp").forward(request, response);

    // Example: wrap request forward:
    SiteMinderToken token = new SiteMinderToken();
    token.setSmuser("zmzemian");
    token.setAppuser("zemian");
    SiteMinderRequestWrapper requestWrapper =
            new SiteMinderRequestWrapper(request, token);
    request.getRequestDispatcher("/forward-result.jsp").forward(requestWrapper, response);
%>

</pre>
</html>
