package TYS.RestService.rest;

import TYS.RestService.domain.Attendance;
import TYS.RestService.domain.StudentAttendance;
import TYS.RestService.dto.AttendanceCreateDTO;
import TYS.RestService.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/{id}")
    public Attendance getAttendance(@PathVariable int id){
        return attendanceService.getAttendance(id);
    }

    @GetMapping
    public List<Attendance> getAttendances(){
        return attendanceService.getAttendances();
    }

    @PostMapping
    public void takeAttendance(@RequestBody AttendanceCreateDTO attendanceCreateDTO) throws NullPointerException {
        attendanceService.takeAttendance(attendanceCreateDTO);
    }

    @PutMapping("/{id}")
    public void updateAttendance(@PathVariable int id, @RequestBody AttendanceCreateDTO attendanceCreateDTO) {
        attendanceService.updateAttendance(id, attendanceCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAttendance(@PathVariable int id) {
        attendanceService.deleteAttendance(id);
    }
}