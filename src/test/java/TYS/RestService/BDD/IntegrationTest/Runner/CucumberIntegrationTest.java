package TYS.RestService.BDD.IntegrationTest.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features"
        , glue = "src/test/java/TYS.RestService/BDD/IntegrationTest")
public class CucumberIntegrationTest {
}