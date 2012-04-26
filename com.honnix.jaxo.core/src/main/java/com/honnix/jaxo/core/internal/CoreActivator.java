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
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ManagedService;

import java.util.Properties;

/**
 * Extension of the default OSGi bundle activator
 */
public final class CoreActivator implements BundleActivator {
    public void start(BundleContext bc) throws Exception {
        CoreService coreService = new CoreServiceImpl();
        PoolableCoreService poolableCoreService = new PoolableCoreServiceImpl();

        bc.registerService(CoreService.class.getName(), coreService, new Properties());
        bc.registerService(PoolableCoreService.class.getName(), poolableCoreService, new Properties());

        Properties properties = new Properties();
        properties.setProperty("service.pid", PoolableCoreService.class.getName());
        bc.registerService(ManagedService.class.getName(), poolableCoreService, properties);
    }

    public void stop(BundleContext bc) throws Exception {
    }
}

