/*
 * Copyright 2012 Honnix Liang
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.honnix.jaxo.core.internal.factory;

import com.honnix.jaxo.core.internal.util.FactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link ValidatorObjectFactory}.
 *
 * @author honnix
 */
public class ValidatorObjectFactoryTest {
    private ValidatorObjectFactory factory;

    public ValidatorObjectFactoryTest() {
        super();
    }

    @Before
    public void before() throws SAXException {
        Schema schema = FactoryBuilder.buildSchemaFactory().newSchema(getClass().getResource("/example.xsd"));
        factory = new ValidatorObjectFactory(schema);
    }

    @Test
    public void testMakeObject() throws Exception {
        Validator validator = factory.makeObject();
        assertNotNull("sorry can not make more object", validator);
    }

    @Test
    public void testPassivateObject() throws Exception {
        Validator mockValidator = mock(Validator.class);
        factory.passivateObject(mockValidator);
        verify(mockValidator).reset();
    }
}
