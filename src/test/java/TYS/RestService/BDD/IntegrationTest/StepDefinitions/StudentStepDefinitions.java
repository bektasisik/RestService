package TYS.RestService.BDD.IntegrationTest.StepDefinitions;

import TYS.RestService.BDD.IntegrationTest.Runner.CucumberIntegrationTest;
import TYS.RestService.BDD.IntegrationTest.service.ScenarioContext;
import TYS.RestService.domain.Student;
import TYS.RestService.dto.StudentCreateDTO;
import TYS.RestService.rest.StudentController;
import TYS.RestService.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Scope(SCOPE_CUCUMBER_GLUE)
@WebMvcTest(controllers = StudentController.class)
public class StudentStepDefinitions extends CucumberIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    StudentService studentService;
    ;
    StudentCreateDTO studentCreateDTO;
    String requestBody;
    @Autowired
    ScenarioContext scenerioContext;

    /* @Given  */
    @Given("{string} adi {string} soyadi ile talebeler eklenmiş olsun")
    public Student talebelerEklenmisOlsun(String name, String surname) {
        studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setName(name);
        studentCreateDTO.setSurname(surname);
        return studentService.addStudent(studentCreateDTO);
    }

    @Given("Aşağıdaki talebeler eklenmiş olsun")
    public void asagidakiTalebelerEklenmisOlsun(@NotNull DataTable table) {
        scenerioContext.setContext("beforeListSize", studentService.getStudents().size());
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            Student student = talebelerEklenmisOlsun(columns.get("adi"), columns.get("soyadi"));
            scenerioContext.setContext(columns.get("id"), student);
        }
    }

    /* @When  */
    @When("Kullanıcı talebeler listesini talep ettiğinde")
    public void talebeListesiGoruntulenmekIstediginde() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);

    }

    @When("Kullanıcı talebenin Adını {string} Soyadını {string} olarak kaydetmek istediğinde")
    public void kullaniciTalebeKaydetmekIstediginde(String name, String surname) throws Exception {
        studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setName(name);
        studentCreateDTO.setSurname(surname);

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(studentCreateDTO);

        ResultActions result = mockMvc.perform(post("/api/v1/students")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("Kullanıcı {string} idli talebeyi silmek isteiğinde")
    public void kullaniciNumaraliTalebeyiSilmekIstediginde(String studentId) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(studentId);

        ResultActions result = mockMvc.perform(delete("/api/v1/students/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("Kullanıcı {int} idli talebeyi {string} adi {string} soyadi olarak güncellemek istediğinde")
    public void kullaniciIdliTalebeyiOlarakGuncellemekIstediginde(int studentId, String name, String surname) throws Exception {
        Student student = (Student) scenerioContext.getContext(String.valueOf(studentId));
        student.setName(name);
        student.setSurname(surname);

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(student);

        ResultActions result = mockMvc.perform(put("/api/v1/students/{studentId}", studentId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("Kullanıcı {int} idli talebeyi silmek istediğinde")
    public void kullaniciIdliTalebeyiSilmekIstediginde(int studentId) throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/v1/students/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        scenerioContext.setContext("result", result);
    }

    @When("Kullanıcı talebenin Adını {string} Soyadını {string} olarak hatalı şekilde kaydetmek istediğinde")
    public void kullaniciTalebeninAdiniSoyadiniOlarakHataliSekildeKaydetmekIstediginde(String name, String surname) throws Exception {
        try {
            studentCreateDTO = new StudentCreateDTO();
            studentCreateDTO.setName(name);
            studentCreateDTO.setSurname(surname);
            Student student = studentService.addStudent(studentCreateDTO);
            student.setName(name);
            student.setSurname(surname);

            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(student);

            ResultActions result = mockMvc.perform(post("/api/v1/students")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());
            scenerioContext.setContext("result", result);
        } catch (Exception e) {
            scenerioContext.setContext("error", "Name and Surname must be filled");
        }
    }

    @When("Kullanıcı {int} idli talebeyi hatalı şekilde güncellemek istediğinde")
    public void kullaniciIdliTalebeyiHataliSekildeGuncellemekIstediginde(int studentId) throws Exception {
        try {
            Student student = (Student) scenerioContext.getContext(String.valueOf(studentId));
            student.setName(null);
            student.setSurname(null);

            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(student);

            ResultActions result = mockMvc.perform(put("/api/v1/students/{studentId}", studentId)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());
            scenerioContext.setContext("result", result);
        } catch (Exception e) {
            scenerioContext.setContext("error", "Name and Surname must be filled");
        }
    }

    /* @Then  */
    @Then("Talebe listesi toplam {int} adet talebe içermelidir")
    public void talebeListesiToplamAdetTalebeIcermelidir(int expectedSize) throws Exception {
        int beforeListSize = (int) scenerioContext.getContext("beforeListSize");
        List<Student> students = studentService.getStudents();
        Assert.assertEquals(expectedSize + beforeListSize, students.size());
    }

    @Then("İşlemin başarılı olarak gerçekleşmesi beklenir")
    public void basariliStatusKoduDonmesi() throws Exception {
        ResultActions result = (ResultActions) scenerioContext.getContext("result");
        Assert.assertEquals(200, result.andReturn().getResponse().getStatus());

    }

    @Then("{int} idli talebenin adı {string} soyadı {string} olmalıdır")
    public void idliTalebeninAdiSoyadiOlmalidir(int studentId, String name, String surname) {
        Student student = (Student) scenerioContext.getContext(String.valueOf(studentId));
        Assert.assertEquals(name, student.getName());
        Assert.assertEquals(surname, student.getSurname());
    }

    @Then("Geriye başarısız status kodu dönmesi")
    public void geriyeBasarisizStatusKoduDonmesi() {
        scenerioContext.getContext("error");
        Assert.assertEquals("Name and Surname must be filled", scenerioContext.getContext("error"));
    }

}
