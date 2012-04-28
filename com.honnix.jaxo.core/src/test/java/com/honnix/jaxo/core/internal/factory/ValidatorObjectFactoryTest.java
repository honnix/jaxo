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

import org.junit.Before;
import org.junit.Test;

import javax.xml.xpath.XPath;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link com.honnix.jaxo.core.internal.factory.XPathObjectFactory}.
 *
 * @author honnix
 */
public class ValidatorObjectFactoryTest {
    private XPathObjectFactory factory;

    public ValidatorObjectFactoryTest() {
        super();
    }

    @Before
    public void before() {
        factory = new XPathObjectFactory();
    }

    @Test
    public void testMakeObject() throws Exception {
        XPath xpath = factory.makeObject();
        assertNotNull("sorry can not make more object", xpath);
    }

    @Test
    public void testPassivateObject() throws Exception {
        XPath mockXPath = mock(XPath.class);
        factory.passivateObject(mockXPath);
        verify(mockXPath).reset();
    }
}
