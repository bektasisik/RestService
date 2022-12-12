package TYS.RestService.BDD.rest.StudentControllerBDD;

import TYS.RestService.domain.Student;
import TYS.RestService.service.StudentService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;

public class StudentControllerStepDefinations extends CucumberIntegrationTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String STUDENTS_URI = "/api/v1/students";

    private static StudentService studentService;
    private static Response response;
    List<Student> studentList = studentService.getStudents();


    @Given("^Talebe Listesi mevcut$")
    public List<Student> existStudentList() {
        return studentList;
    }

    @And("^Servis Ayakta$")
    public void runService(){
    }

    @When("^Kullanıcı istek yaptığında$")
    public void takeRequest() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get(STUDENTS_URI);
    }

    @Then("^Geriye (\\d+) http durum kodu döner$")
    public void isEqualStatusCode(int arg0) {
        Assert.assertEquals(arg0, response.getStatusCode());
    }

    @And("^Talebe Listesini Döner$")
    public List<Student> returnList() {
        return studentList;
    }
}
