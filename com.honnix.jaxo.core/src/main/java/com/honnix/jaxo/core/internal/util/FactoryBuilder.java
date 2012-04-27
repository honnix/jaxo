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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

/**
 * @author honnix
 */
public final class FactoryBuilder {
    private static final String DOCUMENT_BUILDER_FACTORY_CLASS_NAME = "org.apache.xerces.jaxp" +
            ".DocumentBuilderFactoryImpl";

    private static final String XPATH_FACTORY_CLASS_NAME = "org.apache.xpath.jaxp.XPathFactoryImpl";

    private static final String TRANSFORMER_FACTORY_CLASS_NAME = "org.apache.xalan.xsltc.trax.TransformerFactoryImpl";

    private static final String SCHEMA_FACTORY_CLASS_NAME = "org.apache.xerces.jaxp.validation.XMLSchemaFactory";

    public static DocumentBuilderFactory buildDocumentBuilderFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(DOCUMENT_BUILDER_FACTORY_CLASS_NAME,
                FactoryBuilder.class.getClassLoader());
        factory.setNamespaceAware(true);
        return factory;
    }

    public static XPathFactory buildXPathFactory() {
        try {
            return XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI, XPATH_FACTORY_CLASS_NAME,
                    FactoryBuilder.class.getClassLoader());
        } catch (XPathFactoryConfigurationException e) {
            throw new JAXOException("Failed to create XPathFactory", e);
        }
    }

    public static TransformerFactory buildTransformerFactory() {
        return TransformerFactory.newInstance(TRANSFORMER_FACTORY_CLASS_NAME, FactoryBuilder.class.getClassLoader());
    }

    public static SchemaFactory buildSchemaFactory() {
        return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI, SCHEMA_FACTORY_CLASS_NAME,
                FactoryBuilder.class.getClassLoader());
    }

    private FactoryBuilder() {
        super();
    }
}
