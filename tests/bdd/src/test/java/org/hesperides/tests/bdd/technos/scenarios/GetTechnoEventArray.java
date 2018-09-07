package org.hesperides.tests.bdd.technos.scenarios;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.hesperides.core.presentation.io.events.LegacyEventOutput;
import org.hesperides.tests.bdd.CucumberSpringBean;
import org.hesperides.tests.bdd.technos.contexts.TechnoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class GetTechnoEventArray extends CucumberSpringBean implements En {

    @Autowired
    private TechnoContext technoContext;

    private ResponseEntity<LegacyEventOutput[]> response;
    private ResponseEntity failResponse;

    public GetTechnoEventArray() {
        When("^retrieving the techno's events array$", () -> {
            response = rest.getTestRest().getForEntity("events/"+technoContext.getTechnoLegacyKey(), LegacyEventOutput[].class);
        });
        Then("^the events array of this techno is retrieved$", () -> {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            LegacyEventOutput[] eventOutput = response.getBody();
            assertEquals(1,eventOutput.length);
            assertEquals();
        });
        Then("^the number of events is (\\d+)$", (Integer arg0) -> {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            LegacyEventOutput[] eventOutput = response.getBody();
            assertEquals(arg0.intValue(),eventOutput.length);
        });
    }
}
