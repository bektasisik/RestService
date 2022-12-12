package TYS.RestService.BDD.rest.AttendanceControllerBDD;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/rest/AttendanceController")
public class CucumberIntegrationTest {
}