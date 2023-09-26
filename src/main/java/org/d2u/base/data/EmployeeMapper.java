package org.d2u.base.data;

import org.d2u.base.model.Department;
import org.d2u.base.model.Employee;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> findAll();
    Employee findById(int id);
   // Department findDepartmentOfEmployee(int id);
}
