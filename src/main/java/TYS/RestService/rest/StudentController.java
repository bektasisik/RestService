package TYS.RestService.rest;

import TYS.RestService.domain.Student;
import TYS.RestService.dto.StudentCreateDTO;
import TYS.RestService.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id){
        return studentService.getStudent(id);
    }

    @PostMapping
    public void addStudent(@RequestBody StudentCreateDTO studentCreateDTO) throws IllegalClassFormatException {
        this.studentService.addStudent(studentCreateDTO);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable int id, @RequestBody StudentCreateDTO studentCreateDTO) throws IllegalClassFormatException {
        this.studentService.updateStudent(id, studentCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id){
        this.studentService.deleteStudent(id);
    }
}
