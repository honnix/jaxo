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
import com.honnix.jaxo.core.PoolableCoreService;
import com.honnix.jaxo.core.exception.JAXOException;
import com.honnix.jaxo.core.internal.factory.DocumentBuilderObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author honnix
 */
class PoolableCoreServiceImpl extends AbstractCoreServiceImpl implements PoolableCoreService, ManagedService {
    private static final String MAX_IDLE = ".maxIdle";

    private static final String MIN_IDLE = ".minIdle";

    private static final String MAX_ACTIVE = ".maxActive";

    private static final String MAX_WAIT = ".maxWait";

    private static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = ".timeBetweenEvictionRunsMillis";

    private static final String MIN_EVICTABLE_IDLE_TIME_MILLIS = ".minEvictableIdleTimeMillis";

    private Map<String, ObjectPool> objectPoolMap;

    public PoolableCoreServiceImpl() {
        super();

        objectPoolMap = new HashMap<String, ObjectPool>();
    }

    @Override
    public DocumentBuilder getDocumentBuilder() {
        try {
            return (DocumentBuilder) objectPoolMap.get(DocumentBuilder.class.getName()).borrowObject();
        } catch (Exception e) {
            throw new JAXOException("Unable to get DocumentBuilder.", e);
        }
    }

    @Override
    public XPath getXPath() {
        try {
            return (XPath) objectPoolMap.get(XPath.class.getName()).borrowObject();
        } catch (Exception e) {
            throw new JAXOException("Unable to get XPath.", e);
        }
    }

    @Override
    public Transformer getTransformer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Schema getSchema() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updated(Dictionary properties) throws ConfigurationException {
        if (properties == null) {
            createDefaultPools();
        } else {
            createConfiguredPools(buildConfigMap(properties));
        }
    }

    @Override
    public void returnDocumentBuilder(DocumentBuilder documentBuilder) {
        try {
            objectPoolMap.get(DocumentBuilder.class.getName()).returnObject(documentBuilder);
        } catch (Exception e) {
            throw new JAXOException("Unable to return DocumentBuilder.", e);
        }
    }

    @Override
    public void returnXPath(XPath xpath) {
        try {
            objectPoolMap.get(XPath.class.getName()).returnObject(xpath);
        } catch (Exception e) {
            throw new JAXOException("Unable to return XPath.", e);
        }
    }

    @Override
    public void returnTransformer(Transformer transformer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Map<String, GenericObjectPool.Config> buildConfigMap(Dictionary properties) {
        Map<String, GenericObjectPool.Config> configMap = new HashMap<String, GenericObjectPool.Config>();
        configMap.put(DocumentBuilder.class.getName(), buildConfig(properties, DocumentBuilder.class.getName()));
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

    private void createConfiguredPools(Map<String, GenericObjectPool.Config> configMap) {
        clearObjectPoolMap();
        objectPoolMap.put(DocumentBuilder.class.getName(), new GenericObjectPool<DocumentBuilder>(new
                DocumentBuilderObjectFactory(), configMap.get(DocumentBuilder.class.getName())));
    }

    private void createDefaultPools() {
        clearObjectPoolMap();
        objectPoolMap.put(DocumentBuilder.class.getName(), new GenericObjectPool<DocumentBuilder>(new
                DocumentBuilderObjectFactory()));
    }

    private void clearObjectPoolMap() {
        Iterator<ObjectPool> iterator = objectPoolMap.values().iterator();
        while (iterator.hasNext()) {
            ObjectPool objectPool = iterator.next();
            try {
                objectPool.close();
            } catch (Exception e) {
            }
            iterator.remove();
        }
    }
}
