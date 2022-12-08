package TYS.RestService.service;

import TYS.RestService.domain.Attendance;
import TYS.RestService.domain.StudentAttendance;
import TYS.RestService.dto.AttendanceCreateDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    private List<Attendance> attendances = new ArrayList<>();
    private int sequence = 1;
    private List<StudentAttendance> studentAttendances = new ArrayList<>();
    private final StudentService studentService;

    public AttendanceService(StudentService studentService) {
        this.studentService = studentService;
    }

    public int getSequence() {
        return sequence++;
    }

    public List<StudentAttendance> getStudentAttendances() {
        return studentAttendances;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Attendance getAttendance(int id) {
        return attendances.stream().filter(attendance -> attendance.getId() == id).findFirst().orElseThrow();
    }

    public List<StudentAttendance> getAttendanceById(int id) {
        return studentAttendances.stream().filter(studentAttendance -> studentAttendance.getAttendance().getId() == id).toList();
    }

    public List<StudentAttendance> getAttendanceByStudentId(int studentId) {
        return studentAttendances.stream().filter(studentAttendance -> studentAttendance.getStudent().getId() == studentId).toList();
    }

    public boolean isEmpty(String prayertime) {
        return prayertime != null && !prayertime.equals("");
    }

    public void takeAttendance(AttendanceCreateDTO attendanceCreateDTO) throws NullPointerException {
        if (!isEmpty(attendanceCreateDTO.getPrayerTime())) {
            //todo
            throw new NullPointerException();
        }
        Attendance attendance = new Attendance(getSequence(), attendanceCreateDTO.getPrayerTime());
        this.attendances.add(attendance);

        attendanceCreateDTO.getStudentAttendanceDto().forEach(studentAttendanceCreateDto -> {
            StudentAttendance studentAttendance = new StudentAttendance(
                    studentService.getStudent(studentAttendanceCreateDto.getStudentId()),
                    attendance,
                    studentAttendanceCreateDto.getIsAbsence());
            if (!studentAttendanceCreateDto.getIsAbsence()) {
                studentService.getStudent(studentAttendanceCreateDto.getStudentId()).increaseAbsent();
            }
            getStudentAttendances().add(studentAttendance);
        });
    }

    public void updateAttendance(int id, @NotNull AttendanceCreateDTO attendanceCreateDTO) {
        getAttendance(id).setPrayerTime(attendanceCreateDTO.getPrayerTime());

        List<StudentAttendance> oldStudentAttendances = getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == id).toList();

        this.studentAttendances = getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() != id).toList();

        attendanceCreateDTO.getStudentAttendanceDto().forEach(studentAttendanceCreateDto -> {
            if (!studentAttendanceCreateDto.getIsAbsence()) {
                studentService.getStudent(studentAttendanceCreateDto.getStudentId()).increaseAbsent();
            }
            getStudentAttendances().add(new StudentAttendance(
                    studentService.getStudent(studentAttendanceCreateDto.getStudentId()),
                    getAttendance(id),
                    studentAttendanceCreateDto.getIsAbsence()));
        });

        List<StudentAttendance> newStudentAttendances = getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == id).toList();

        oldStudentAttendances.forEach(oldStudentAttendance -> {
            StudentAttendance newStudentAttendance = newStudentAttendances.stream().filter(
                    studentAttendance -> studentAttendance.getStudent().getId() == oldStudentAttendance.getStudent().getId()).findAny().orElseThrow();

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
        this.studentAttendances.forEach(studentAttendance -> {
            if (!studentAttendance.getIsAbsence()) {
                studentAttendance.getStudent().decraseAbsent();
            }
        });
        this.attendances = attendances.stream().filter(
                attendance -> attendance.getId()!=id).toList();
        this.studentAttendances = studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() != id).toList();
    }
}