package com.example.njug_spring_crud_project.repositories;

import com.example.njug_spring_crud_project.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByDepartment(String department);

	List<Employee> findByActiveTrue();

	Optional<Employee> findByEmail(String email);

	@Query("SELECT e from Employee e WHERE e.salary BETWEEN :min AND :max")
	List<Employee> findBySalaryRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
}
