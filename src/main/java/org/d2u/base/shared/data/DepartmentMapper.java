package org.d2u.base.shared.data;

import org.d2u.base.shared.model.Department;
import org.d2u.base.shared.model.Employee;

import java.util.List;

public interface DepartmentMapper {
    List<Employee> getEmployees(Department department);
}
