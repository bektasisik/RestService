package TYS.RestService.rest;

import TYS.RestService.domain.StudentAttendance;
import TYS.RestService.service.StudentAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-attendances")
public class StudentAttendanceController {

    @Autowired
    private StudentAttendanceService studentAttendanceService;

    @GetMapping
    public List<StudentAttendance> getStudentAttendance(){
        return studentAttendanceService.getStudentAttendances();
    }

    @GetMapping("/searchByAttendance")
    public List<StudentAttendance> getStudentsByAttendanceId(@RequestParam("attendanceId") String attendanceId) {
        return studentAttendanceService.getStudenstsByAttendanceId(Integer.parseInt(attendanceId));
    }

    @GetMapping("/searchByStudent")
    public List<StudentAttendance> getAttendancesByStudentId(@RequestParam("studentId") String studentId) {
        return studentAttendanceService.getAttendancesByStudentId(Integer.parseInt(studentId));
    }
}

//queryString
//pathVariable