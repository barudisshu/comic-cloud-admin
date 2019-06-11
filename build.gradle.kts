import org.flywaydb.gradle.FlywayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Target

val jacksonVersion = "2.9.9"
val swaggerVersion = "2.9.2"
val mysqlVersion = "8.0.12"
val jooQVersion = "3.11.5"
val flywayVersion = "4.0.3"


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
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // jooq
    compile("org.jooq:jooq:$jooQVersion")
    // flyway
    compile("org.flywaydb:flyway-core:$flywayVersion")
    // log
    compile("net.logstash.logback:logstash-logback-encoder:4.11")
    // jackson json
    compile("com.fasterxml.jackson.module:jackson-modules-java8:$jacksonVersion")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    compile("com.fasterxml.jackson.module:jackson-module-parameter-names:$jacksonVersion")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    // swagger
    compile("io.springfox:springfox-swagger2:$swaggerVersion")
    compile("io.springfox:springfox-swagger-ui:$swaggerVersion")
}

configure<FlywayExtension> {
    driver = "com.mysql.cj.jdbc.Driver"
    url = "jdbc:mysql://localhost/comic?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
    user = dbUser
    password = dbPasswd
    sqlMigrationPrefix = "MySQL-"
    baselineOnMigrate = true
}

tasks {
    val jooqCodeGen by creating(DefaultTask::class) {
        dependsOn(get("flywayMigrate"))

        val configuration = Configuration().apply {
            jdbc = Jdbc().apply {
                driver = "com.mysql.cj.jdbc.Driver"
                url = "jdbc:mysql://localhost/comic?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC"
                user = dbUser
                password = dbPasswd
                schema = "public"
            }
            generator = Generator().apply {
                database = Database().apply {
                    name = "org.jooq.meta.mysql.MySQLDatabase"
                    includes = ".*"
                    excludes = "flyway*.*"
                    inputSchema = "comic"
                }
                target = Target().apply {
                    packageName = "info.galudisu.comic.model"
                    directory = "src/main/java"
                }
            }
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
