grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

//Mail plugin config
grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "myedro@kaitzen-solutions.com"
        password = "asdfghijk"
        props = ["mail.smtp.auth":"true",
                 "mail.smtp.socketFactory.port":"465",
                 "mail.smtp.socketFactory.class":
                         "javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback":"false"]
    }
}


grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        mavenRepo "http://repo.grails.org/grails/core" // needed for compass, etc.
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    def gebVersion = "0.9.2"
    def seleniumVersion = "2.41.0"

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.27'
        // runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'

        //Adds Geb/Spock integration
        test "org.gebish:geb-spock:$gebVersion"

        //Uses Firefox for functional tests
        test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
        test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
        test "com.github.groovy-wslite:groovy-wslite:0.7.2"

        //test "org.seleniumhq.selenium:selenium-firefox-driver:2.31.0"

        compile "org.apache.lucene:lucene-spellchecker:2.4.1"
        compile "org.apache.lucene:lucene-highlighter:2.4.1"
    }


    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.52.1"

        // plugins for the compile step
        compile ":scaffolding:2.0.3"
        compile ':cache:1.1.2'
        compile ':mail:1.0.1'
        compile ":searchable:0.6.5-SNAPSHOT"
        //compile ":spring-security-core:2.0"
        compile ':spring-security-core:2.0-RC2'
        compile ":spring-security-twitter:0.6.2"
        compile ":platform-core:1.0.RC5"
        compile ":jms:1.3"
        compile "org.grails.plugins:activemq:0.5"


        // plugins needed at runtime but not for compilation
        runtime ":hibernate:3.6.10.13" // or ":hibernate4:4.3.5.1"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.0.2"
        runtime ":resources:1.2.7"
        runtime ":navigation:1.3.2"

        test ":geb:$gebVersion" //Adds Gebs Grails Plugin
        test ':dumbster:0.2' //Allows to mock a smpt server


        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0.1"
        //runtime ":cached-resources:1.1"
        //runtime ":yui-minify-resources:0.1.5"

        // An alternative to the default resources plugin is the asset-pipeline plugin
        //compile ":asset-pipeline:1.6.1"

        // Uncomment these to enable additional asset-pipeline capabilities
        //compile ":sass-asset-pipeline:1.5.5"
        //compile ":less-asset-pipeline:1.5.3"
        //compile ":coffee-asset-pipeline:1.5.0"
        //compile ":handlebars-asset-pipeline:1.3.0.1"
    }
}
