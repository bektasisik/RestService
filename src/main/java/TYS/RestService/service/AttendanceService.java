package TYS.RestService.service;

import TYS.RestService.domain.Attendance;
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
            StudentAttendance studentAttendance = new StudentAttendance(
                    this.studentService.getStudent(studentAttendanceCreateDto.getStudentId()),
                    attendance,
                    studentAttendanceCreateDto.getIsAbsence());
            if (!studentAttendanceCreateDto.getIsAbsence()) {
                this.studentService.getStudent(studentAttendanceCreateDto.getStudentId()).increaseAbsent();
            }
            this.studentAttendanceService.getStudentAttendances().add(studentAttendance);
        });
    }

    //todo
    public void updateAttendance(int id, AttendanceCreateDTO attendanceCreateDTO) {
        System.out.println(studentAttendanceService.studentAttendances);
//        this.studentAttendanceService.studentAttendances.clear();
//        studentAttendanceService.studentAttendances = new StudentAttendance()

        Attendance attendance = getAttendance(id);
        attendance.setPrayerTime(attendanceCreateDTO.getPrayerTime());

        List<StudentAttendance> oldStudentAttendances = this.studentAttendanceService.studentAttendances.stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() == id).toList();

        this.studentAttendanceService.studentAttendances = this.studentAttendanceService.getStudentAttendances().stream().filter(
                studentAttendance -> studentAttendance.getAttendance().getId() != id).toList();

        attendanceCreateDTO.getStudentAttendanceDto().forEach(studentAttendanceCreateDto -> {
            StudentAttendance studentAttendance = new StudentAttendance(
                    this.studentService.getStudent(studentAttendanceCreateDto.getStudentId()),
                    getAttendance(id),
                    studentAttendanceCreateDto.getIsAbsence());
            if (!studentAttendanceCreateDto.getIsAbsence()) {
                this.studentService.getStudent(studentAttendanceCreateDto.getStudentId()).increaseAbsent();
            }
            this.studentAttendanceService.getStudentAttendances().add(studentAttendance);
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
