plugins {
    id("java")
    id("dev.architectury.loom") version("1.7-SNAPSHOT")
    id("architectury-plugin") version("3.4-SNAPSHOT")
    kotlin("jvm") version "1.9.23"
}


group = "com.github.yajatkaul"
version = "1.0.1-neoforge"

architectury {
    platformSetupLoomIde()
    neoForge()
}

val generatedResources = file("src/generated")
val defaultResources = file("src/main/resources")

sourceSets {
    main {
        resources.srcDir(generatedResources)
    }
}

loom {
    silentMojangMappingsLicense()
    runs {
        register("data") {
            data()
            programArgs("--all", "--mod", "mega_showdown")
            programArgs("--output", generatedResources.absolutePath, "--existing", defaultResources.absolutePath)
        }
    }
}

repositories {
    mavenCentral()
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.neoforged.net")
}

dependencies {
    minecraft("net.minecraft:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())
    neoForge("net.neoforged:neoforge:21.1.66")

    modImplementation("com.cobblemon:neoforge:1.6.0+1.21.1-SNAPSHOT")
    //Needed for cobblemon
    implementation("thedarkcolour:kotlinforforge-neoforge:5.3.0") {
        exclude("net.neoforged.fancymodloader", "loader")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(project.properties)
    }
}




