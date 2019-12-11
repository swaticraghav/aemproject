package soap.core.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.day.cq.searchpromote.xml.result.ResultEntity;

import soap.core.Student;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=FormServlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "this/resource/type",
		"sling.servlet.extensions=" + "json" })
public class FormServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(FormServlet.class);

	@Reference
	DataSourcePool dataSourcePool;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		List<Student> list = new ArrayList<>();
		Student studentOne = new Student("Shahsi", "Tharror", "GodKnows!abc.com");
		Student studentTwo = new Student("swati", "Raghav", "Love!abc.com");
		Student studentThree = new Student("Supp", "Tiwari", "yay!abc.com");
		list.add(studentOne);
		list.add(studentTwo);
		list.add(studentThree);
		request.setAttribute("Student_List", list);

		Resource resource = request.getResourceResolver().getResource(request.getRequestPathInfo().getSuffix());
		/*
		 * if (resource != null) { logger.info("Inside the null chaeck for resource");
		 * RequestDispatcher dispatcher =
		 * request.getRequestDispatcher(resource.getPath()); dispatcher.forward(request,
		 * response); }
		 */

		response.setContentType("text/plain");
		response.getWriter().println("resource: " + resource + ", Parent info: " + resource.getParent().getPath());
		logger.info("Success!");
	}

	String getDataConnection() {
		return "";
	}

}
