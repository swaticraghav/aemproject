package soap.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

public class ReadWriteJDBCServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		// this method will list out all the students.
		// i will send the data in the form of list.
		final Resource resource = request.getResource();
		response.setContentType("text/plain");
		
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		// this method will take customer name and email and will send back the id.
	}

}
