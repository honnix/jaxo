# Introduction [![Build Status](https://secure.travis-ci.org/honnix/jaxo.png?branch=0.1.0)](http://travis-ci.org/honnix/jaxo)

JAXP, JAXB, JAXWS, these XML stuffs can not work well together with OSGi due to class loading strategy.
Also nearly all of them are not thread-safe and  have critical performance problem if without proper pooling.

The core bundle wraps latest implementations, pools, and finally provides services.

# Build and Run

`> mvn clean install`

`> mvn pax:provision`

# Details

## com.honnix.jaxo.core

This bundle provides following OSGi services:

* CoreService

    This service uses `ThreadLocal` to cache `DocumentBuilder`, `XPath`, etc. The client only needs to get and use without
    concerning about return back.

* PoolableCoreSerivce

    This service uses object pool to cache `DocumentBuilder`, `XPath`, etc. Since this is a pool, the client needs to get,
    use and then return it back, otherwise the object will be lost.

    For each kind of pool, it can be configured separately through standard OSGi `ConfigAdmin` service. Each configuration
    key is prefixed with the class name, for example:

    * javax.xml.parsers.DocumentBuilder.maxIdle=4
    * javax.xml.parsers.DocumentBuilder.minIdle=2
    * javax.xml.parsers.DocumentBuilder.maxActive=6
    * javax.xml.parsers.DocumentBuilder.maxWait=100
    * javax.xml.parsers.DocumentBuilder.timeBetweenEvictionRunsMillis=100
    * javax.xml.parsers.DocumentBuilder.minEvictableIdleTimeMillis=1000
    * javax.xml.xpath.XPath.maxIdle=4
    * javax.xml.xpath.XPath.minIdle=2
    * javax.xml.xpath.XPath.maxActive=6
    * javax.xml.xpath.XPath.maxWait=100
    * javax.xml.xpath.XPath.timeBetweenEvictionRunsMillis=100
    * javax.xml.xpath.XPath.minEvictableIdleTimeMillis=1000

    For what these configurations mean, please refer to [Apache Commons Pool](http://commons.apache.org/pool/).

    Currently, following types of objects can be configured:

    * javax.xml.parsers.DocumentBuilder
    * javax.xml.parsers.SAXParser
    * javax.xml.xpath.XPath
    * javax.xml.transform.Transformer
    * javax.xml.validation.Validator

    `service.pid` equals to service name, and if not configured, default values provided by
    [Apache Commons Pool](http://commons.apache.org/pool/) will be used.

## com.honnix.jaxo.example

This bundle acts as an example of how to use services. It is very simple, so just read through the source code. One thing
need to mention is maven dependency. The client should define dependencies:

```xml
<dependency>
  <groupId>com.honnix.jaxo</groupId>
  <artifactId>com.honnix.jaxo.core</artifactId>
  <version>${release.version}</version>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>xml-apis</groupId>
  <artifactId>xml-apis</artifactId>
  <version>1.4.01</version>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>javax.xml.bind</groupId>
  <artifactId>jaxb-api</artifactId>
  <version>2.2.6</version>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>javax.xml.ws</groupId>
  <artifactId>jaxws-api</artifactId>
  <version>2.2.8</version>
  <scope>provided</scope>
</dependency>
```

And the client should not delegate `javax.xml` to bootstrap class loader.
