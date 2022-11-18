package TYS.RestService.dto;

import java.util.List;

public class AttendanceCreateDTO {
    private String prayerTime;
    private List<StudentAttendanceCreateDto> studentAttendanceDto;

    public String getPrayerTime() {
        return prayerTime;
    }

    public List<StudentAttendanceCreateDto> getStudentAttendanceDto() {
        return studentAttendanceDto;
    }
}
