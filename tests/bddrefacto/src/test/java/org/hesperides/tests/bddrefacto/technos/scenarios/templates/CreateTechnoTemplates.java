/*
 *
 * This file is part of the Hesperides distribution.
 * (https://github.com/voyages-sncf-technologies/hesperides)
 * Copyright (c) 2016 VSCT.
 *
 * Hesperides is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, version 3.
 *
 * Hesperides is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.hesperides.tests.bddrefacto.technos.scenarios.templates;

import cucumber.api.java8.En;
import org.apache.commons.lang3.StringUtils;
import org.hesperides.core.presentation.io.templatecontainers.TemplateIO;
import org.hesperides.tests.bddrefacto.technos.TechnoBuilder;
import org.hesperides.tests.bddrefacto.technos.TechnoClient;
import org.hesperides.tests.bddrefacto.templatecontainers.TemplateBuilder;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateTechnoTemplates implements En {

    @Autowired
    private TemplateBuilder templateBuilder;
    @Autowired
    private TechnoBuilder technoBuilder;
    @Autowired
    private TechnoClient technoClient;

    private ResponseEntity responseEntity;

    public CreateTechnoTemplates() {

        Given("^a template to create( with the same name as the existing one)?$", (final String withTheSameName) -> {
            if (StringUtils.isEmpty(withTheSameName)) {
                templateBuilder.withName("new-template");
            }
        });

        When("^I add this template to the techno$", () -> {
            responseEntity = technoClient.addTemplate(templateBuilder.build(), technoBuilder.build(), TemplateIO.class);
        });

        When("^I try to add this template to the techno$", () -> {
            responseEntity = technoClient.addTemplate(templateBuilder.build(), technoBuilder.build(), String.class);
        });

        Then("^the template is successfully added to the techno$", () -> {
            Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        });

        Then("^the template is rejected with a conflict error$", () -> {
            Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        });
    }
}