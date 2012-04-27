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

import com.honnix.jaxo.core.services.PoolableCoreService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.xpath.XPath;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Test class for {@link PoolableCoreServiceImpl}.
 *
 * @author honnix
 */
public class PoolableCoreServiceImplTest {
    private PoolableCoreService poolableCoreService;

    public PoolableCoreServiceImplTest() {
        super();
    }

    @Before
    public void before() throws ConfigurationException {
        poolableCoreService = new PoolableCoreServiceImpl();
        ((ManagedService) poolableCoreService).updated(null);
    }

    @After
    public void after() {
        poolableCoreService.close();
    }

    @Test
    public void testGetDocumentBuilder() {
        DocumentBuilder builder = poolableCoreService.getDocumentBuilder();
        assertNotNull("unable to get document builder", builder);
        poolableCoreService.returnDocumentBuilder(builder);

        DocumentBuilder cachedBuilder = poolableCoreService.getDocumentBuilder();
        assertNotNull("unable to get document builder", cachedBuilder);
        assertSame("it should have been cached", builder, cachedBuilder);
        poolableCoreService.returnDocumentBuilder(cachedBuilder);
    }

    @Test
    public void testGetDocumentBuilderTwoObjects() {
        DocumentBuilder builder = poolableCoreService.getDocumentBuilder();
        assertNotNull("unable to get document builder", builder);

        DocumentBuilder cachedBuilder = poolableCoreService.getDocumentBuilder();
        assertNotNull("unable to get document builder", cachedBuilder);
        assertNotSame("should be no cached object available", builder, cachedBuilder);

        poolableCoreService.returnDocumentBuilder(builder);
        poolableCoreService.returnDocumentBuilder(cachedBuilder);
    }

    @Test
    public void testGetXPath() {
        XPath xpath = poolableCoreService.getXPath();
        assertNotNull("unable to get xpath", xpath);
        poolableCoreService.returnXPath(xpath);

        XPath cachedXPath = poolableCoreService.getXPath();
        assertNotNull("unable to get xpath", cachedXPath);
        assertSame("it should have been cached", xpath, cachedXPath);
        poolableCoreService.returnXPath(cachedXPath);
    }

    @Test
    public void testGetXPathTwoObjects() {
        XPath xpath = poolableCoreService.getXPath();
        assertNotNull("unable to get xpath", xpath);

        XPath cachedXPath = poolableCoreService.getXPath();
        assertNotNull("unable to get xpath", cachedXPath);
        assertNotSame("should be no cached object available", xpath, cachedXPath);

        poolableCoreService.returnXPath(cachedXPath);
        poolableCoreService.returnXPath(xpath);
    }

    @Test
    public void testGetTransformer() {
        Transformer transformer = poolableCoreService.getTransformer();
        assertNotNull("unable to get transformer", transformer);
        poolableCoreService.returnTransformer(transformer);

        Transformer cachedTransformer = poolableCoreService.getTransformer();
        assertNotNull("unable to get transformer", cachedTransformer);
        assertSame("it should have been cached", transformer, cachedTransformer);
        poolableCoreService.returnTransformer(cachedTransformer);
    }

    @Test
    public void testGetTransformerTwoObjects() {
        Transformer transformer = poolableCoreService.getTransformer();
        assertNotNull("unable to get transformer", transformer);

        Transformer cachedTransformer = poolableCoreService.getTransformer();
        assertNotNull("unable to get transformer", cachedTransformer);
        assertNotSame("should be no cached object available", transformer, cachedTransformer);

        poolableCoreService.returnTransformer(cachedTransformer);
        poolableCoreService.returnTransformer(transformer);
    }

    @Test
    public void testUpdated() throws ConfigurationException {
        Properties properties = new Properties();

        properties.setProperty(DocumentBuilder.class.getName() + ".maxIdle", "4");
        properties.setProperty(DocumentBuilder.class.getName() + ".minIdle", "2");
        properties.setProperty(DocumentBuilder.class.getName() + ".maxActive", "6");
        properties.setProperty(DocumentBuilder.class.getName() + ".maxWait", "100");
        properties.setProperty(DocumentBuilder.class.getName() + ".timeBetweenEvictionRunsMillis", "100");
        properties.setProperty(DocumentBuilder.class.getName() + ".minEvictableIdleTimeMillis", "1000");

        properties.setProperty(XPath.class.getName() + ".maxIdle", "4");
        properties.setProperty(XPath.class.getName() + ".minIdle", "2");
        properties.setProperty(XPath.class.getName() + ".maxActive", "6");
        properties.setProperty(XPath.class.getName() + ".maxWait", "100");
        properties.setProperty(XPath.class.getName() + ".timeBetweenEvictionRunsMillis", "100");
        properties.setProperty(XPath.class.getName() + ".minEvictableIdleTimeMillis", "1000");

        properties.setProperty(Transformer.class.getName() + ".maxIdle", "4");
        properties.setProperty(Transformer.class.getName() + ".minIdle", "2");
        properties.setProperty(Transformer.class.getName() + ".maxActive", "6");
        properties.setProperty(Transformer.class.getName() + ".maxWait", "100");
        properties.setProperty(Transformer.class.getName() + ".timeBetweenEvictionRunsMillis", "100");
        properties.setProperty(Transformer.class.getName() + ".minEvictableIdleTimeMillis", "1000");

        ((ManagedService) poolableCoreService).updated(properties);
    }
}
