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
package com.honnix.jaxo.core.internal.pool;

import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.Transformer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link TransformerObjectFactory}.
 *
 * @author honnix
 */
public class TransformerObjectFactoryTest {
    private TransformerObjectFactory factory;

    public TransformerObjectFactoryTest() {
        super();
    }

    @Before
    public void before() {
        factory = new TransformerObjectFactory();
    }

    @Test
    public void testMakeObject() throws Exception {
        Transformer transformer = factory.makeObject();
        assertNotNull("sorry can not make more object", transformer);
    }

    @Test
    public void testPassivateObject() throws Exception {
        Transformer mockTransformer = mock(Transformer.class);
        factory.passivateObject(mockTransformer);
        verify(mockTransformer).reset();
    }
}
