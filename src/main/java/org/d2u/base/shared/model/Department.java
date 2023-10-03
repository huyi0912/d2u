package org.d2u.base.shared.model;

import java.util.List;

public class Department {
    private String name;
    //private Employee lead;
    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public Employee getLead() {
    //     return lead;
    // }

    //public void setLead(Employee lead) {
    //    this.lead = lead;
    //}

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString(){
        //return "Department:[name="+name+",lead="+lead.toString()+", total "+employees.size()+" employees]";
        if(employees == null)
            return "Department:[name="+name+"]";
        else return "Department:[name="+name+", total "+employees.size()+" employees]";
    }
}
