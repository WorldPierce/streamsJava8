package com.company;

public class Person implements Comparable {
    String name;
    String gender;
    int age;

    public Person( String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
