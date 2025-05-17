plugins {
    id("java")
    id("dev.architectury.loom") version("1.7-SNAPSHOT")
    id("architectury-plugin") version("3.4-SNAPSHOT")
    kotlin("jvm") version "1.9.23"
}

group = "com.github.yajatkaul"
version = "9.6.1-release-fabric"

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
    modImplementation("net.fabricmc:fabric-loader:0.16.5")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.115.6+1.21.1")
    modImplementation(fabricApi.module("fabric-command-api-v2", "0.115.6+1.21.1"))

    modImplementation("net.fabricmc:fabric-language-kotlin:1.12.3+kotlin.2.0.21")

    modImplementation("com.cobblemon:fabric:1.6.1+1.21.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    modImplementation("maven.modrinth:supermartijn642s-config-lib:1.1.8-fabric-mc1.21")

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