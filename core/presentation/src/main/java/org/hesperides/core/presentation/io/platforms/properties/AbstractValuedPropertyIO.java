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
package org.hesperides.core.presentation.io.platforms.properties;

import com.google.gson.*;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@NonFinal
public abstract class AbstractValuedPropertyIO {

    String name;

    public static <T extends AbstractValuedPropertyIO> List<T> getPropertyWithType(List<AbstractValuedPropertyIO> properties, Class<T> clazz) {
        return Optional.ofNullable(properties)
                .orElse(Collections.emptyList())
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public static class Adapter implements JsonDeserializer<AbstractValuedPropertyIO>, JsonSerializer<AbstractValuedPropertyIO> {

        @Override
        public AbstractValuedPropertyIO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement items = jsonObject.get("iterable_valorisation_items");
            Class<? extends AbstractValuedPropertyIO> subClass = items != null ? IterableValuedPropertyIO.class : ValuedPropertyIO.class;
            return context.deserialize(json, subClass);
        }

        @Override
        public JsonElement serialize(AbstractValuedPropertyIO src, Type typeOfSrc, JsonSerializationContext context) {
            Class<? extends AbstractValuedPropertyIO> subClass = src instanceof ValuedPropertyIO ? ValuedPropertyIO.class : IterableValuedPropertyIO.class;
            return context.serialize(src, subClass);
        }
    }
}
