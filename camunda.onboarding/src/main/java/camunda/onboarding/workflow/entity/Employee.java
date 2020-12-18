package camunda.onboarding.workflow.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "employee", schema = "public")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "shoe_size")
	private String shoeSize;

	@Column(name = "tshirt_size")
	private String tShirtSize;

	@Column(name = "role_title")
	private String roleTitle;

	@Column(name = "department")
	private String department;

//	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "employee_number")
	private String employeeNumber;

	public Employee(String firstName, String lastName, String shoeSize, String tShirtSize, String roleTitle,
			String department, LocalDate startDate, String employeeNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.shoeSize = shoeSize;
		this.tShirtSize = tShirtSize;
		this.roleTitle = roleTitle;
		this.department = department;
		this.startDate = startDate;
		this.employeeNumber = employeeNumber;
	}

	public String getShoeSize() {
		return shoeSize;
	}

	public void setShoeSize(String shoeSize) {
		this.shoeSize = shoeSize;
	}

	public String gettShirtSize() {
		return tShirtSize;
	}

	public void settShirtSize(String tShirtSize) {
		this.tShirtSize = tShirtSize;
	}

	public String getRoleTitle() {
		return roleTitle;
	}

	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
