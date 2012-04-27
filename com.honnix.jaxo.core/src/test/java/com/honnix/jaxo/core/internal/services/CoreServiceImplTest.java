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
package com.honnix.jaxo.core.internal.services;

import com.honnix.jaxo.core.services.CoreService;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.xpath.XPath;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Test class for {@link CoreServiceImpl}.
 *
 * @author honnix
 */
public class CoreServiceImplTest {
    private CoreService coreService;

    @Before
    public void before() {
        coreService = new CoreServiceImpl();
    }

    @Test
    public void testGetDocumentBuilder() {
        DocumentBuilder builder = coreService.getDocumentBuilder();
        assertNotNull("unable to get document builder", builder);

        DocumentBuilder cachedBuilder = coreService.getDocumentBuilder();
        assertSame("it should have been cached", builder, cachedBuilder);
    }

    @Test
    public void testGetTransformer() {
        Transformer transformer = coreService.getTransformer();
        assertNotNull("unable to get document builder", transformer);

        Transformer cachedTransformer = coreService.getTransformer();
        assertSame("it should have been cached", transformer, cachedTransformer);
    }

    @Test
    public void testGetXPath() {
        XPath xpath = coreService.getXPath();
        assertNotNull("unable to get xpath", xpath);

        XPath cachedXPath = coreService.getXPath();
        assertSame("it should have been cached", xpath, cachedXPath);
    }
}
