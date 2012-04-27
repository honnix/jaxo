package com.honnix.jaxo.core.internal;

import com.honnix.jaxo.core.services.CoreService;
import com.honnix.jaxo.core.services.PoolableCoreService;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ManagedService;

import java.util.Properties;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link CoreActivator}.
 *
 * @author honnix
 */
public class CoreActivatorTest {
    private BundleActivator activator;

    private BundleContext mockBundleContext;

    public CoreActivatorTest() {
        super();
    }

    @Before
    public void before() {
        activator = new CoreActivator();
        mockBundleContext = mock(BundleContext.class);
    }

    @Test
    public void testStartStop() throws Exception {
        activator.start(mockBundleContext);

        verify(mockBundleContext).registerService(eq(CoreService.class.getName()), any(CoreService.class),
                eq(new Properties()));
        verify(mockBundleContext).registerService(eq(PoolableCoreService.class.getName()),
                any(PoolableCoreService.class), eq(new Properties()));

        Properties properties = new Properties();
        properties.setProperty("service.pid", PoolableCoreService.class.getName());
        verify(mockBundleContext).registerService(eq(ManagedService.class.getName()), any(PoolableCoreService.class),
                eq(properties));

        activator.stop(mockBundleContext);
    }
}
