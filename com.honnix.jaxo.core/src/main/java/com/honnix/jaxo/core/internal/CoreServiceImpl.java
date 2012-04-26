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
package com.honnix.jaxo.core.internal;

import com.honnix.jaxo.core.CoreService;
import com.honnix.jaxo.core.exception.JAXOException;
import com.honnix.jaxo.core.internal.util.FactoryBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link CoreService}.
 *
 * @author honnix
 */
class CoreServiceImpl extends AbstractCoreServiceImpl {
    private Map<String, ThreadLocal> threadLocalMap;

    public CoreServiceImpl() {
        super();

        threadLocalMap = new HashMap<String, ThreadLocal>();
        threadLocalMap.put(DocumentBuilder.class.getName(), new ThreadLocal<DocumentBuilder>());
        threadLocalMap.put(XPath.class.getName(), new ThreadLocal<XPath>());
    }

    @Override
    public DocumentBuilder getDocumentBuilder() {
        DocumentBuilder documentBuilder = (DocumentBuilder) threadLocalMap.get(DocumentBuilder.class.getName()).get();

        if (documentBuilder == null) {
            try {
                documentBuilder = createDocumentBuilderFactory().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new JAXOException("Unable to get DocumentBuilder.", e);
            }

            threadLocalMap.get(DocumentBuilder.class.getName()).set(documentBuilder);
        }

        return documentBuilder;
    }

    @Override
    public XPath getXPath() {
        XPath xpath = (XPath) threadLocalMap.get(XPath.class.getName()).get();

        if (xpath == null) {
            xpath = createXPathFactory().newXPath();
            threadLocalMap.get(XPath.class.getName()).set(xpath);
        }

        return xpath;
    }

    @Override
    public Transformer getTransformer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Schema getSchema() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}