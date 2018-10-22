package org.hesperides.core.presentation.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hesperides.core.application.workshopproperties.WorkshopPropertyUseCases;
import org.hesperides.core.domain.workshopproperties.entities.WorkshopProperty;
import org.hesperides.core.domain.workshopproperties.queries.views.WorkshopPropertyView;
import org.hesperides.core.presentation.io.WorkshopPropertyInput;
import org.hesperides.core.presentation.io.WorkshopPropertyOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

import static org.hesperides.core.domain.security.User.fromAuthentication;

@Slf4j
@RequestMapping("/workshop/properties")
@RestController
public class WorkshopPropertiesController extends AbstractController {

    private final WorkshopPropertyUseCases workshopPropertyUseCases;

    @Autowired
    public WorkshopPropertiesController(WorkshopPropertyUseCases workshopPropertyUseCases) {
        this.workshopPropertyUseCases = workshopPropertyUseCases;
    }

    @PostMapping
    public ResponseEntity<WorkshopPropertyOutput> createWorkshopProperty(Authentication authentication,
                                                                         @Valid @RequestBody final WorkshopPropertyInput workshopPropertyInput) {
        log.info("Add a WorkshopProperty {} {}", workshopPropertyInput.getKey(), workshopPropertyInput.getValue());
        final WorkshopProperty workshopProperty = workshopPropertyInput.toDomainInstance();
        final String workshopPropertyKey = workshopPropertyUseCases.createWorkshopProperty(workshopProperty, fromAuthentication(authentication));
        return getWorkshopProperty(workshopPropertyKey);
    }

    @GetMapping("/{key}")
    public ResponseEntity<WorkshopPropertyOutput> getWorkshopProperty(@PathVariable("key") final String workshopPropertyKey) {
        final WorkshopPropertyView workshopPropertyView = workshopPropertyUseCases.getWorkshopProperty(workshopPropertyKey);
        final URI newURI = URI.create("/rest/workshop/properties/" + workshopPropertyView.getKeyValue());
        return ResponseEntity.created(newURI).body(new WorkshopPropertyOutput(workshopPropertyView));
    }

    @PutMapping
    public ResponseEntity<WorkshopPropertyOutput> updateWorkshopProperty(Authentication authentication,
                                                                         @Valid @RequestBody final WorkshopPropertyInput workshopPropertyInput) {
        final WorkshopProperty workshopProperty = workshopPropertyInput.toDomainInstance();
        workshopPropertyUseCases.updateWorkshopProperty(workshopProperty, fromAuthentication(authentication));
        return getWorkshopProperty(workshopPropertyInput.getKey());
    }
}
