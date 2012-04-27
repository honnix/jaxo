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

import org.apache.commons.pool.BasePoolableObjectFactory;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

/**
 * Object factory to create {@link javax.xml.transform.Transformer}.
 *
 * @author honnix
 */
public class ValidatorObjectFactory extends BasePoolableObjectFactory<Validator> {
    private Schema schema;

    public ValidatorObjectFactory(Schema schema) {
        super();

        this.schema = schema;
    }

    @Override
    public Validator makeObject() throws Exception {
        return schema.newValidator();
    }

    @Override
    public void passivateObject(Validator validator) throws Exception {
        validator.reset();
    }
}
