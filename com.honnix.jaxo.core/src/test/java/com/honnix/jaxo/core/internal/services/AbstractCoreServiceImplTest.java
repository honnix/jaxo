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

import com.honnix.jaxo.core.exception.JAXOException;
import com.honnix.jaxo.core.internal.jaxb.ObjectFactory;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for {@link AbstractCoreServiceImpl}.
 *
 * @author honnix
 */
public class AbstractCoreServiceImplTest {
    private AbstractCoreServiceImpl mockCoreService;

    public AbstractCoreServiceImplTest() {
        super();
    }

    @Before
    public void before() {
        mockCoreService = new MockCoreServiceImpl();
    }

    @Test
    public void testCreateDocumentBuilderFactory() {
        assertNotNull("unable to create factory", mockCoreService.createDocumentBuilderFactory());
    }

    @Test
    public void testCreateSchemaFactory() {
        assertNotNull("unable to create factory", mockCoreService.createSchemaFactory());
    }

    @Test
    public void testCreateTransformerFactory() {
        assertNotNull("unable to create factory", mockCoreService.createTransformerFactory());
    }

    @Test
    public void testCreateXPathFactory() {
        assertNotNull("unable to create factory", mockCoreService.createXPathFactory());
    }

    @Test
    public void testSetOutputProperties() {
        Properties properties = mockCoreService.getTransformer().getOutputProperties();
        assertEquals("wrong method", "xml", properties.getProperty(OutputKeys.METHOD));
        assertEquals("it should indent", "yes", properties.getProperty(OutputKeys.INDENT));
        assertEquals("wrong indentation amount", "2", properties.getProperty("{http://xml.apache" +
                ".org/xalan}indent-amount"));
        assertEquals("wrong line separator", System.getProperty("line.separator"),
                properties.getProperty("{http://xml.apache.org/xalan}line-separator"));
    }

    @Test
    public void testCreateJAXBContext() {
        mockCoreService.createJAXBContext(ObjectFactory.class.getPackage().getName(), getClass().getClassLoader());
    }

    @Test
    public void testCreateJAXBContextWithProperties() {
        mockCoreService.createJAXBContext(ObjectFactory.class.getPackage().getName(), getClass().getClassLoader(),
                new HashMap<String, Object>());
    }

    private static class MockCoreServiceImpl extends AbstractCoreServiceImpl {
        private MockCoreServiceImpl() {
            super();
        }

        @Override
        public DocumentBuilder getDocumentBuilder() {
            return null;
        }

        @Override
        public XPath getXPath() {
            return null;
        }

        @Override
        public Transformer getTransformer() {
            Transformer transformer = null;
            try {
                transformer = createTransformerFactory().newTransformer();
            } catch (TransformerConfigurationException e) {
                throw new JAXOException(e.getMessage(), e);
            }
            setOutputProperties(transformer);
            return transformer;
        }

        @Override
        public Validator getValidator(Schema schema) {
            return null;
        }

        @Override
        public void clearValidators(Schema schema) {
        }
    }
}
