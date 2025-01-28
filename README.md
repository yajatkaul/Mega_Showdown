# Cobblemon MDKs

These are MDKs (Mod Development Kits) provided specifically to help you get started with writing a side-mod for
cobblemon on whatever platform you prefer (or even multi-platform if you chose to do so).

## Get Started

To get started all you have to do is the following:

- Clone or download the repo (or the specific folder that contains the platform you like to use)
- (Rename the folder to match what your mod is going to be called)
- Inside the `settings.gradle` change the `rootProject.name` property to what you like, this also drives what the mod.jar is called in the end
  - Exception is the `Multi-Platform` mdk which instead refers to the `gradle.properties` > `archives_base_name`
- Inside your `build.gradle` update the `group` variable to something of your choice (example: `io.github.myname` refer to [this](#what-should-my-group-be) if you need help)
  - While the `group` variable is mainly important if you plan to write libraries or push your artifacts to a maven repo, it's still good practice to do!
  - This is also where you find the `version` variable which gets added to the mod.jar when you build (and inserted into the mod files that need it e.g. `fabric.mod.json`)
  - If you are using the `Multi-Platform` mdk, you will find these variables instead in your `gradle.properties` being `maven_group` and `mod_version` respectively
- Update folder structure for the code. This is determined by the `group` you have defined above
  - For example, if you have set up the group to be `io.github.username` and your mod is called `flying`, then your folder structure for the code should start as follows:
    - `<project-root>/src/main/java/io/github/username/flying/FlyingMod.java` - You'd then set up all your classes and more packages within the `flying` package
    - If you are using an IDE such as IntelliJ you can also tell it to "refactor" or move the example.java class for you into place
- Review the mod files and update their information to be accurate to the mod you are writing
  - For Fabric, you will find the mod file here: `<project-root>/src/main/resources/fabric.mod.json`
  - For NeoForge, you will find the mod file here: `<project-root>/src/main/resources/META-INF/neoforge.mods.toml`
  - If you are using the `Multi-Platform` mdk you will have both in their respective subprojects
    - In there you will find various fields like mod id, name, authors, license (not sure which one to use? [have a look at this](https://choosealicense.com/)) and also means of declaring dependencies. Refer to the documentation of your respective mod loader for further information on these files
    - **IMPORTANT**: The mod id of the `neoforge.mods.toml` needs to MATCH the `@Mod` annotation of your main mod file (`ExampleNeoForgeMod` in our MDKs) otherwise you will crash
    - **IMPORTANT**: The `fabric.mod.json` contains an `entrypoint` field which points to your mods main class, if you update the package or rename that class, you have to update the `entrypoint` in the `fabric.mod.json` too!
- Start writing your own code!

### What should my Group be?

In short, it can be whatever you want for the most part, but below are some conventions:

- If you own a domain, you could use that here (for example com.cobblemon for cobblemon.com)
- If you work in a group/organization and your mod is supposed to be part of that, you can tie it into the organization's domain
- If your project is open source and on GitHub, using `io.github.username` is also an option
- Finally, its also common practice to use your email address, if you have one specifically for development purposes (e.g. `com.gmail.username`)

You can read more about these package names and conventions in [Oracle's documentation](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)

### A cobblemon update released! What now?

The steps and effort needed for this depend on whether cobblemon has updated to a new Minecraft version.

#### Same Minecraft version

On the same minecraft version, the following steps would be an example of how to go about updating your mod:

- Try running your mod on the new cobblemon version without any changes and test it, it might be that your mod is compatible as-is and no changes are needed!
  - It might be that depending on your declaration of dependencies in your mod files, your mod loader of choice might say that your mod is not compatible, denying you the chance to test it
  - In that case, try changing the dependency declaration in your mod files temporarily so you can test it and verify it still works without further changes
- If your mod crashes or otherwise does not work as expect you will need to update your gradle configuration to target this new cobblemon release
  - To do that, go to your `build.gradle.kts` and look for the `dependencies` block
  - In there, you will find the declaration of cobblemon, change the version to match that new version you want to target (don't forget to update your mod files dependencies too!)
  - If your mod is based off the `Multi-Platform` mdk, refer to the `gradle.properties` file and update the versions declared in there instead
  - Refresh your gradle and fix any potential compile errors your IDE will tell you about
- Try running your mod again with the new cobblemon version
- If you continue to have issues, feel free to reach out in our cobblemon discord for further help

#### Different Minecraft version

Updating a mod to a different minecraft version can be of varying complexity depending on how many changes Minecraft itself did in those versions.
Your best bet is either reaching out directly for help in the cobblemon discord or refer back to the MDKs which are being updated as new versions of cobblemon get released.

## Links and more documentation

- [Cobblemon Website](https://cobblemon.com/)
- [Cobblemon Wiki](https://wiki.cobblemon.com/index.php/Main_Page)
- [Cobblemon Discord](https://discord.gg/cobblemon)
- [NeoForged Community Documentation](https://docs.neoforged.net/)  
- [NeoForged Discord](https://discord.neoforged.net/)
- [Fabric Documentation](https://docs.fabricmc.net/develop/)
- [Fabric Discord](https://discord.gg/v6v4pMv)
- [Architectury Loom Documentation](https://docs.architectury.dev/loom/introduction) - The core tooling to allow Minecraft modding in these MDKs