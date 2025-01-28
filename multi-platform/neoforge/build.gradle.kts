plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

loom {
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()
}

repositories {
    mavenCentral()
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.neoforged.net")
}

val shadowBundle = configurations.create("shadowBundle") {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    minecraft("net.minecraft:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    neoForge("net.neoforged:neoforge:${property("neoforge_version")}")

    modImplementation("com.cobblemon:neoforge:${property("cobblemon_version")}") { isTransitive = false }
    //Needed for cobblemon
    forgeRuntimeLibrary("thedarkcolour:kotlinforforge-neoforge:${property("kotlin_for_forge_version")}") {
        exclude("net.neoforged.fancymodloader", "loader")
    }

    implementation(project(":common", configuration = "namedElements"))
    "developmentNeoForge"(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowBundle(project(":common", configuration = "transformProductionFabric"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junit_version")}")
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

tasks {

    jar {
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        exclude("fabric.mod.json")
        archiveClassifier.set("dev-shadow")
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        configurations = listOf(shadowBundle)
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveVersion.set("${rootProject.version}")
    }
}