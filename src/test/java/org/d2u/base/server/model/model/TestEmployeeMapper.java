package org.d2u.base.server.model.model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.d2u.base.shared.data.DepartmentMapper;
import org.d2u.base.shared.data.EmployeeMapper;
import org.d2u.base.shared.model.Department;
import org.d2u.base.shared.model.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestEmployeeMapper{
    @Test
    public void findAll() {

        System.out.println("Test EmployeeMapper.findAll");
        boolean oldStyle = false;
        try {
            String resource = "mybatis-config.xml";
            SqlSessionFactory factory = newSQLSessionFactory(resource);
            SqlSession session = factory.openSession();
            List<Employee> empList;
            if (oldStyle) {
                empList = session.selectList("EmployeeMapper.findAll");
            } else {
                EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
                empList = mapper.findAll();
            }
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            for(Employee emp:empList){
                System.out.println(emp);
                Employee empWithDepartment = mapper.findById(emp.getId());
                System.out.println("---"+empWithDepartment);
                assertNotNull(empWithDepartment.getDepartment(),"Department of employee should not be null");
                Department dep = emp.getDepartment();
                if(dep != null){
                    DepartmentMapper depMapper = session.getMapper(DepartmentMapper.class);
                    List<Employee> employees = depMapper.getEmployees(dep);
                    for(Employee emp2:employees) {
                        System.out.println("-----------"+emp2);
                    }
                }
            }
            assertEquals(8,empList.size(),"Expected employees count ");


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory newSQLSessionFactory(String configFile) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(configFile);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
