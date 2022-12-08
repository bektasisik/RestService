package TYS.RestService.service;

import TYS.RestService.domain.Attendance;
import TYS.RestService.domain.Student;
import TYS.RestService.domain.StudentAttendance;
import TYS.RestService.dto.AttendanceCreateDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    private final List<Attendance> attendances = new ArrayList<>();
    private int sequence = 1;
    private final StudentService studentService;
    private final StudentAttendanceService studentAttendanceService;

    public AttendanceService(StudentService studentService, StudentAttendanceService studentAttendanceService) {
        this.studentService = studentService;
        this.studentAttendanceService = studentAttendanceService;
    }

    public int getSequence() {
        return sequence++;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Attendance getAttendance(int id) {
        return attendances.stream().filter(
                attendance -> attendance.getId() == id).findFirst().orElseThrow();
    }

    public boolean isEmpty(String prayertime) {
        return prayertime != null && !prayertime.equals("");
    }

    public void takeAttendance(AttendanceCreateDTO attendanceCreateDTO) throws NullPointerException {
        if (!isEmpty(attendanceCreateDTO.getPrayerTime())) {
            throw new NullPointerException();
        }
        Attendance attendance = new Attendance(getSequence(), attendanceCreateDTO.getPrayerTime());
        this.attendances.add(attendance);

        attendanceCreateDTO.getStudentAttendanceDto().forEach(studentAttendanceCreateDto -> {
            Student student = this.studentService.getStudent(studentAttendanceCreateDto.getStudentId());
            Boolean isAbsence = studentAttendanceCreateDto.getIsAbsence();
            this.studentAttendanceService.getStudentAttendances().add(new StudentAttendance(student, attendance, isAbsence));
            if (!isAbsence) {
                student.increaseAbsent();
            }
        });
    }

    //todo
    public void updateAttendance(int id, AttendanceCreateDTO attendanceCreateDTO) {
        System.out.println(studentAttendanceService.studentAttendances);

        Attendance attendance = getAttendance(id);
        attendance.setPrayerTime(attendanceCreateDTO.getPrayerTime());

        List<StudentAttendance> oldStudentAttendances = this.studentAttendanceService.studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == id).toList();

        this.studentAttendanceService.studentAttendances = this.studentAttendanceService.getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() != id).toList();

        attendanceCreateDTO.getStudentAttendanceDto().forEach(studentAttendanceCreateDto -> {
            Student student = this.studentService.getStudent(studentAttendanceCreateDto.getStudentId());
            Boolean isAbsence = studentAttendanceCreateDto.getIsAbsence();
            this.studentAttendanceService.getStudentAttendances().add(new StudentAttendance(student, attendance, isAbsence));
            if (!isAbsence) {
                student.increaseAbsent();
            }
        });

        List<StudentAttendance> newStudentAttendances = this.studentAttendanceService.getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == id).toList();

        oldStudentAttendances.forEach(oldStudentAttendance -> {
            StudentAttendance newStudentAttendance = newStudentAttendances.stream().filter(
                    studentAttendance -> studentAttendance.getStudent().getId() == oldStudentAttendance.getStudent().getId()).findFirst().orElseThrow();
            if (oldStudentAttendance.getIsAbsence() != newStudentAttendance.getIsAbsence()) {
                if (oldStudentAttendance.getIsAbsence()) {
                    newStudentAttendance.getStudent().increaseAbsent();
                } else {
                    newStudentAttendance.getStudent().decraseAbsent();
                }
            }
        });
    }

    public void deleteAttendance(int id) {
        this.studentAttendanceService.getStudentAttendances().forEach(studentAttendance -> {
            if (studentAttendance.getAttendance().getId() == id) {
                if (!studentAttendance.getIsAbsence()) {
                    studentAttendance.getStudent().decraseAbsent();
                }
            }
        });
        this.attendances.remove(getAttendance(id));
        this.studentAttendanceService.studentAttendances = this.studentAttendanceService.getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() != id).toList();
    }
}
