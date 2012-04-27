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
package com.honnix.jaxo.core.services;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * JAXO core services. Most JAXP specified stuffs can be retrieved via this services.
 * <p/>
 * This services guarantees that correct class loader will be used to load factories,
 * and these factories will not be cached.
 * <p/>
 * The other stuffs like <code>DocumentBuilder</code>, <code>XPath</code> will be cached.
 * <p/>
 * ThreadLocal is used for caching.
 *
 * @author honnix
 */
public interface CoreService {
    DocumentBuilderFactory createDocumentBuilderFactory();

    DocumentBuilder getDocumentBuilder();

    XPathFactory createXPathFactory();

    XPath getXPath();

    TransformerFactory createTransformerFactory();

    Transformer getTransformer();

    SchemaFactory createSchemaFactory();
}
