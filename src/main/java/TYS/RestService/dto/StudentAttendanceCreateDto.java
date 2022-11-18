package TYS.RestService.dto;

public class StudentAttendanceCreateDto {
    private int studentId;
    private Boolean isAbsence;

    public int getStudentId() {
        return studentId;
    }

    public Boolean getIsAbsence() {
        return isAbsence;
    }
}
