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
package org.hesperides.core.domain.workshopproperties.commands;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.hesperides.core.domain.CreateWorkshopPropertyCommand;
import org.hesperides.core.domain.UpdateWorkshopPropertyCommand;
import org.hesperides.core.domain.WorkshopPropertyCreatedEvent;
import org.hesperides.core.domain.WorkshopPropertyUpdatedEvent;
import org.hesperides.core.domain.workshopproperties.entities.WorkshopProperty;

import java.io.Serializable;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.isLive;

@Slf4j
@Aggregate
@NoArgsConstructor
public class WorkshopPropertyAggregate implements Serializable {

    @AggregateIdentifier
    private String key;

    /*** COMMAND HANDLERS ***/

    @CommandHandler
    @SuppressWarnings("unused")
    public WorkshopPropertyAggregate(CreateWorkshopPropertyCommand command) {
        log.debug("Applying create module command...");
        String key = command.getWorkshopProperty().getKey();
        String value = command.getWorkshopProperty().getValue();
        WorkshopProperty workshopProperty = new WorkshopProperty(key, value, key + value);
        apply(new WorkshopPropertyCreatedEvent(workshopProperty, command.getUser()));
    }

    @CommandHandler
    @SuppressWarnings("unused")
    public void onUpdateWorkshopPropertyCommand(UpdateWorkshopPropertyCommand command) {
        log.debug("Applying update WorkshopProperty command...");
        String key = command.getWorkshopProperty().getKey();
        String value = command.getWorkshopProperty().getValue();
        WorkshopProperty workshopProperty = new WorkshopProperty(key, value, key + value);
        apply(new WorkshopPropertyUpdatedEvent(workshopProperty, command.getUser()));
    }

    /*** EVENT HANDLERS ***/

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void onWorkshopPropertyCreatedEvent(WorkshopPropertyCreatedEvent event) {
        this.key = event.getWorkshopProperty().getKey();
        log.debug("module créé. (aggregate is live ? {})", isLive());
    }

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void onWorkshopPropertyUpdatedEvent(WorkshopPropertyUpdatedEvent event) {
        log.debug("module mis à jour. (aggregate is live ? {})", isLive());
    }
}
