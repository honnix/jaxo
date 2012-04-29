package com.honnix.jaxo.core.internal.factory;

import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.SAXParser;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link com.honnix.jaxo.core.internal.factory.SAXParserObjectFactory}.
 *
 * @author honnix
 */
public class SAXParserObjectFactoryTest {
    private SAXParserObjectFactory factory;


    public SAXParserObjectFactoryTest() {
        super();
    }

    @Before
    public void before() {
        factory = new SAXParserObjectFactory();
    }

    @Test
    public void testMakeObject() throws Exception {
        SAXParser parser = factory.makeObject();
        assertNotNull("sorry can not make more object", parser);
    }

    @Test
    public void testPassivateObject() throws Exception {
        SAXParser mockParser = mock(SAXParser.class);
        factory.passivateObject(mockParser);
        verify(mockParser).reset();
    }
}
