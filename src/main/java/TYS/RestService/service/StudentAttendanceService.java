package TYS.RestService.service;

import TYS.RestService.domain.StudentAttendance;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentAttendanceService {
    public List<StudentAttendance> studentAttendances = new ArrayList<>();

    public List<StudentAttendance> choseList(@Nullable Integer attendanceId,@Nullable Integer studentId) {
        if (attendanceId != null)
            return getStudenstsByAttendanceId(attendanceId);
        else if (studentId != null)
            return getAttendancesByStudentId(studentId);
        return getStudentAttendances();
    }

    public List<StudentAttendance> getStudentAttendances() {
        return studentAttendances;
    }

    public List<StudentAttendance> getStudenstsByAttendanceId(Integer attendanceId) {
        return studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == attendanceId).toList();
    }

    public List<StudentAttendance> getAttendancesByStudentId(Integer studentId) {
        return studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getStudent().getId() == studentId).toList();
    }
}