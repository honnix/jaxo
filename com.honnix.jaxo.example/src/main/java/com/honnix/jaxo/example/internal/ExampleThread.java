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
package com.honnix.jaxo.example.internal;

import com.honnix.jaxo.core.services.CoreService;
import com.honnix.jaxo.core.services.PoolableCoreService;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * The thread which is used to test core services.
 *
 * @author honnix
 */
public class ExampleThread extends Thread {
    private BundleContext context;

    public ExampleThread(BundleContext context) {
        super();

        this.context = context;
    }

    @Override
    public void run() {
        ServiceTracker coreServiceTracker = new ServiceTracker(context, CoreService.class.getName(), null);
        coreServiceTracker.open();
        verifyCoreService(coreServiceTracker);
        verifyCoreService(coreServiceTracker);

        ServiceTracker poolableCoreServiceTracker = new ServiceTracker(context, PoolableCoreService.class.getName(),
                null);
        poolableCoreServiceTracker.open();
        verifyPoolableCoreService(poolableCoreServiceTracker);
        verifyPoolableCoreService(poolableCoreServiceTracker);
    }

    private void verifyCoreService(ServiceTracker coreServiceTracker) {
        try {
            coreServiceTracker.waitForService(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        CoreService coreService = (CoreService) coreServiceTracker.getService();

        DocumentBuilderFactory builderFactory = coreService.createDocumentBuilderFactory();
        assert (builderFactory != null);
        System.out.println("DocumentBuilderFactory is good");

        XPathFactory xpathFactory = coreService.createXPathFactory();
        assert (xpathFactory != null);
        System.out.println("XPathFactory is good");

        TransformerFactory transformerFactory = coreService.createTransformerFactory();
        assert (transformerFactory != null);
        System.out.println("TransformerFactory is good");

        DocumentBuilder builder = coreService.getDocumentBuilder();
        assert (builder != null);
        System.out.println("DocumentBuilder is good");

        XPath xpath = coreService.getXPath();
        assert (xpath != null);
        System.out.println("XPath is good");

        Transformer transformer = coreService.getTransformer();
        assert (transformer != null);
        System.out.println("Transformer is good");
    }

    private void verifyPoolableCoreService(ServiceTracker poolableCoreServiceTracker) {
        try {
            poolableCoreServiceTracker.waitForService(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        PoolableCoreService poolableCoreService = (PoolableCoreService) poolableCoreServiceTracker.getService();

        DocumentBuilderFactory builderFactory = poolableCoreService.createDocumentBuilderFactory();
        assert (builderFactory != null);
        System.out.println("DocumentBuilderFactory is good");

        XPathFactory xpathFactory = poolableCoreService.createXPathFactory();
        assert (xpathFactory != null);
        System.out.println("XPathFactory is good");

        TransformerFactory transformerFactory = poolableCoreService.createTransformerFactory();
        assert (transformerFactory != null);
        System.out.println("TransformerFactory is good");

        DocumentBuilder builder = poolableCoreService.getDocumentBuilder();
        assert (builder != null);
        System.out.println("DocumentBuilder is good");
        poolableCoreService.returnDocumentBuilder(builder);

        XPath xpath = poolableCoreService.getXPath();
        assert (xpath != null);
        System.out.println("XPath is good");
        poolableCoreService.returnXPath(xpath);

        Transformer transformer = poolableCoreService.getTransformer();
        assert (transformer != null);
        System.out.println("Transformer is good");
        poolableCoreService.returnTransformer(transformer);
    }
}
