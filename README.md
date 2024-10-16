# EnhancedCommunications
A next-generation chat plugin for enhancing Minecraft chat.

# ⚠️ DISCLAIMER: ONLY WORKS FOR PAPERMC 1.18+

## Features:
**High-performance**: Can process big messages and spam without hassle, chat delay, or TPS drops.

**Versatile API**: The API is very versatile where you can do anything from creating new actions to creating your managers for the plugin.

**Customizable**: Customize everything in this plugin for your server however you like!

**PlaceholderAPI**: This plugin supports PlaceholderAPI for practically everything, from actions to chat formatting, etc!

**MiniMessage**: EnhancedCommunications specializes over other plugins by staying on the modern side, so we use MiniMessage as our primary way to color messages and give them actions!

## Honorable Mentions:
Mentioning system:

![2024-10-15_17 22 56](https://github.com/user-attachments/assets/d717c47b-91e8-4fa8-b5dc-6d465be1eb2b)

Messaging system (/message, /reply) with extensive API.

Chat formatting (LuckPerms, Vault, and even no-permissions plugin chat formatting)

Maven:

```xml
<repositories>
    <repository>
        <id>foxikle-flameyos</id>
        <name>Foxikle's Repository</name>
        <url>https://repo.foxikle.dev/flameyos</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>me.flame.communication</groupId>
        <artifactId>EnhancedCommunication</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

Gradle (Kotlin):

```kt
repositories {
    mavenCentral()
    maven("
"https://repo.foxikle.dev/flameyos")

}

dependencies {
    implementation("me.flame.communication:EnhancedCommunication:1.0.0")
}
```

You can ask for help in my [discord server](https://discord.gg/Zj6KBS7UwX)

**ChannelsCommunication** and **EnhancedChatModeration** will soon join the party.
