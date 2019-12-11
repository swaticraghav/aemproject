package soap.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import soap.core.Student;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.resourceTypes=" + "this/is/us",
		"sling.servlet.extensions=" + "json" })
public class Emergency extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;

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
		String path = request.getResourceResolver().getResource("/content/aemandsoap/en/formpage/jcr:content")
				.getParent().getPath();
		if (path != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/apps/aemandsoap/components/content/formResult/formResult.jsp");
			dispatcher.forward(request, response);
		}

		response.setContentType("text/plain");
		response.getWriter().println("Hey There!");

	}
}
