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

import com.honnix.jaxo.core.internal.services.CoreServiceImpl;
import com.honnix.jaxo.core.internal.services.PoolableCoreServiceImpl;
import com.honnix.jaxo.core.services.CoreService;
import com.honnix.jaxo.core.services.PoolableCoreService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ManagedService;

import java.util.Properties;

/**
 * The main entrance of this bundle.
 */
public final class CoreActivator implements BundleActivator {
    private final PoolableCoreService poolableCoreService;

    public CoreActivator() {
        super();

        poolableCoreService = new PoolableCoreServiceImpl();
    }

    /**
     * Hooked into <code>start</code> lifecycle.
     *
     * @param context bundle context
     * @throws Exception anything wrong
     */
    public void start(BundleContext context) throws Exception {
        context.registerService(CoreService.class.getName(), new CoreServiceImpl(), new Properties());
        context.registerService(PoolableCoreService.class.getName(), poolableCoreService, new Properties());

        Properties properties = new Properties();
        properties.setProperty("service.pid", PoolableCoreService.class.getName());
        context.registerService(ManagedService.class.getName(), poolableCoreService, properties);
    }

    /**
     * Hooked into <code>stop</code> lifecycle.
     *
     * @param context bundle context
     * @throws Exception anything wrong
     */
    public void stop(BundleContext context) throws Exception {
        poolableCoreService.close();
    }
}

