plugins {
    id("java")
    id("dev.architectury.loom") version("1.11-SNAPSHOT")
    id("architectury-plugin") version("3.4-SNAPSHOT")
    kotlin("jvm") version "2.2.10"
}

group = "com.github.yajatkaul"
version = "1.0.0+1.7-alpha-fabric"

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    silentMojangMappingsLicense()
    mixin {
        defaultRefmapName.set("mixins.${project.name}.refmap.json")
    }
}

fabricApi {
    configureDataGeneration()
}

repositories {
    mavenCentral()
    maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
    maven {
        name = "Ladysnake Libs"
        url = uri("https://maven.ladysnake.org/releases")
    }

    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }
}

dependencies {
    minecraft("net.minecraft:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.17.2")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.116.6+1.21.1")
    modImplementation(fabricApi.module("fabric-command-api-v2", "0.116.6+1.21.1"))

    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.5+kotlin.2.2.10")

    modImplementation("com.cobblemon:fabric:1.7.0+1.21.1-SNAPSHOT")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    modImplementation("maven.modrinth:supermartijn642s-config-lib:1.1.8-fabric-mc1.21")
    include("maven.modrinth:supermartijn642s-config-lib:1.1.8-fabric-mc1.21")

    modImplementation("dev.emi:trinkets:${properties["trinkets_version"]}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand(project.properties)
    }
}