import org.flywaydb.gradle.FlywayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Target
import org.jooq.meta.jaxb.ForcedType

val jacksonVersion = "2.9.9"
val swaggerVersion = "2.9.2"
val mysqlVersion = "8.0.12"
val jooQVersion = "3.11.5"
val flywayVersion = "5.2.4"
val shiroVersion = "1.4.1"


val dbUser = "root"
val dbPasswd = "root"


plugins {
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("nu.studer.jooq") version "3.0.3"
    id("org.flywaydb.flyway") version "5.2.4"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    application
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("mysql:mysql-connector-java:8.0.12")
        classpath("nu.studer:gradle-jooq-plugin:2.0.7")
    }
}

group = "info.galudisu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    implementation {
        exclude(module = "spring-boot-starter-tomcat")
        exclude(module = "spring-boot-starter-logging")
        exclude(module = "undertow-websockets-jsr")
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // shiro
    compile("org.apache.shiro:shiro-core:$shiroVersion")
    compile("org.apache.shiro:shiro-web:$shiroVersion")
    compile("org.apache.shiro:shiro-spring:$shiroVersion")
    // jooq
    compile("org.jooq:jooq:$jooQVersion")
    // flyway
    compile("org.flywaydb:flyway-core:$flywayVersion")
    // jackson json
    compile("com.fasterxml.jackson.module:jackson-modules-java8:$jacksonVersion")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    compile("com.fasterxml.jackson.module:jackson-module-parameter-names:$jacksonVersion")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    // swagger
    compile("io.springfox:springfox-swagger2:$swaggerVersion")
    compile("io.springfox:springfox-swagger-ui:$swaggerVersion")
    // apache common
    compile("commons-codec:commons-codec:1.12")
    compile("org.apache.commons:commons-lang3:3.9")
    compile("org.apache.commons:commons-collections4:4.3")
    compile("org.apache.commons:commons-math3:3.6.1")
    compile("commons-io:commons-io:2.6")
    // gson
    compile("com.google.code.gson:gson:2.8.5")
    // okhttp
    compile("com.squareup.okhttp3:okhttp:3.14.2")
}

configure<FlywayExtension> {
    driver = "com.mysql.cj.jdbc.Driver"
    url = "jdbc:mysql://localhost/comic-cloud-admin?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
    user = dbUser
    password = dbPasswd
    sqlMigrationPrefix = "MySQL-"
    baselineOnMigrate = true
}

tasks {
    val jooqCodeGen by creating(DefaultTask::class) {
        dependsOn(get("flywayMigrate"))

        fun target(): Target = Target()
                .withDirectory("src/main/java")
                .withPackageName("info.galudisu.comic.model")
                .withClean(true)

        fun generate(): Generate = Generate()
                .withJavaTimeTypes(false)
                .withComments(false)
                .withJavadoc(false)
                .withDeprecated(false)

        fun database(): Database = Database()
                .withName("org.jooq.meta.mysql.MySQLDatabase")
                .withExcludes("flyway_schema_history")
                .withIncludes(".*")
                .withInputSchema("comic-cloud-admin")
                .withForcedTypes(ForcedType()
                        .withUserType("java.time.Instant")
                        .withConverter("info.galudisu.comic.converter.InstantConverter")
                        .withTypes("TIMESTAMP"))

        val configuration = Configuration().apply {
            jdbc = Jdbc().apply {
                driver = "com.mysql.cj.jdbc.Driver"
                url = "jdbc:mysql://localhost/comic-cloud-admin?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
                user = dbUser
                password = dbPasswd
                schema = "public"
            }

            generator = Generator()
                    .withDatabase(database())
                    .withGenerate(generate())
                    .withTarget(target())
        }
        GenerationTool.generate(configuration)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
