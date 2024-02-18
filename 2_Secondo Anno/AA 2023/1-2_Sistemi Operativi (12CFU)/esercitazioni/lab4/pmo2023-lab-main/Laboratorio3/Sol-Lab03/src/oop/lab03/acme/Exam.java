package oop.lab03.acme;

import java.util.Arrays;

public class Exam {

    private final int id;
    private final int nMaxStudents;
    private int nRegisteredStudents;
    private final String courseName;
    private Student[] students; // Expanding array!
    private final Professor professor;
    private final ExamRoom room;

    public Exam(final int id, final int nMaxStudents, final String courseName, final Professor professor, final ExamRoom room) {
        this.id = id;
        this.nMaxStudents = nMaxStudents;
        this.nRegisteredStudents = 0;
        this.courseName = courseName;
        this.professor = professor;
        this.room = room;
        this.students = new Student[0];
    }

    private void expand() {
        /*
         * We need to copy the whole array every time... pretty expensive...
         * 
         * (any better idea, students?)
         */
        students = Arrays.copyOf(students, students.length + 1);
    }

    public void registerStudent(final Student stud) {
        if (nRegisteredStudents < nMaxStudents) {
            expand();
            students[nRegisteredStudents] = stud;
            nRegisteredStudents++;
        }
    }

    public String toString() {
        return "Exam [id=" + this.id
                + ", nMaxStudents=" + this.nMaxStudents
                + ", nRegisteredStudents=" + this.nRegisteredStudents
                + ", courseName=" + this.courseName
                + ", students=" + Arrays.toString(this.students)
                + ", professor=" + this.professor
                + ", room=" + this.room.toString() + "]";
    }

    public int getId() {
        return this.id;
    }

    public int getNMaxStudents() {
        return this.nMaxStudents;
    }

    public int getnRegisteredStudents() {
        return this.nRegisteredStudents;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public Student[] getStudents() {
        return Arrays.copyOf(this.students, this.students.length);
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public ExamRoom getRoom() {
        return this.room;
    }
}
