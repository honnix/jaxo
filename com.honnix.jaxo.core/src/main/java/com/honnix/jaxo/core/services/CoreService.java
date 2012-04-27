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
 * <p/>
 * Another important thing to note is the real implementation is hard coded not to use JDK embedded one,
 * and this can not be changed.
 *
 * @author honnix
 */
public interface CoreService {
    /**
     * Create a new {@link DocumentBuilderFactory}. This will not be cached anyway.
     * <p/>
     * By default, the {@link DocumentBuilderFactory} is configured as namespace aware.
     *
     * @return the new created {@link DocumentBuilderFactory}
     */
    DocumentBuilderFactory createDocumentBuilderFactory();

    /**
     * Get {@link DocumentBuilder} from {@link ThreadLocal} if there is, otherwise create a new one,
     * set it to {@link ThreadLocal}, and then return back.
     * <p/>
     * The {@link DocumentBuilderFactory} used to create this {@link DocumentBuilder} is configured as namespace aware.
     *
     * @return either one from {@link ThreadLocal} or a new created one
     */
    DocumentBuilder getDocumentBuilder();

    /**
     * Create a new {@link XPathFactory}. This will not be cached anyway.
     *
     * @return the new created {@link XPathFactory}
     */
    XPathFactory createXPathFactory();

    /**
     * Get {@link XPath} from {@link ThreadLocal} if there is, otherwise create a new one,
     * set it to {@link ThreadLocal}, and then return back.
     *
     * @return either one from {@link ThreadLocal} or a new created one
     */
    XPath getXPath();

    /**
     * Create a new {@link TransformerFactory}. This will not be cached anyway.
     *
     * @return the new created {@link TransformerFactory}
     */
    TransformerFactory createTransformerFactory();

    /**
     * Get {@link Transformer} from {@link ThreadLocal} if there is, otherwise create a new one,
     * set it to {@link ThreadLocal}, set output properties and then return back.
     * <p/>
     * {@link Transformer}s get via this API is only used to pretty print XML, not designed to use with XSLT.
     *
     * @return either one from {@link ThreadLocal} or a new created one
     */
    Transformer getTransformer();

    /**
     * Create a new {@link SchemaFactory}. This will not be cached anyway.
     * <p/>
     * Sorry we only support creating XML schema factory, no other.
     *
     * @return the new created {@link SchemaFactory}
     */
    SchemaFactory createSchemaFactory();
}
