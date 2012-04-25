# Introduction

JAXP, JAXB, JAXWS, these XML stuffs can not work well together with OSGi due to class loading strategy.
Also nearly all of them have critical performance problem if without proper pooling.

This bundle will wrapp latest implementations, pool those damn factories, and finally provide services.