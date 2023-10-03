package org.d2u.base.shared.data;

import org.d2u.base.shared.model.Employee;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> findAll();
    Employee findById(int id);
   // Department findDepartmentOfEmployee(int id);
}
