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

package com.honnix.jaxo.core.internal.factory;

import com.honnix.jaxo.core.internal.util.FactoryBuilder;
import org.apache.commons.pool.BasePoolableObjectFactory;

import javax.xml.parsers.DocumentBuilder;

/**
 * Object factory to create {@link DocumentBuilder}.
 *
 * @author honnix
 */
public class DocumentBuilderObjectFactory extends BasePoolableObjectFactory<DocumentBuilder> {
    public DocumentBuilderObjectFactory() {
        super();
    }

    @Override
    public DocumentBuilder makeObject() throws Exception {
        return FactoryBuilder.buildDocumentBuilderFactory().newDocumentBuilder();
    }

    @Override
    public void passivateObject(DocumentBuilder builder) throws Exception {
        builder.reset();
    }
}
