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
import com.honnix.jaxo.core.exception.JAXOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link CoreService}.
 *
 * @author honnix
 */
public class CoreServiceImpl extends AbstractCoreServiceImpl {
    private final Map<String, ThreadLocal> threadLocalMap;

    public CoreServiceImpl() {
        super();

        threadLocalMap = new HashMap<String, ThreadLocal>();
        threadLocalMap.put(DocumentBuilder.class.getName(), new ThreadLocal<DocumentBuilder>());
        threadLocalMap.put(XPath.class.getName(), new ThreadLocal<XPath>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public DocumentBuilder getDocumentBuilder() {
        DocumentBuilder documentBuilder = (DocumentBuilder) threadLocalMap.get(DocumentBuilder.class.getName()).get();

        if (documentBuilder == null) {
            try {
                documentBuilder = createDocumentBuilderFactory().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new JAXOException(e.getMessage(), e);
            }

            threadLocalMap.get(DocumentBuilder.class.getName()).set(documentBuilder);
        }

        return documentBuilder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public XPath getXPath() {
        XPath xpath = (XPath) threadLocalMap.get(XPath.class.getName()).get();

        if (xpath == null) {
            xpath = createXPathFactory().newXPath();
            threadLocalMap.get(XPath.class.getName()).set(xpath);
        }

        return xpath;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Transformer getTransformer() {
        Transformer transformer = (Transformer) threadLocalMap.get(Transformer.class.getName()).get();

        if (transformer == null) {
            try {
                transformer = TransformerFactory.newInstance().newTransformer();
            } catch (TransformerConfigurationException e) {
                throw new JAXOException(e.getMessage(), e);
            }

            /*
            * NEVER set max line length since we don't want line break everywhere
            */

            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty("{http://xml.apache.org/xalan}line-separator",
                    System.getProperty("line.separator"));

            threadLocalMap.get(Transformer.class.getName()).set(transformer);

        }

        return transformer;
    }
}
