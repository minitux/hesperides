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
package org.hesperides.application.modules;

import org.hesperides.domain.templatecontainer.entities.Model;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MustacheTest {

    @Test
    public void testExtractKeyValueProperties() {
        List<Model.Property> keyValueProperties = Model.extractProperties("{{ foo}} {{bar }} {{ fub }}");
        assertEquals(3, keyValueProperties.size());
        assertEquals("foo", keyValueProperties.get(0).getName());
        assertEquals("bar", keyValueProperties.get(1).getName());
        assertEquals("fub", keyValueProperties.get(2).getName());
    }

    @Test
    public void testExtractKeyValueProperty() {
        Model.Property completeProperty = Model.Property.extractProperty("foo|@required|@comment \"comment\"|@default 5|@pattern \"pattern\"|@password");
        assertEquals("foo", completeProperty.getName());
        assertEquals(true, completeProperty.isRequired());
        assertEquals("comment", completeProperty.getComment());
        assertEquals("5", completeProperty.getDefaultValue());
        assertEquals("pattern", completeProperty.getPattern());
        assertEquals(true, completeProperty.isPassword());

        Model.Property minimalistProperty = Model.Property.extractProperty("bar");
        assertEquals("bar", minimalistProperty.getName());
        assertEquals(false, minimalistProperty.isRequired());
        assertEquals("", minimalistProperty.getComment());
        assertEquals("", minimalistProperty.getDefaultValue());
        assertEquals("", minimalistProperty.getPattern());
        assertEquals(false, minimalistProperty.isPassword());
    }

    @Test
    public void testExtractVariableOptionValue() {
        assertEquals("something without any quotes", Model.Property.extractPropertyOptionValue("@anyOption something without any quotes"));
        assertEquals("something with quotes", Model.Property.extractPropertyOptionValue(" @anyOption \"something with quotes\" "));
        assertEquals("12", Model.Property.extractPropertyOptionValue("@anyOption 12"));
        assertEquals("something else", Model.Property.extractPropertyOptionValue("   something that should not be there   @anyOption   something else      "));
    }

    @Test
    public void testRemoveSurroundingQuotesIfPresent() {
        assertEquals("Surrounded by quotes", Model.Property.removeSurroundingQuotesIfPresent("\"Surrounded by quotes\""));
        assertEquals("Not surrounded by quotes", Model.Property.removeSurroundingQuotesIfPresent("Not surrounded by quotes"));
        assertEquals("Contains \"quotes\"", Model.Property.removeSurroundingQuotesIfPresent("Contains \"quotes\""));
        assertEquals("\"Only starts with quotes", Model.Property.removeSurroundingQuotesIfPresent("\"Only starts with quotes"));
        assertEquals("Only ends with quotes\"", Model.Property.removeSurroundingQuotesIfPresent("Only ends with quotes\""));
    }
}
