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
		"sling.servlet.resourceTypes=" + "aemandsoap/components/content/helloworld",
		"sling.servlet.extensions=" + "json" })
public class JDBCServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(JDBCServlet.class);

	@Reference
	DataSourcePool source;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		final Resource resource = request.getResource();
		response.setContentType("text/plain");
		response.getWriter().println("Property Value = " + resource.adaptTo(ValueMap.class).get("text"));

		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "Select * from Student";
		String dataSourceName = "test";

		try {
			dataSource = (DataSource) source.getDataSource(dataSourceName);
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String email = resultSet.getString("email");
				response.getWriter().println("Email Value = " + email);
			}

			response.getWriter().println("Connection = " + connection);
		} catch (DataSourceNotFoundException | SQLException e) {
			logger.error("Error in JDBC Servlet: " + e.getCause().getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (Exception e) {
				logger.error("Error while Closing connections:  " + e.getCause().getMessage());
			}
		}

	}
}
