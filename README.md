# U-Mod

[
![Curseforge Downloads](http://cf.way2muchnoise.eu/u-mod.svg)
![Curseforge Versions](http://cf.way2muchnoise.eu/versions/u-mod.svg)
](https://www.curseforge.com/minecraft/mc-mods/u-mod)
[
![Travis](https://api.travis-ci.org/MC-U-Team/U-Mod.svg?branch=1.14.4)]
](https://travis-ci.org/MC-U-Team/U-Mod)
[
![Discord](https://img.shields.io/discord/297104769649213441?label=Discord)
](https://discordapp.com/invite/QXbWS36)

### This mod adds many machines and automation systems

- Download on [curseforge](https://www.curseforge.com/minecraft/mc-mods/u-mod).  
- Find more information on our [website](https://u-team.info/mods/umod).
- Updates can be found in the [changelog](CHANGELOG.md).

### How to build this mod

#### Setup Eclipse
- ``./gradlew genEclipseRuns eclipse``
- Import project as existing workspace

#### Setup IntelliJ IDEA
- ``./gradlew genIntellijRuns``
- Import as gradle project

#### Build
- ``./gradlew build``

### How to include this mod

- Repository: [repo.u-team.info](https://repo.u-team.info)
- Artifact: **info.u-team:u_mod-${config.forge.mcversion}:${config.umod.version}** 
- *{config.forge.mcversion}* is the minecraft version.
- *{config.umod.version}* is the umod version.

#### Using in Forge Gradle 3:
```gradle
repositories {
    maven { url = "https://repo.u-team.info" }
}

dependencies {
  compileOnly fg.deobf("info.u-team:u_mod-${config.forge.mcversion}:${config.umod.version}")
}
```

### License

- This mod is licensed under apache 2 license. For more information see [here](LICENSE).  
- This mod can be packed in any curseforge modpack you like.

### Issues

- Please report issues to the [github issues](../../issues).
- Include your minecraft version, forge version and mod version.
- Upload your log on [gist](https://gist.github.com) or [pastebin](https://pastebin.com) and include link in your report.
