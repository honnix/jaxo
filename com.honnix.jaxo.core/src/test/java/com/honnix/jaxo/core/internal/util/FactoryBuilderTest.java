package com.honnix.jaxo.core.internal.util;

import com.honnix.jaxo.core.internal.jaxb.ObjectFactory;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.apache.xerces.jaxp.validation.XMLSchemaFactory;
import org.apache.xpath.jaxp.XPathFactoryImpl;
import org.junit.Test;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link FactoryBuilder}.
 *
 * @author honnix
 */
public class FactoryBuilderTest {
    public FactoryBuilderTest() {
        super();
    }

    @Test
    public void testBuildDocumentBuilderFactory() {
        DocumentBuilderFactory factory = FactoryBuilder.buildDocumentBuilderFactory();
        assertNotNull("oops, we failed to build factory", factory);
        assertTrue("it should be namespace aware", factory.isNamespaceAware());
        assertTrue("it is the wrong type", factory instanceof DocumentBuilderFactoryImpl);
    }

    public void testBuildSAXParserFactory() {
        SAXParserFactory factory = FactoryBuilder.buildSAXParserFactory();
        assertNotNull("oops, we failed to build factory", factory);
        assertTrue("it should be namespace aware", factory.isNamespaceAware());
        assertTrue("it is the wrong type", factory instanceof SAXParserFactoryImpl);
    }

    @Test
    public void testBuildXPathFactory() {
        XPathFactory factory = FactoryBuilder.buildXPathFactory();
        assertNotNull("oops, we failed to build factory", factory);
        assertTrue("it is the wrong type", factory instanceof XPathFactoryImpl);
    }

    @Test
    public void testBuildTransformerFactory() {
        TransformerFactory factory = FactoryBuilder.buildTransformerFactory();
        assertNotNull("oops, we failed to build factory", factory);
        assertTrue("it is the wrong type", factory instanceof TransformerFactoryImpl);
    }

    @Test
    public void testBuildSchemaFactory() {
        SchemaFactory factory = FactoryBuilder.buildSchemaFactory();
        assertNotNull("oops, we failed to build factory", factory);
        assertTrue("it should support xml schema", factory.isSchemaLanguageSupported(XMLConstants
                .W3C_XML_SCHEMA_NS_URI));
        assertTrue("it is the wrong type", factory instanceof XMLSchemaFactory);
    }

    @Test
    public void testBuildJAXBContext() {
        FactoryBuilder.buildJAXBContext(ObjectFactory.class.getPackage().getName(), getClass().getClassLoader(),
                new HashMap<String, Object>());
    }
}
