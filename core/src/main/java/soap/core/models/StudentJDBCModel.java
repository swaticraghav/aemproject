package soap.core.models;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import soap.core.Student;
import soap.core.StudentService;

@Model(adaptables = Resource.class)
public class StudentJDBCModel {

	private String message;
	List<Student> studentList;

	@OSGiService
	StudentService service;

	@PostConstruct
	public void init() {
		if (service != null) {
			message = "This is JDBC example where data is displayed in Table format on Web page." + service;
			studentList = service.getStudentList();
		} else {
			message = "Service is null";
		}
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public String getMessage() {
		return message;
	}

}
