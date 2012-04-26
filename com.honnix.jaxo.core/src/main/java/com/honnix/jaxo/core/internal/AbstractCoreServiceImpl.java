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
import com.honnix.jaxo.core.internal.util.FactoryBuilder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;

/**
 * @author honnix
 */
abstract class AbstractCoreServiceImpl implements CoreService {
    public AbstractCoreServiceImpl() {
        super();
    }

    @Override
    public DocumentBuilderFactory createDocumentBuilderFactory() {
        return FactoryBuilder.buildDocumentBuilderFactory();
    }

    @Override
    public XPathFactory createXPathFactory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TransformerFactory createTransformerFactory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SchemaFactory createSchemaFactory() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
