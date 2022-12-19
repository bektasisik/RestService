package TYS.RestService.BDD.IntegrationTest.StepDefinitions;

import TYS.RestService.BDD.IntegrationTest.Runner.CucumberIntegrationTest;
import TYS.RestService.BDD.IntegrationTest.service.ScenarioContext;
import TYS.RestService.domain.Attendance;
import TYS.RestService.domain.Student;
import TYS.RestService.dto.AttendanceCreateDTO;
import TYS.RestService.dto.StudentAttendanceCreateDto;
import TYS.RestService.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Scope(SCOPE_CUCUMBER_GLUE)
public class AttendanceStepDefinitions extends CucumberIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    AttendanceService attendanceService;

    String requestBody;
    AttendanceCreateDTO attendanceCreateDTO;
    @Autowired
    StudentStepDefinitions studentStepDefinitions;

    @Autowired
    ScenarioContext scenerioContext;

    /* @Given  */
    @Given("Aşağıdaki yoklamalar eklenmiş olsun")
    public void asagidakiYoklamalarEklenmisOlsun(@NotNull DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            Attendance attendance = new Attendance(attendanceService.getSequence(), columns.get("vakit"));
            scenerioContext.setContext(columns.get("index"), attendance);
        }
    }

    @Given("Aşağıdaki talebeler listeye eklenmiş olsun")
    public void asagidakiTalebelerEklenmisOlsun(@NotNull DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            Student student = studentStepDefinitions.talebelerEklenmisOlsun(columns.get("adi"), columns.get("soyadi"));
            scenerioContext.setContext(columns.get("index"), student);
        }
    }

    /* @When  */
    @When("Yoklama listesi görüntülenmek istendiğinde")
    public void yoklamaListesiGoruntulenmekIstendiginde() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/attendances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }


    @When("{string} Vakti seçilmiş olsun")
    public void vaktiSecilmisOlsun(String prayTime) {

    }

    @When("{string} vakti için mevcut talebelerin yoklamaları alındığı zaman")
    public void vaktiIcinMevcutTalebelerinYoklamalariAlindigiZaman(String prayerTime, @NotNull DataTable table) throws Exception {
        attendanceCreateDTO = new AttendanceCreateDTO();
        attendanceCreateDTO.setPrayerTime(prayerTime);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            Student student = (Student) scenerioContext.getContext(columns.get("index"));
            attendanceCreateDTO.setStudentAttendanceDto(Collections.singletonList(
                    new StudentAttendanceCreateDto(
                            (int) student.getId(),
                            Boolean.getBoolean(columns.get("durum")))));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(attendanceCreateDTO);

        ResultActions result = mockMvc.perform(post("/api/v1/attendances")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("Kullanıcı seçilen {string} indexli yoklamayı silmek istediğinde")
    public void kullaniciSecilenIndexliYoklamayiSilmekIstediginde(String attendanceId) throws Exception {
        Attendance attendance = (Attendance) scenerioContext.getContext(attendanceId);
        ResultActions result = mockMvc.perform(delete("/api/v1/attendances/{attendanceId}", attendance.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("Kullanıcı {string} idlı yoklamayı güncellemek istediğinde")
    public void kullaniciIdliYoklamayiGuncellemekIstediginde(String attendanceId, DataTable table) throws Exception {
        Attendance attendance = (Attendance) scenerioContext.getContext(attendanceId);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            attendance.setPrayerTime(columns.get("vakit"));
        }

        scenerioContext.setContext(String.valueOf(table.columns(Integer.parseInt("index"))), attendance);

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(attendanceCreateDTO);

        ResultActions result = mockMvc.perform(put("/api/v1/attendances/{attendanceId}", attendance.getId())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    /* @Then  */
    @Then("İşlem başarılı bir şekilde tamamlanmalıdır")
    public void islemBasariliBirSekildeTamamlanmalidir() throws Exception {
        ResultActions result = (ResultActions) scenerioContext.getContext("result");
        Assert.assertEquals(200, result.andReturn().getResponse().getStatus());
    }

    @Then("Yoklamada yok yazılan talebelerin toplam devamsızlığı {int} artmalı")
    public void yoklamadaYokYazilanTalebelerinToplamDevamsizligiArtmali(int absence) {
        for (int i = 0; i < attendanceCreateDTO.getStudentAttendanceDto().size(); i++) {
            if (!attendanceCreateDTO.getStudentAttendanceDto().get(i).getIsAbsence()) {
                Student student = (Student) scenerioContext.getContext(String.valueOf(i));
                Assert.assertEquals(absence, student.getAbsent());
            }
        }
    }

    @Then("Silinen yoklamada yok yazılan talebelerin toplam devamsızlığı bir azaltılmalı")
    public void silinenYoklamadaYokYazilanTalebelerinToplamDevamsizligiBirAzaltilmali() {
        for (int i = 0; i < attendanceCreateDTO.getStudentAttendanceDto().size(); i++) {
            if (!attendanceCreateDTO.getStudentAttendanceDto().get(i).getIsAbsence()) {
                Student student = (Student) scenerioContext.getContext(String.valueOf(i));
                Assert.assertNotEquals(1, student.getAbsent());
            }
        }
    }

    @Then("{string} idli yoklamayı güncelleyince {string} vakitli olarak listelenmelidir")
    public void idliYoklamayiGuncelleyinceVakitliOlarakListelenmelidir(String attendanceId, String prayerTime) {
        Attendance attendance = (Attendance) scenerioContext.getContext(attendanceId);
        Assert.assertEquals(prayerTime, attendance.getPrayerTime());
    }
}
