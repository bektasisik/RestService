package TYS.RestService.BDD.IntegrationTest.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "TYS.RestService.BDD.IntegrationTest.StepDefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumberIntegrationTest {
}