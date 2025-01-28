rootProject.name = "Fabric" //By default, this is how your built jar is called

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net/")
        gradlePluginPortal()
    }
}
