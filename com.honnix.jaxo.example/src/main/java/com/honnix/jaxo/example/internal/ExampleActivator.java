package com.honnix.jaxo.example.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The main entrance of this bundle.
 */
public final class ExampleActivator implements BundleActivator {
    public ExampleActivator() {
        super();
    }

    /**
     * Hooked into <code>start</code> lifecycle.
     *
     * @param context bundle context
     * @throws Exception anything wrong
     */

    public void start(BundleContext context) throws Exception {
        new ExampleThread(context).start();
    }

    /**
     * Hooked into <code>stop</code> lifecycle.
     *
     * @param context bundle context
     * @throws Exception anything wrong
     */

    public void stop(BundleContext context) throws Exception {
    }
}

