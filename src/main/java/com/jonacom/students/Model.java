package com.jonacom.students;


import com.jonacom.students.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Model {

    private static final String DATA_PACKAGE = "data";
    private static final String STUDENTS_DATA_FILE = "students.txt";
    private static final String GROUPS_DATA_FILE = "groups.txt";

    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<StudGroup> groups = FXCollections.observableArrayList();

    public Model() {

        createGroups();
        createStudents();

        /*
        students.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                updateStudents();
            }
        });

        groups.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                updateGroups();
            }
        });
        */

        /*
        StudGroup gr1 = new StudGroup("gr1", "fac1");
        StudGroup gr2 = new StudGroup("gr2", "fac1");
        groups.add(gr1);
        groups.add(gr2);
        students.add(new Student("John", "C.", null));
        students.add(new Student("Ned", "B.", gr1));
        students.add(new Student("Kate", "P.", gr2));*/

    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void setStudents(ObservableList<Student> students) {
        this.students = students;
    }

    public ObservableList<StudGroup> getGroups() {
        return groups;
    }

    public void setGroups(ObservableList<StudGroup> groups) {
        this.groups = groups;
    }


    /*----------------*/

    public int getStudentsCount() {
        return students.size();
    }

    public int getGroupsCount() {
        return groups.size();
    }

    /*----------------*/

    public Student getStudentByNumber(int number) {

        int studentsCount = getStudentsCount();
        if (number >= 0 && number < studentsCount) {
            Student student = students.get(number);
            return student;
        }
        return null;

    }

    public int getStudentNumber(Student student) {

        if (student != null) {
            return students.indexOf(student);
        }

        return -1;
    }

    public Student getStudentFromData(String name, String surname, StudGroup group, Date date) {
        for (Student student : students) {
            if (student.getName().equals(name) &&
                    student.getSurname().equals(surname) &&
                    DateUtils.isFormatEquals(student.getEnrollmentDate(), date) &&
                    ((student.getGroup() == null && group == null) || (student.getGroup().equals(group))) ) {
                return student;

            }
        }
        return null;
    }

    public boolean isStudentExists(Student student) {
        return getStudentNumber(student) >= 0;
    }

    /*-----*/

    public StudGroup getGroupByNumber(int number) {

        int groupsCount = getGroupsCount();
        if (number >= 0 && number < groupsCount) {
            StudGroup group = groups.get(number);
            return group;
        }
        return null;

    }

    public int getGroupNumber(StudGroup group) {

        if (group != null) {
            return groups.indexOf(group);
        }

        return -1;

    }

    public StudGroup getGroupFromData(String name, String faculty) {
        for (StudGroup group : groups) {
            if (group.getName().equals(name) &&
                    group.getFaculty().equals(faculty)) {
                return group;
            }
        }
        return null;
    }

    public boolean isGroupExists(StudGroup group) {
        return getGroupNumber(group) >= 0;
    }

    public StudGroup getGroupFromName(String name) {

        for (StudGroup currGroup : groups) {
            if (currGroup.getName().equals(name)) {
                return currGroup;
            }
        }

        return null;

    }

    /*--------------------*/

    public Student addStudent(String name, String surname, StudGroup group, Date enrollmentDate) {
        Student student = new Student(name, surname, group, enrollmentDate);
        students.add(student);
        updateStudents();
        return student;
    }

    public Student addStudent(String name, String surname, StudGroup group) {
        return addStudent(name, surname, group, new Date());
    }

    public StudGroup addGroup(String name, String faculty) {
        StudGroup group = new StudGroup(name, faculty);
        groups.add(group);
        updateGroups();
        return group;
    }

    /*-----*/

    public void deleteStudent(int number) {

        Student student = getStudentByNumber(number);
        if (student == null) return;

        students.remove(number);
        updateStudents();

    }

    public void deleteGroup(int number) {

        StudGroup group = getGroupByNumber(number);
        if (group == null) return;

        for (Student student : students) {
            if (student.getGroup().equals(group)) {
                student.setGroup(null);
            }
        }

        groups.remove(number);
        updateGroups();

    }

    /*-----*/

    public void setStudentData(int number, String name, String surname, StudGroup group, Date enrollmentDate ) {

        Student student = getStudentByNumber(number);
        if (student == null) return;

        student.setName(name);
        student.setSurname(surname);
        student.setGroup(group);
        student.setEnrollmentDate(enrollmentDate);
        updateStudents();

    }

    public void setGroupData(int number, String name, String faculty) {

        StudGroup group = getGroupByNumber(number);
        if (group == null) return;

        group.setName(name);
        group.setFaculty(faculty);
        updateGroups();

    }

    /*----------------*/

    private String getAbsoluteDataCatalog() {
        return new File("").getAbsolutePath();
    }

    private void checkDataFilesCatalog() {
        String dataFilesCatalog = getAbsoluteDataCatalog() + "\\" + DATA_PACKAGE;
        File catalog = new File(dataFilesCatalog);
        if (!catalog.exists()) {
            catalog.mkdir();
        }
    }

    private File getStudentsDataFile() {
        checkDataFilesCatalog();
        String dataFilePatch = getAbsoluteDataCatalog() + "\\" + DATA_PACKAGE + "\\" + STUDENTS_DATA_FILE;
        File file = new File(dataFilePatch);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private File getGroupsDataFile() {
        checkDataFilesCatalog();
        String dataFilePatch = getAbsoluteDataCatalog() + "\\" + DATA_PACKAGE + "\\" + GROUPS_DATA_FILE;
        File file = new File(dataFilePatch);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void createStudents() {

        ArrayList<Student> fileStudents = null;

        ObjectInputStream in = null;
        try {
            File file = getStudentsDataFile();
            if (file.length() > 0) {

                in = new ObjectInputStream(new FileInputStream(file));
                fileStudents = (ArrayList<Student>) in.readObject();

                /* anti-group-duplicating */
                for (Student student : fileStudents) {
                    StudGroup group = student.getGroup();
                    if (group != null) {
                        student.setGroup(getGroupFromData(group.getName(), group.getFaculty()));
                    }
                }

                students = FXCollections.observableArrayList(fileStudents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void createGroups() {

        ObjectInputStream in = null;
        try {
            File file = getGroupsDataFile();
            if (file.length() > 0) {
                in = new ObjectInputStream(new FileInputStream(file));
                groups = FXCollections.observableArrayList((ArrayList<StudGroup>) in.readObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void updateStudents() {

        ObjectOutputStream out = null;
        try {

            File file = getStudentsDataFile();

            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(new ArrayList<Student>(students));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void updateGroups() {

        ObjectOutputStream out = null;
        try {

            File file = getGroupsDataFile();

            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(new ArrayList<StudGroup>(groups));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
