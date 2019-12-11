package soap.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(service = StudentService.class, immediate = true)
public class StudentServiceImpl implements StudentService {

	private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Reference
	DataSourcePool source;

	@Override
	public List<Student> getStudentList() {

		List<Student> list = new ArrayList<>();
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
				Student student = new Student(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"));
				list.add(student);
			}

		} catch (DataSourceNotFoundException | SQLException e) {
			logger.error("Error in StudentServiceImpl: " + e.getCause().getMessage());
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

		return list;
	}

}
