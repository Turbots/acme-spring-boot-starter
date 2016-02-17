# spring-boot-starter-acme
Spring Boot Starter for Automatic Certificate Management Environment

[![Build Status](https://travis-ci.org/Turbots/acme-spring-boot-starter.svg?branch=master)](https://travis-ci.org/Turbots/acme-spring-boot-starter)

== Goal of this spring-boot-starter

This module was made to be able to create certificates for Spring Boot applications using the ACME (Automatic Certificate Management Environment) spec.
When added as dependency in a Spring Boot application, it will expose a controller method at `/.well-known/acme-challenge/{token}` which will respond with the configured response token.

More information about the ACME spec can be found [here](https://letsencrypt.github.io/acme-spec/).

== Usage

`
<dependency>
    <groupId>be.hubau</groupId>
    <artifactId>acme-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
`