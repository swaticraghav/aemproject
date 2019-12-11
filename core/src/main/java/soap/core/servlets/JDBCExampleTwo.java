package soap.core.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "aemandsoap/components/content/list", "sling.servlet.extensions=" + "json" })
public class JDBCExampleTwo extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(JDBCExampleTwo.class);

	@Reference
	DataSourcePool source;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		final Resource resource = request.getResource();
		response.setContentType("text/plain");

		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "Select * from user";
		String dataSourceName = "swati";

		try {
			dataSource = (DataSource) source.getDataSource(dataSourceName);
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String firstName = resultSet.getString("firstName");
				response.getWriter().println("First Name = " + firstName);
			}

			response.getWriter().println("Connection = " + connection);
		} catch (DataSourceNotFoundException | SQLException e) {
			logger.error("Error in JDBC Servlet: " + e.getCause().getMessage());
			e.printStackTrace();
		}

		response.getWriter().println("Property Value = " + resource.adaptTo(ValueMap.class).get("childDepth"));
	}
}
