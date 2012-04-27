package com.honnix.jaxo.core.internal.factory;

import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link DocumentBuilderObjectFactory}.
 *
 * @author honnix
 */
public class DocumentBuilderObjectFactoryTest {
    private DocumentBuilderObjectFactory factory;


    public DocumentBuilderObjectFactoryTest() {
        super();
    }

    @Before
    public void before() {
        factory = new DocumentBuilderObjectFactory();
    }

    @Test
    public void testMakeObject() throws Exception {
        DocumentBuilder builder = factory.makeObject();
        assertNotNull("sorry can not make more object", builder);
    }

    @Test
    public void testPassivateObject() throws Exception {
        DocumentBuilder mockBuilder = mock(DocumentBuilder.class);
        factory.passivateObject(mockBuilder);
        verify(mockBuilder).reset();
    }
}
