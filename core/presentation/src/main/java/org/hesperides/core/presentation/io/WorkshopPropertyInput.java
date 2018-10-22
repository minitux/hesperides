package org.hesperides.core.presentation.io;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.hesperides.core.domain.workshopproperties.entities.WorkshopProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor
public class WorkshopPropertyInput {

    @NotNull
    @NotEmpty
    String key;
    @NotNull
    @NotEmpty
    String value;

    public WorkshopProperty toDomainInstance() {
        return new WorkshopProperty(key, value, null);
    }
}
