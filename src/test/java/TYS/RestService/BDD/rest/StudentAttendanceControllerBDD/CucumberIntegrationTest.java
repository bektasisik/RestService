package TYS.RestService.BDD.rest.StudentAttendanceControllerBDD;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/rest/StudentAttendanceController")
public class CucumberIntegrationTest {
}