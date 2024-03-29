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

import com.honnix.jaxo.core.exception.JAXOException;
import com.honnix.jaxo.core.internal.pool.DocumentBuilderObjectFactory;
import com.honnix.jaxo.core.internal.pool.SAXParserObjectFactory;
import com.honnix.jaxo.core.internal.pool.TransformerObjectFactory;
import com.honnix.jaxo.core.internal.pool.ValidatorObjectFactory;
import com.honnix.jaxo.core.internal.pool.XPathObjectFactory;
import com.honnix.jaxo.core.services.PoolableCoreService;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.Transformer;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Implementation of {@link PoolableCoreService}. Also this class implements {@link ManagedService} by which it can
 * receive configuration update.
 * <p/>
 * Whenever there is a configuration update, existing object pools will be closed,
 * and a new ones will be created according to configuration. If there is no configuration,
 * default properties of the object pool will be used.
 *
 * @author honnix
 */
public class PoolableCoreServiceImpl extends AbstractCoreServiceImpl implements PoolableCoreService, ManagedService {
    private static final String MAX_IDLE = ".maxIdle";

    private static final String MIN_IDLE = ".minIdle";

    private static final String MAX_ACTIVE = ".maxActive";

    private static final String MAX_WAIT = ".maxWait";

    private static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = ".timeBetweenEvictionRunsMillis";

    private static final String MIN_EVICTABLE_IDLE_TIME_MILLIS = ".minEvictableIdleTimeMillis";

    private static final String[] CONFIG_ITEMS = {DocumentBuilder.class.getName(), SAXParser.class.getName(),
            XPath.class.getName(), Transformer.class.getName()};

    private final Map<String, ObjectPool> objectPoolMap;

    private final ConcurrentMap<Schema, ObjectPool<Validator>> validatorObjectPoolMap;

    private GenericObjectPool.Config validatorConfig;

    public PoolableCoreServiceImpl() {
        super();

        objectPoolMap = new HashMap<String, ObjectPool>();
        validatorObjectPoolMap = new ConcurrentHashMap<Schema, ObjectPool<Validator>>();
    }

    @Override
    public void close() {
        for (ObjectPool objectPool : objectPoolMap.values()) {
            try {
                objectPool.close();
            } catch (Exception ignored) {
            }
        }
        objectPoolMap.clear();

        for (ObjectPool objectPool : validatorObjectPoolMap.values()) {
            try {
                objectPool.close();
            } catch (Exception ignored) {
            }
        }
        validatorObjectPoolMap.clear();
    }

