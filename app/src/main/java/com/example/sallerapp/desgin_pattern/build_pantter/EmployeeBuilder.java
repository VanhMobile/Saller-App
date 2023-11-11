package com.example.sallerapp.desgin_pattern.build_pantter;

import com.example.sallerapp.model.Employee;

public class EmployeeBuilder {
    private Employee employee = new Employee();

    public EmployeeBuilder addImgPath(String imgPath){
        employee.setImgPath(imgPath);
        return this;
    }

    public EmployeeBuilder addId(String id){
        employee.setIdEmployee(id);
        return this;
    }

    public  EmployeeBuilder addName(String name){
        employee.setName(name);
        return this;
    }

    public EmployeeBuilder addNumberPhone(String numberPhone){
        employee.setNumberPhone(numberPhone);
        return this;
    }

    public EmployeeBuilder addEmail(String email){
        employee.setEmail(email);
        return this;
    }

    public EmployeeBuilder addPassword(String password){
        employee.setPassword(password);
        return this;
    }

    public EmployeeBuilder addIdShop(String id){
        employee.setIdShop(id);
        return this;
    }

    public EmployeeBuilder addNote(String note){
        employee.setNote(note);
        return this;
    }

    public Employee build(){
        return employee;
    }
}
