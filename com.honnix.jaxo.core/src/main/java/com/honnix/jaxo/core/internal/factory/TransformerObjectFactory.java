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

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.xpath.XPath;

/**
 * @author honnix
 */
public class TransformerObjectFactory extends BasePoolableObjectFactory<Transformer> {
    public TransformerObjectFactory() {
        super();
    }

    @Override
    public Transformer makeObject() throws Exception {
        Transformer transformer = FactoryBuilder.buildTransformerFactory().newTransformer();
        setOutputProperties(transformer);
        return transformer;
    }

    @Override
    public void passivateObject(Transformer transformer) throws Exception {
        transformer.reset();
        setOutputProperties(transformer);
    }

    private void setOutputProperties(Transformer transformer) {
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
