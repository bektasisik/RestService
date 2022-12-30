package TYS.RestService.rest;

import TYS.RestService.domain.StudentAttendance;
import TYS.RestService.service.StudentAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-attendances")
public class StudentAttendanceController {

    @Autowired
    private StudentAttendanceService studentAttendanceService;

    @GetMapping
    public List<StudentAttendance> getStudentAttendanceLists(@RequestParam("attendanceId") @Nullable Integer attendanceId,
                                                             @RequestParam("studentId") @Nullable Integer studentId) {
        return studentAttendanceService.choseList(attendanceId,studentId);
    }
}