package camunda.onboarding.workflow.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import camunda.onboarding.workflow.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}