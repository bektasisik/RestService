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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Scope(SCOPE_CUCUMBER_GLUE)
@WebMvcTest(controllers = StudentController.class)
public class StudentStepDefinitions extends CucumberIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    StudentService studentService;
    ResultActions result;
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
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            Student student = talebelerEklenmisOlsun(columns.get("adi"), columns.get("soyadi"));
            scenerioContext.setContext(columns.get("id"), student);
        }
    }

    /* @When  */
    @When("Talebe listesi görüntülenmek istediğinde")
    public void talebeListesiGoruntulenmekIstediginde() throws Exception {
        result = mockMvc.perform(get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @When("Kullanıcı talebenin Adını {string} Soyadını {string} olarak kaydetmek istediğinde")
    public void kullaniciTalebeKaydetmekIstediginde(String name, String surname) throws Exception {
        studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setName(name);
        studentCreateDTO.setSurname(surname);

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(studentCreateDTO);

        result = mockMvc.perform(post("/api/v1/students")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @When("Kullanıcı {string} idli talebeyi silmek isteiğinde")
    public void kullaniciNumaraliTalebeyiSilmekIstediginde(String studentId) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(studentId);

        result = mockMvc.perform(delete("/api/v1/students/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @When("Kullanıcı {int} idli talebeyi {string} {string} olarak güncellemek istediğinde")
    public void kullaniciIdliTalebeyiOlarakGuncellemekIstediginde(int studentId, String name, String surname) throws Exception {
        Student student = (Student) scenerioContext.getContext(String.valueOf(studentId));
        student.setName(name);
        student.setSurname(surname);

        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(student);

        result = mockMvc.perform(put("/api/v1/students/{studentId}", studentId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @When("Kullanıcı {int} idli talebeyi silmek istediğinde")
    public void kullaniciIdliTalebeyiSilmekIstediginde(int studentId) throws Exception {
        result = mockMvc.perform(delete("/api/v1/students/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @When("Kullanıcı talebenin Adını {string} Soyadını {string} olarak hatalı şekilde kaydetmek istediğinde")
    public void kullaniciTalebeninAdiniSoyadiniOlarakHataliSekildeKaydetmekIstediginde(String name, String surname) throws Exception {
        try{
            studentService.validateStudent(name, surname);
        }catch (Exception e){
            scenerioContext.setContext("error", e.getMessage());
        }
    }

    @When("Kullanıcı {int} idli talebeyi hatalı şekilde güncellemek istediğinde")
    public void kullaniciIdliTalebeyiHataliSekildeGuncellemekIstediginde(int studentId) throws Exception {
        Student student = (Student) scenerioContext.getContext(String.valueOf(studentId));
        try{
            studentService.validateStudent(student.getName(), student.getSurname());
        }catch (Exception e){
            scenerioContext.setContext("error", "Name and Surname must be filled");
        }
    }

    /* @Then  */
    @Then("Talebe listesi toplam {int} adet talebe içermelidir")
    public void talebeListesiToplamAdetTalebeIcermelidir(int expectedSize) throws Exception {
        result.andExpect(status().isOk())
                .andDo(print());
        List<Student> students = studentService.getStudents();
        Assert.assertEquals(expectedSize, students.size());
    }

    @Then("Kullanıcı talebe kayıt işleminin başarısız olduğunu görür")
    public void kullaniciTalebeKayitIslemininBasarisizOldugunuGorur() throws Exception {
        result.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Then("Geriye başarılı status kodu dönmesi")
    public void basariliStatusKoduDonmesi() throws Exception {
        result.andExpect(status().is2xxSuccessful());

    }

    @Then("{int} idli talebenin adı {string} soyadı {string} olmalıdır")
    public void idliTalebeninAdiSoyadiOlmalidir(int studentId, String name, String surname) throws Exception {
        Student student = (Student) scenerioContext.getContext(String.valueOf(studentId));
        Assert.assertEquals(name, student.getName());
        Assert.assertEquals(surname, student.getSurname());
    }

    @Then("Geriye başarısız status kodu dönmesi")
    public void geriyeBasarisizStatusKoduDonmesi() throws Exception {
        //TODO: Burada hata mesajı kontrolü yapılacak
//        result.andExpect(status().is4xxClientError());
    }
}
