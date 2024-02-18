package oop.lab03.acme;

import java.util.Arrays;

public class Professor implements User {

    private static final String DOT = ".";
    private final int id;
    private final String name;
    private final String surname;
    private final String password;
    private String[] courses;

    public Professor(final Integer id,
            final String name, final String surname, final String password,
            final String[] courses) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.courses = Arrays.copyOf(courses, courses.length);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String[] getCourses() {
        return Arrays.copyOf(this.courses, this.courses.length);
    }

    public void replaceCourse(final String subject, final int index) {
        if (index < this.courses.length) {
            this.courses[index] = subject;
        }
    }

    public void replaceAllCourses(final String[] newCourses) {
        this.courses = Arrays.copyOf(newCourses, newCourses.length);
    }

    public String toString() {
        return "Professor [id=" + this.id
                + ", name=" + this.name
                + ", surname=" + this.surname
                + ", subjects=" + Arrays.toString(this.courses) + "]";
    }

    public String getUsername() {
        return this.name + Professor.DOT + this.surname;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDescription() {
        return this.toString();
    }
}
