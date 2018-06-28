package de.dmeiners.mapping.api

import spock.lang.Specification

class ClasspathScriptNameResolverSpec extends Specification {

    def "fails on non-existing script name"() {

        given:
        def subject = new ClasspathScriptNameResolver()

        when:
        subject.resolve("nonExisting.jexl")

        then:
        def e = thrown(ScriptNameResolutionException)
        e.message == "Could not find any of the following classpath resources: [/jexl/nonExisting.jexl]."
    }

    def "fails on non-existing script name when a tenant prefix is available"() {

        given:
        def tenantId = "5dc6dfaa-b2e2-492a-8c04-380e9c4c3371"
        def subject = new ClasspathScriptNameResolver()

        when:
        subject.resolve("nonExisting.jexl", [tenantId: tenantId])

        then:
        def e = thrown(ScriptNameResolutionException)
        e.message == "Could not find any of the following classpath resources: [/jexl/5dc6dfaa-b2e2-492a-8c04-380e9c4c3371/nonExisting.jexl, /jexl/nonExisting.jexl]."
    }

    def "resolves an existing script name"() {

        given:
        def subject = new ClasspathScriptNameResolver()

        expect:
        subject.resolve("myScript.jexl", [:]) == "// I am here"
    }

    def "resolves an existing script name when a tenant prefix is available"() {

        given:
        def tenantId = "5dc6dfaa-b2e2-492a-8c04-380e9c4c3371"
        def subject = new ClasspathScriptNameResolver()

        expect:
        subject.resolve("myScript.jexl", [tenantId: tenantId]) == "// Tenant override"
    }

    def "falls back to default script name when a tenant prefix is invalid"() {

        given:
        def tenantId = "0b05940f-8ec9-4e8c-8a83-f6acb7578d73"
        def subject = new ClasspathScriptNameResolver()

        expect:
        subject.resolve("myScript.jexl", [tenantId: tenantId]) == "// I am here"
    }
}
