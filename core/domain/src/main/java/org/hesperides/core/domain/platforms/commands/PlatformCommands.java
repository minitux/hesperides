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
package org.hesperides.core.domain.platforms.commands;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.hesperides.core.domain.platforms.*;
import org.hesperides.core.domain.platforms.entities.Platform;
import org.hesperides.core.domain.platforms.entities.properties.AbstractValuedProperty;
import org.hesperides.core.domain.platforms.entities.properties.ValuedProperty;
import org.hesperides.core.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlatformCommands {

    private final CommandGateway commandGateway;

    @Autowired
    public PlatformCommands(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public String createPlatform(Platform platform, User user) {
        return commandGateway.sendAndWait(new CreatePlatformCommand(platform, user));
    }

    public String copyPlatform(String existingPlatformId, Platform newPlatform, User user) {
        return commandGateway.sendAndWait(new CopyPlatformCommand(existingPlatformId, newPlatform, user));
    }

    public void updatePlatform(String platformId, Platform platform, boolean copyProperties, User user) {
        commandGateway.sendAndWait(new UpdatePlatformCommand(platformId, platform, copyProperties, user));
    }

    public void deletePlatform(String platformId, User user) {
        commandGateway.sendAndWait(new DeletePlatformCommand(platformId, user));
    }

    public void saveModulePropertiesInPlatform(final String platformId,
                                               final String modulePath,
                                               final Long platformVersionId,
                                               final List<AbstractValuedProperty> valuedProperties,
                                               final User user) {
        commandGateway.sendAndWait(new UpdatePlatformModulePropertiesCommand(platformId, modulePath, platformVersionId, valuedProperties, user));
    }

    public void savePlatformProperties(final String platformId,
                                       final Long platformVersionId,
                                       final List<ValuedProperty> valuedProperties,
                                       final User user) {
        commandGateway.sendAndWait(new UpdatePlatformPropertiesCommand(platformId, platformVersionId, valuedProperties, user));
    }
}
