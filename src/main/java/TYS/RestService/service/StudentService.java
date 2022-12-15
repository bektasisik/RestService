package TYS.RestService.service;

import TYS.RestService.domain.Student;
import TYS.RestService.dto.StudentCreateDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private int sequence = 1;

    public int getSequence() {
        return sequence++;
    }

    public StudentService() {
//        students.add(new Student(getSequence(), "Veli", "Çam"));
//        students.add(new Student(getSequence(), "Abdurrahman", "Kutlu"));
//        students.add(new Student(getSequence(), "Emre", "Yavuz"));
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getStudent(int studentId) {
        return students.stream().filter(student -> student.getId() == studentId).findFirst().orElseThrow();
    }

    public Student addStudent(@NotNull StudentCreateDTO studentCreateDTO) throws IllegalArgumentException {
        validateStudent(studentCreateDTO.getName(), studentCreateDTO.getSurname());
        Student student = new Student(getSequence(), studentCreateDTO.getName(), studentCreateDTO.getSurname());
        students.add(student);
        return student;
    }

    public void validateStudent(String name, String surname) throws  IllegalArgumentException {
        if (!(isValidName(name, surname))) {
            //todo
            throw new IllegalArgumentException();
        }
    }

    public boolean isValidName(String name, String surname) {
        if (name != null && name.length() > 2 && name.length() < 20 && name.matches("[a-zA-Z ğüşöçıİĞÜŞÖÇ]+\\S\\D\\Z")) {
            return surname != null && surname.length() >= 2 && surname.length() < 20 && surname.matches("[a-zA-Z ğüşöçıİĞÜŞÖÇ]+\\D\\Z");
        }
        return false;
    }

    public void updateStudent(int studentId, @NotNull StudentCreateDTO studentCreateDTO) throws IllegalArgumentException {
        validateStudent(studentCreateDTO.getName(), studentCreateDTO.getSurname());
        Student student = getStudent(studentId);
        student.setName(studentCreateDTO.getName());
        student.setSurname(studentCreateDTO.getSurname());
    }

    public void deleteStudent(int studentId) {
        students.remove(getStudent(studentId));
    }
}