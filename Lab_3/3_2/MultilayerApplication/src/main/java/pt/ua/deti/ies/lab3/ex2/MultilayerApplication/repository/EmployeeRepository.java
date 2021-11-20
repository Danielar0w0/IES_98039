package pt.ua.deti.ies.lab3.ex2.MultilayerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.deti.ies.lab3.ex2.MultilayerApplication.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Employee findByEmailId(String emailId);

}