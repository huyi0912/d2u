package org.d2u.base.pojo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                empList = session.selectList("EmpMapper.findAll");
            } else {
                EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
                empList = mapper.findAll();
            }
            for(Employee emp:empList){
                System.out.println(emp);
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
