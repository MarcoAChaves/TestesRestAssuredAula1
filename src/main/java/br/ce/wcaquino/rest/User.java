package br.ce.wcaquino.rest;

public class User {

    private String name;
    private Integer age;
    private Double Salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
