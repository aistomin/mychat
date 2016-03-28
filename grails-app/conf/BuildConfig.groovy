grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.project.fork = [
    test   : false,
    run    : false,
    war    : false,
    console: false
]

grails.project.dependency.resolver = "maven"
grails.tomcat.nio = true
grails.project.dependency.resolution = {
    inherits("global") {
    }
    log "error"
    checksums true
    legacyResolve false

    repositories {
        inherits true

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
    }

    plugins {
        build ":tomcat:7.0.55.2"
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        compile ":asset-pipeline:2.1.5"
        compile "org.grails.plugins:atmosphere-meteor:1.0.5"
        compile "org.grails.plugins:spring-security-core:2.0.0"
        compile "org.grails.plugins:spring-security-ui:1.0-RC3"
        runtime ":hibernate4:4.3.8.1"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
    }
}
