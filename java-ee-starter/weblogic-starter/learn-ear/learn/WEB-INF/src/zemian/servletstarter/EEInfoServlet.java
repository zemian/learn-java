package zemian.servletstarter;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspFactory;
import javax.inject.Inject;
import javax.annotation.Resource;
import javax.servlet.descriptor.*;

/**
 * Serlvet to Display List of Java EE Implementation Classes.
 * NOTE: You must enable CDI (add WEB-INF/beans.xml) in order to make this
 * Servlet work.
 */
@WebServlet("/ee-info")
public class EEInfoServlet extends HttpServlet {
	@Inject	ServletContext servletContext;
	@Inject HttpSession session;
	@Inject HttpServletRequest request;

	@Resource javax.validation.ValidatorFactory validatorFactory;
	@Resource javax.validation.Validator validator;
	@Resource javax.transaction.UserTransaction transaction;
	
	// This will not work unless user is logged in?
	//@Resource java.security.Principal principal;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> versionMap = new TreeMap<>();
		req.setAttribute("versionMap", versionMap);
		versionMap.put("_Java", System.getProperty("java.version"));
		versionMap.put("_Java Vendor", System.getProperty("java.vendor"));
		versionMap.put("_Server", servletContext.getServerInfo());
		versionMap.put("web.Servlet", servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());
		versionMap.put("web.JSP", JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion());

		Map<String, String> implClassMap = new TreeMap<>();
		req.setAttribute("implClassMap", implClassMap);
		implClassMap.put("web.servletContext", getSafeClassName(servletContext));
		implClassMap.put("web.session", getSafeClassName(session));
		implClassMap.put("web.request", getSafeClassName(request));
		implClassMap.put("validator.validatorFactory", getSafeClassName(validatorFactory));
		implClassMap.put("validator.validator", getSafeClassName(validator));
		implClassMap.put("tx.transaction", getSafeClassName(transaction));
		//classNameMap.put("user.principal", getSafeClassName(principal));

		Map<String, String> classLocMap = new TreeMap<>();
		req.setAttribute("classLocMap", classLocMap);
		classLocMap.put("javax.servlet.http.HttpServlet", getClassLoc(javax.servlet.http.HttpServlet.class));
		classLocMap.put("javax.servlet.jsp.JspFactory", getClassLoc(javax.servlet.jsp.JspFactory.class));
		classLocMap.put("javax.servlet.jsp.jstl.core.LoopTag", getClassLoc(javax.servlet.jsp.jstl.core.LoopTag.class));
		classLocMap.put("javax.inject.Inject", getClassLoc(javax.inject.Inject.class));
		classLocMap.put("javax.annotation.Resource", getClassLoc(javax.annotation.Resource.class));

		Map<String, String> tagLibsMap = new TreeMap<>();
		req.setAttribute("tagLibsMap", tagLibsMap);
		JspConfigDescriptor jspConfig = servletContext.getJspConfigDescriptor();
		if (jspConfig != null) {
			Collection<TaglibDescriptor> tagLibs = jspConfig.getTaglibs();
			for (TaglibDescriptor tag : tagLibs) {
				tagLibsMap.put(tag.getTaglibURI(), tag.getTaglibLocation());
			}
		}

        String viewName = "/WEB-INF/jsp/ee-info.jsp";
		req.getRequestDispatcher(viewName).forward(req, resp);
	}

	public static String toSafeString(Object obj) {
		if (obj == null) {
			return "NULL";
		} else {
			try {
				return obj.toString();
			} catch (Exception e) {
				String msg = "ERROR: " + obj.getClass() + " toString() throws exception";
				//System.out.println(msg);
				//e.printStackTrace();
				return msg + ": " + e;
			}
		}
	}

	public static String getClassLoc(Class<?> cls) {
		java.security.ProtectionDomain d = cls.getProtectionDomain();
		if (d != null && d.getCodeSource() != null && d.getCodeSource().getLocation() != null) {
			return d.getCodeSource().getLocation().toString();
		} else {
			return "NOT_FOUND";
		}
	}

	public static String getSafeClassName(Object obj) {
		if (obj == null) {
			return "NULL";
		} else {
			return obj.getClass().getName();
		}
	}
}
