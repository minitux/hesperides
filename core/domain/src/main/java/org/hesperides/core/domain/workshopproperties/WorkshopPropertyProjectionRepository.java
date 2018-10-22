package org.hesperides.core.domain.workshopproperties;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.hesperides.core.domain.GetWorkshopPropertyQuery;
import org.hesperides.core.domain.WorkshopPropertyAlreadyExistsQuery;
import org.hesperides.core.domain.WorkshopPropertyCreatedEvent;
import org.hesperides.core.domain.WorkshopPropertyUpdatedEvent;
import org.hesperides.core.domain.workshopproperties.queries.views.WorkshopPropertyView;

import java.util.Optional;

public interface WorkshopPropertyProjectionRepository {

    /*** EVENT HANDLERS ***/
    @EventHandler
    public void onWorkshopPropertyCreatedEvent(WorkshopPropertyCreatedEvent event);

    @EventHandler
    public void onWorkshopPropertyUpdatedEvent(WorkshopPropertyUpdatedEvent event);

    /*** QUERY HANDLERS ***/

    @QueryHandler
    public Optional<WorkshopPropertyView> onGetWorkshopPropertyQuery(GetWorkshopPropertyQuery query);

    @QueryHandler
    public Boolean onWorkshopPropertyAlreadyExistsQuery(WorkshopPropertyAlreadyExistsQuery query);

}
