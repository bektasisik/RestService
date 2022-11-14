package TYS.RestService.service;

import TYS.RestService.domain.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentService {
    List<Student> students = new ArrayList<>();

    public StudentService() {
        students.add(new Student(1, "emre", "yavuz"));
    }

    public List<Student> getStudents(){
        return students;
    }
}
