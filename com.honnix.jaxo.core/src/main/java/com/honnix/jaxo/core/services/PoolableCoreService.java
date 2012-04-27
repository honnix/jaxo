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
import javax.xml.transform.Transformer;
import javax.xml.xpath.XPath;

/**
 * JAXO core services. Most JAXP specified stuffs can be retrieved via this services.
 * <p/>
 * This services guarantees that correct class loader will be used to load factories,
 * and these factories will not be cached.
 * <p/>
 * The other stuffs like <code>DocumentBuilder</code>, <code>XPath</code> will be cached.
 * <p/>
 * A generic object pool is used for caching, and following properties are supported:
 * <ul>
 * <li>maxIdle</li>
 * <li>minIdle</li>
 * <li>maxActive</li>
 * <li>maxWait</li>
 * <li>timeBetweenEvictionRunsMillis</li>
 * <li>minEvictableIdleTimeMillis</li>
 * </ul>
 * Please refer to Apache Commons Pool documentation to get what these properties mean.
 * Since different pools is used for different type, for example {@link DocumentBuilder},
 * these pools can be configured separately. And the rule is prefixing class name, for example:
 * <ul>
 * <li>javax.xml.parsers.DocumentBuilder.maxIdle=dd</li>
 * <li>javax.xml.parsers.DocumentBuilder.minIdle=dd</li>
 * <li>javax.xml.parsers.DocumentBuilder.maxActive=xx</li>
 * <li>javax.xml.parsers.DocumentBuilder.timeBetweenEvictionRunsMillis=ddd</li>
 * <li>javax.xml.parsers.DocumentBuilder.minEvictableIdleTimeMillis=ddd</li>
 * </ul>
 *
 * @author honnix
 */
public interface PoolableCoreService extends CoreService {
    /**
     * Return {@link DocumentBuilder} to object pool.
     *
     * @param documentBuilder the object to return back to pool
     */
    void returnDocumentBuilder(DocumentBuilder documentBuilder);

    /**
     * Return {@link XPath} to object pool.
     *
     * @param xpath the object to return back to pool
     */
    void returnXPath(XPath xpath);

    /**
     * Return {@link Transformer} to object pool
     *
     * @param transformer the object to return back to pool
     */
    void returnTransformer(Transformer transformer);

    /**
     * Close object pool to release resources.
     */
    void close();
}
