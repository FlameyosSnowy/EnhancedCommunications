plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

group = "me.flame.communication"
version = "1.2.1"

repositories {
    mavenCentral()
    maven("https://repository.apache.org/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.foxikle.dev/flameyos")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation("dev.dejvokep:boosted-yaml:1.3.6")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")

    implementation("dev.velix:imperat-core:1.5.1")
    implementation("dev.velix:imperat-bukkit:1.5.1")

    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    compileOnly("net.luckperms:api:5.4")
    compileOnly("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")
}

tasks.shadowJar {
    archiveClassifier.set("")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

publishing {
    repositories {
        maven {
            name = "mainRepository"
            url = uri("https://repo.foxikle.dev/flameyos")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            artifact(tasks["shadowJar"])
            //artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}

tasks.processResources {
    filesMatching("paper-plugin.yml") {
        filter {
            it.replace("{project.version}", version.toString())
        }
    }
}

tasks {
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    javadoc {
        (options as StandardJavadocDocletOptions).tags("apiNote:a:API Note:")
        options.encoding = Charsets.UTF_8.name()
        exclude("**/internal/**", "**/versions/**")
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    jar {
        archiveClassifier = "core"
    }
}