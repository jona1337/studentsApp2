
package com.jonacom.students;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Student extends Person implements Serializable {

    private StudGroup group;
    private Date enrollmentDate;

    public Student() {
        super();
    }

    public Student(String name, String surname, StudGroup group) {
        super(name, surname);
        this.group = group;
        this.enrollmentDate = new Date();
    }
    
    public Student(String name, String surname, StudGroup group, Date enrollmentDate) {
        super(name, surname);
        this.group = group;
        this.enrollmentDate = enrollmentDate;
    }

    public StudGroup getGroup() {
        return group;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setGroup(StudGroup group) {
        this.group = group;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateStr = sdf.format(getEnrollmentDate());
        
        sb.append(getName())
                    .append(" ")
                    .append(getGroup().getName())
                    .append(" ")
                    .append(dateStr);
        
        return  sb.toString();
        
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;
        Student student = (Student) o;

        boolean isEqual = false;
        if (group == null)
            isEqual = student.getGroup() == null;
        else
            isEqual = group.equals(student.getGroup());
        isEqual = isEqual && enrollmentDate.equals(student.getEnrollmentDate());
        return isEqual;
    }


}
