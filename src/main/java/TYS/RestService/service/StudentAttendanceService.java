package TYS.RestService.service;

import TYS.RestService.domain.StudentAttendance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentAttendanceService {
    public List<StudentAttendance> studentAttendances = new ArrayList<>();
    public List<StudentAttendance> getStudentAttendances() {
        return studentAttendances;
    }
    public List<StudentAttendance> getStudenstsByAttendanceId(int attendanceId) {
        return studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == attendanceId).toList();
    }
    public List<StudentAttendance> getAttendancesByStudentId(int studentId) {
        return studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getStudent().getId() == studentId).toList();
    }
}
