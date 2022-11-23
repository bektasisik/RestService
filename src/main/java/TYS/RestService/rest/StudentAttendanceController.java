package TYS.RestService.rest;

import TYS.RestService.domain.StudentAttendance;
import TYS.RestService.service.StudentAttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-attendances")
public class StudentAttendanceController {

    StudentAttendanceService studentAttendanceService;

    public StudentAttendanceController(StudentAttendanceService studentAttendanceService) {
        this.studentAttendanceService = studentAttendanceService;
    }

    @GetMapping
    public List<StudentAttendance> getStudentAttendance(){
        return studentAttendanceService.getStudentAttendances();
    }

    @GetMapping("/{attendanceId}/students")
    public List<StudentAttendance> getStudentsByAttendanceId(@PathVariable int attendanceId) {
        return studentAttendanceService.getStudenstsByAttendanceId(attendanceId);
    }

    @GetMapping("/{studentId}/attendances")
    public List<StudentAttendance> getAttendancesByStudentId(@PathVariable int studentId) {
        return studentAttendanceService.getAttendancesByStudentId(studentId);
    }
}