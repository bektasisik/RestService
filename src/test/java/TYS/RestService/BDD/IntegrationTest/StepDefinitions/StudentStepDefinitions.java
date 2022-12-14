package TYS.RestService.BDD.IntegrationTest.StepDefinitions;

import TYS.RestService.BDD.IntegrationTest.Runner.CucumberIntegrationTest;
import TYS.RestService.dto.StudentCreateDTO;
import TYS.RestService.rest.StudentController;
import TYS.RestService.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @When("Kullanıcı talebenin Adını {string} Soyadını {string} olarak kaydetmek istediğinde")
    public void kullaniciKoduluTalebeninAdınıSoyadınıOlarakKaydetmekIstediğinde(String arg0, String name, String surname) throws Exception {
        studentCreateDTO = new StudentCreateDTO();
        studentCreateDTO.setName(name);
        studentCreateDTO.setSurname(surname);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        requestBody = objectMapper.writeValueAsString(studentCreateDTO);

        result = mockMvc.perform(post("/api/v1/students")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Then("Geriye başarılı status kodu dönmesi")
    public void geriyeBaşarılıStatusKoduDönmesi() throws Exception {
        result.andExpect(status().is2xxSuccessful());
    }
}