    @Override
    public DocumentBuilder getDocumentBuilder() {
        try {
            return (DocumentBuilder) objectPoolMap.get(DocumentBuilder.class.getName()).borrowObject();
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }

    @Override
    public SAXParser getSAXParser() {
        try {
            return (SAXParser) objectPoolMap.get(SAXParser.class.getName()).borrowObject();
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }

    @Override
    public XPath getXPath() {
        try {
            return (XPath) objectPoolMap.get(XPath.class.getName()).borrowObject();
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }

    @Override
    public Transformer getTransformer() {
        Transformer transformer;
        try {
            transformer = (Transformer) objectPoolMap.get(Transformer.class.getName()).borrowObject();
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
        setOutputProperties(transformer);
        return transformer;
    }

    @Override
    public Validator getValidator(Schema schema) {
        /*
         * this looks stupid but to avoid lock, hope creating a GenericObjectPool does not cost too much
         * java suck with no lazy evaluation support
         */
        validatorObjectPoolMap.putIfAbsent(schema,
                new GenericObjectPool<Validator>(new ValidatorObjectFactory(schema), validatorConfig));
        ObjectPool<Validator> validatorObjectPool = validatorObjectPoolMap.get(schema);

        try {
            return validatorObjectPool.borrowObject();
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }

    @Override
    public void clearValidators(Schema schema) {
        ObjectPool<Validator> validatorObjectPool = validatorObjectPoolMap.remove(schema);
        try {
            validatorObjectPool.close();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void updated(Dictionary properties) throws ConfigurationException {
        Dictionary props = properties != null ? properties : new Properties();

        Map<String, GenericObjectPool.Config> configMap = buildConfigMap(props);
        createPools(configMap);

        validatorConfig = buildConfig(props, Validator.class.getName());
    }

    @Override
    public void returnDocumentBuilder(DocumentBuilder documentBuilder) {
        returnIt(DocumentBuilder.class.getName(), documentBuilder);
    }

    @Override
    public void returnSAXParser(SAXParser saxParser) {
        returnIt(SAXParser.class.getName(), saxParser);
    }

    @Override
    public void returnXPath(XPath xpath) {
        returnIt(XPath.class.getName(), xpath);
    }

    @Override
    public void returnTransformer(Transformer transformer) {
        returnIt(Transformer.class.getName(), transformer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void returnValidator(Schema schema, Validator validator) {
        try {
            validatorObjectPoolMap.get(schema).returnObject(validator);
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }

    private Map<String, GenericObjectPool.Config> buildConfigMap(Dictionary properties) {
        Map<String, GenericObjectPool.Config> configMap = new HashMap<String, GenericObjectPool.Config>();

        for (String item : CONFIG_ITEMS) {
            configMap.put(item, buildConfig(properties, item));
        }

        return configMap;
    }

    private GenericObjectPool.Config buildConfig(Dictionary properties, String prefix) {
        GenericObjectPool.Config config = new GenericObjectPool.Config();

        String maxIdle = (String) properties.get(prefix + MAX_IDLE);
        if (maxIdle != null) {
            config.maxIdle = Integer.valueOf(maxIdle);
        }

        String minIdle = (String) properties.get(prefix + MIN_IDLE);
        if (minIdle != null) {
            config.minIdle = Integer.valueOf(minIdle);
        }

        String maxActive = (String) properties.get(prefix + MAX_ACTIVE);
        if (maxActive != null) {
            config.maxActive = Integer.valueOf(maxActive);
        }

        String maxWait = (String) properties.get(prefix + MAX_WAIT);
        if (maxWait != null) {
            config.maxWait = Long.valueOf(maxWait);
        }

        String timeBetweenEvictionRunsMillis = (String) properties.get(prefix + TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        if (timeBetweenEvictionRunsMillis != null) {
            config.timeBetweenEvictionRunsMillis = Long.parseLong(timeBetweenEvictionRunsMillis);
        }

        String minEvictableIdleTimeMillis = (String) properties.get(prefix + MIN_EVICTABLE_IDLE_TIME_MILLIS);
        if (minEvictableIdleTimeMillis != null) {
            config.minEvictableIdleTimeMillis = Long.parseLong(minEvictableIdleTimeMillis);
        }

        return config;
    }

    private void createPools(Map<String, GenericObjectPool.Config> configMap) {
        close();

        objectPoolMap.put(DocumentBuilder.class.getName(), new GenericObjectPool<DocumentBuilder>(new
                DocumentBuilderObjectFactory(), configMap.get(DocumentBuilder.class.getName())));
        objectPoolMap.put(SAXParser.class.getName(), new GenericObjectPool<SAXParser>(new SAXParserObjectFactory(),
                configMap.get(SAXParser.class.getName())));
        objectPoolMap.put(XPath.class.getName(), new GenericObjectPool<XPath>(new
                XPathObjectFactory(), configMap.get(XPath.class.getName())));
        objectPoolMap.put(Transformer.class.getName(), new GenericObjectPool<Transformer>(new
                TransformerObjectFactory(), configMap.get(Transformer.class.getName())));
    }

    @SuppressWarnings("unchecked")
    private void returnIt(String key, Object object) {
        try {
            objectPoolMap.get(key).returnObject(object);
        } catch (Exception e) {
            throw new JAXOException(e.getMessage(), e);
        }
    }
}
