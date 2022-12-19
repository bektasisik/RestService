package TYS.RestService.BDD.IntegrationTest.StepDefinitions;


import TYS.RestService.BDD.IntegrationTest.Runner.CucumberIntegrationTest;
import TYS.RestService.BDD.IntegrationTest.service.ScenarioContext;
import TYS.RestService.domain.Attendance;
import TYS.RestService.domain.Student;
import TYS.RestService.service.StudentAttendanceService;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Scope(SCOPE_CUCUMBER_GLUE)
public class StudentAttendanceStepDefinitions extends CucumberIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    StudentAttendanceService studentAttendanceService;

    @Autowired
    ScenarioContext scenerioContext;

    @When("{string} yoklaması görüntülenmek istendiğinde")
    public void yoklamasiGoruntulenmekIstendiginde(String attendanceIndex) throws Exception {
        Attendance attendance = (Attendance) scenerioContext.getContext(attendanceIndex);
        ResultActions result = mockMvc.perform(get("/api/v1/student-attendances")
                        .queryParam("attendanceId", String.valueOf(attendance.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("{string} numaralı talebenin yoklamaları görüntülenmek istendiğinde")
    public void numaraliTalebeninYoklamalariGoruntulenmekIstendiginde(String studentIndex) throws Exception {
        Student student = (Student) scenerioContext.getContext(studentIndex);
        ResultActions result = mockMvc.perform(get("/api/v1/student-attendances")
                        .queryParam("studentId", String.valueOf(student.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }
}
