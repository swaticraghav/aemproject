package soap.core.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;

import soap.core.Student;

@Component(service = Servlet.class, property = { ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=this/is/us",
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "=json",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST })
public class AddingStudentServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(AddingStudentServlet.class);

	@Reference
	DataSourcePool dataSourcePool;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student student = new Student(firstName, lastName, email);

		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		String sql = "insert into student" + "(first_name, last_name, email)" + "values (?, ?, ?)";
		String dataSourceName = "test";

		try {
			dataSource = (DataSource) dataSourcePool.getDataSource(dataSourceName);
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.execute();
			// 
			response.getWriter().write("ID Namespace");

		} catch (DataSourceNotFoundException | SQLException e) {

			logger.error("Error in AddingStudentServlet: " + e.getCause().getMessage());
			e.printStackTrace();

		} finally {
			close(connection, statement, null);
		}

	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {

			if (myConn != null) {
				myConn.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myRs != null) {
				myRs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
