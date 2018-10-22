package org.hesperides.core.domain

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.hesperides.core.domain.security.User
import org.hesperides.core.domain.security.UserEvent
import org.hesperides.core.domain.workshopproperties.entities.WorkshopProperty

// Command
data class CreateWorkshopPropertyCommand(val workshopProperty: WorkshopProperty, val user: User)
data class UpdateWorkshopPropertyCommand(@TargetAggregateIdentifier val workshopPropertyKey: String, val workshopProperty: WorkshopProperty, val user: User)

// Event
data class WorkshopPropertyCreatedEvent(val workshopProperty: WorkshopProperty, override val user: User) : UserEvent(user)
data class WorkshopPropertyUpdatedEvent(val workshopProperty: WorkshopProperty, override val user: User) : UserEvent(user)

// Query
data class WorkshopPropertyAlreadyExistsQuery(val workshopPropertyKey: String)
data class GetWorkshopPropertyQuery(val workshopPropertyKey: String)
