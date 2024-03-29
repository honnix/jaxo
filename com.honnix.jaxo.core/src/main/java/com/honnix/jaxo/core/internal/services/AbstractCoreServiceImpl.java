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

import com.honnix.jaxo.core.internal.util.FactoryBuilder;
import com.honnix.jaxo.core.services.CoreService;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements create factories methods.
 *
 * @author honnix
 */
abstract class AbstractCoreServiceImpl implements CoreService {
    AbstractCoreServiceImpl() {
        super();
    }

    @Override
    public DocumentBuilderFactory createDocumentBuilderFactory() {
        return FactoryBuilder.buildDocumentBuilderFactory();
    }

    @Override
    public SAXParserFactory createSAXParserFactory() {
        return FactoryBuilder.buildSAXParserFactory();
    }

    @Override
    public XPathFactory createXPathFactory() {
        return FactoryBuilder.buildXPathFactory();
    }

    @Override
    public TransformerFactory createTransformerFactory() {
        return FactoryBuilder.buildTransformerFactory();
    }

    @Override
    public SchemaFactory createSchemaFactory() {
        return FactoryBuilder.buildSchemaFactory();
    }

    @Override
    public JAXBContext createJAXBContext(String contextPath, ClassLoader classLoader) {
        return FactoryBuilder.buildJAXBContext(contextPath, classLoader, new HashMap<String, Object>());
    }

    @Override
    public JAXBContext createJAXBContext(String contextPath, ClassLoader classLoader, Map<String, ?> properties) {
        return FactoryBuilder.buildJAXBContext(contextPath, classLoader, properties);
    }

    protected void setOutputProperties(Transformer transformer) {
        /*
        * NEVER set max line length since we don't want line break everywhere
        */

        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}line-separator",
                System.getProperty("line.separator"));
    }
}
