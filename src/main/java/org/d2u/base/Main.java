package org.d2u.base;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.d2u.base.pojo.Employee;
import org.d2u.base.pojo.EmployeeMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main123(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!2");
        boolean oldStyle = false;
        try {
            String resource = "mybatis-config.xml";
            SqlSessionFactory factory = newSQLSessionFactory(resource);
            SqlSession session = factory.openSession();
            List<Employee> empList;
            if (oldStyle) {
                empList = session.selectList("EmpMapper.findAll");
            } else {
                EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
                empList = mapper.findAll();
            }
            for(Employee emp:empList){
                System.out.println(emp);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory newSQLSessionFactory(String configFile) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(configFile);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}