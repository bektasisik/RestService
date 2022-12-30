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

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public void setIsAbsence(Boolean isAbsence) {
        this.isAbsence = isAbsence;
    }

    public StudentAttendanceCreateDto(int studentId, boolean isAbsence){
        this.studentId = studentId;
        this.isAbsence = isAbsence;
    }
}
