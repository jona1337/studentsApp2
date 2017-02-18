package com.jonacom.students;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

    private String name;
    private String surname;

    public Person() {}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return name.equals(person.getName()) &&
                surname.equals(person.getSurname());
    }

}
