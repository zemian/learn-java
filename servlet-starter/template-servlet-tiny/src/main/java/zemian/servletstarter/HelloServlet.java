package zemian.servletstarter;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
    	throws ServletException, IOException {
        String viewName = "/WEB-INF/jsp/hello.jsp";
        req.getRequestDispatcher(viewName).forward(req, resp);
    }
}
