plugins {
    id("java")
}

group = "me.actuallysoheil"
version = "3.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.andrei1058.dev/releases/")
}

dependencies {
    val lombok = "org.projectlombok:lombok:1.18.38"
    compileOnly(lombok)
    annotationProcessor(lombok)

    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    compileOnly("com.andrei1058.bedwars:bedwars-api:25.6")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}