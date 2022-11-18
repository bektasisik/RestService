package TYS.RestService.domain;

public class StudentAttendance {
    private final Student student;
    private final Attendance attendance;
    private final Boolean isAbsence;

    public Attendance getAttendance() {
        return attendance;
    }
    public Student getStudent() {
        return student;
    }
    public Boolean getIsAbsence(){
        return isAbsence;
    }

    public StudentAttendance(Student student, Attendance attendance, Boolean isAbsence) {
        this.student = student;
        this.attendance = attendance;
        this.isAbsence = isAbsence;
    }

    @Override
    public String toString() {
        return "StudentAttendance{" +
                "student=" + getStudent() +
                ", attendance=" + getAttendance() +
                ", isAbsence=" + getIsAbsence() +
                '}';
    }
}
