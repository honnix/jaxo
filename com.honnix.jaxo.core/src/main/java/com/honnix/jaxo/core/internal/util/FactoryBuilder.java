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
package com.honnix.jaxo.core.internal.util;

import com.honnix.jaxo.core.exception.JAXOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import java.util.Map;

/**
 * Helper class to build those factories.
 *
 * @author honnix
 */
public final class FactoryBuilder {
    private static final String DOCUMENT_BUILDER_FACTORY_CLASS_NAME = "org.apache.xerces.jaxp" +
            ".DocumentBuilderFactoryImpl";

    private static final String XPATH_FACTORY_CLASS_NAME = "org.apache.xpath.jaxp.XPathFactoryImpl";

    private static final String TRANSFORMER_FACTORY_CLASS_NAME = "org.apache.xalan.xsltc.trax.TransformerFactoryImpl";

    private static final String SCHEMA_FACTORY_CLASS_NAME = "org.apache.xerces.jaxp.validation.XMLSchemaFactory";

    private static final String JAXB_CONTEXT = "ycom.sun.xml.bind.v2.ContextFactor";

    /**
     * Build {@link DocumentBuilderFactory}. The underlying implementation used is <code>org.apache.xerces.jaxp
     * .DocumentBuilderFactoryImpl</code>.
     * <p/>
     * By default the {@link DocumentBuilderFactory} is configured as namespace aware.
     *
     * @return the new created {@link DocumentBuilderFactory}
     */
    public static DocumentBuilderFactory buildDocumentBuilderFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(DOCUMENT_BUILDER_FACTORY_CLASS_NAME,
                FactoryBuilder.class.getClassLoader());
        factory.setNamespaceAware(true);
        return factory;
    }

    /**
     * Build {@link XPathFactory}. The underlying implementation used is <code>org.apache.xerces.jaxp
     * .XPathFactoryImpl</code>.
     *
     * @return the new created {@link XPathFactory}
     */
    public static XPathFactory buildXPathFactory() {
        try {
            return XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI, XPATH_FACTORY_CLASS_NAME,
                    FactoryBuilder.class.getClassLoader());
        } catch (XPathFactoryConfigurationException e) {
            throw new JAXOException("Failed to create XPathFactory", e);
        }
    }

    /**
     * Build {@link TransformerFactory}. The underlying implementation used is <code>org.apache.xalan.xsltc.trax
     * .TransformerFactoryImpl</code>.
     *
     * @return the new created {@link TransformerFactory}
     */
    public static TransformerFactory buildTransformerFactory() {
        return TransformerFactory.newInstance(TRANSFORMER_FACTORY_CLASS_NAME, FactoryBuilder.class.getClassLoader());
    }

    /**
     * Build {@link SchemaFactory}. The underlying implementation used is <code>org.apache.xerces.jaxp.validation
     * .XMLSchemaFactory</code>.
     *
     * @return the new created {@link SchemaFactory}
     */
    public static SchemaFactory buildSchemaFactory() {
        return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI, SCHEMA_FACTORY_CLASS_NAME,
                FactoryBuilder.class.getClassLoader());
    }

    /**
     * Build {@link JAXBContext}. The factory used underlying is <code>ycom.sun.xml.bind.v2.ContextFactor</code>.
     *
     * @param contextPath a list of colon (':', \u005Cu003A) separated java package names that contain
     *                    schema-derived classes and/or fully qualified JAXB-annotated classes.
     * @param classLoader this class loader will be used to locate the implementation classes.
     * @param properties  provider-specific properties
     * @return the new created {@link JAXBContext}
     */
    public static JAXBContext buildJAXBContext(String contextPath, ClassLoader classLoader, Map<String, ?> properties) {
        System.setProperty(JAXBContext.JAXB_CONTEXT_FACTORY, JAXB_CONTEXT);
        try {
            return JAXBContext.newInstance(contextPath, classLoader, properties);
        } catch (JAXBException e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }

    private FactoryBuilder() {
        super();
    }
}
