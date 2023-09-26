package org.d2u.base.data;

import org.d2u.base.model.Department;
import org.d2u.base.model.Employee;

import java.util.List;

public interface DepartmentMapper {
    List<Employee> getEmployees(Department department);
}
