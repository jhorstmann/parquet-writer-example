plugins {
    java
    application
}

group = "net.jhorstmann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass.set("net.jhorstmann.parquetwriterexample.Main")
}
dependencies {
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    implementation("org.apache.parquet:parquet-avro:1.13.1")

    // exclude a bunch of hadoop stuff that is not needed for a standalone program
    implementation("org.apache.hadoop:hadoop-common:3.3.3") {
        exclude(group = "org.apache.hadoop.thirdparty", module = "hadoop-shaded-protobuf_3_7")
        exclude(group = "org.apache.curator")
        exclude(group = "org.apache.zookeeper")
        exclude(group = "org.apache.kerby")
        exclude(group = "org.mortbay.jetty")
        exclude(group = "org.eclipse.jetty")
        exclude(group = "javax.servlet.jsp")
        exclude(group = "javax.servlet")
        exclude(group = "javax.activation")
        exclude(group = "com.sun.jersey")
        exclude(group = "com.google.protobuf")
        exclude(group = "com.google.inject")
        exclude(group = "com.jcraft")
        exclude(group = "org.slf4j")
        exclude(group = "log4j")
        exclude(group = "dnsjava")
    }
    // org.apache.hadoop.mapred.FileInputFormat seems to be needed for reading, but not for writing
    implementation("org.apache.hadoop:hadoop-mapreduce-client-core:3.3.3") {
        exclude(group = "org.apache.hadoop", module = "hadoop-hdfs-client")
        exclude(group = "org.apache.hadoop", module = "hadoop-yarn-client")
        exclude(group = "org.apache.hadoop", module = "hadoop-yarn-common")
        exclude(group = "com.google.inject")
        exclude(group = "com.google.inject.extensions")
        exclude(group = "com.google.protobuf")
        exclude(group = "io.netty")
        exclude(group = "org.slf4j")
    }

    constraints {
        implementation("com.google.guava:guava:30.1.1-jre")
        implementation("commons-io:commons-io:2.11.0")
        implementation("org.apache.commons:commons-compress:1.21")
        implementation("org.apache.avro:avro:1.10.2")
        implementation("org.apache.parquet:parquet-jackson:1.11.1")
        implementation("org.apache.parquet:parquet-hadoop:1.11.1")

        implementation("com.fasterxml.jackson.core:jackson-core:2.12.5")
        implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.5")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.12.5")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.12.5")
        implementation("com.fasterxml.jackson.module:jackson-modules-java8:2.12.5")

        implementation("net.minidev:json-smart:2.4.7")
    }
}