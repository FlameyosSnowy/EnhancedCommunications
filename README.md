# EnhancedCommunications
A next-generation chat plugin for enhancing Minecraft chat.

# DISCLAIMER: ONLY WORKS FOR PAPERMC 1.18+

## Characteristics:
- **High-performance**: Can process big messages and spam without hassle, chat delay, or TPS drops.
- **Versatile API**: The API is very versatile. You can do anything from creating new actions to creating your managers for the plugin.
- **Customizable**: Customize everything in this plugin for your server however you like!
- **PlaceholderAPI**: This plugin supports PlaceholderAPI for practically everything, from actions to chat formatting, etc!
- **MiniMessage**: EnhancedCommunications specializes over other plugins by staying on the modern side, so we use MiniMessage as our primary way to color messages and give them actions!
- **Folia support**: This plugin fully supports Folia.

## Features
- **Mentioning system**: Mention your friends with custom sound and look! (as shown in the next attachment)
- **Automatic broadcasts**: Broadcast any announcement or message with a fixed interval, and center messages and pad them properly as well!
- **Messaging system**: Proper messaging system (/message, /reply, aliases) with extensive API.
- **Extensive formatting**: Format your chat however you like! (LuckPerms, Vault, and even no-permissions plugin chat formatting, though it's recommended you use LuckPerms native API when possible)
- **Chat cooldowns**: Add cooldowns to the chat if you'd like to reduce spam.
- **Word replacements**: You can change words into new words if you'd like, such as censoring swear words
- **Actions system for all**: You can fully customize what is done for broadcasts, formatting, cooldown result, and more!

Example:

```yaml
Order matters, whatever comes first in the list gets done first
# How to use every default actions:
#
# Sound:
# SOUND:<SoundType>:<Volume>:<Pitch>
#
# Action-bar:
# ACTION_BAR:<Message>:[papi]
#
# Title:
# TITLE:<Title>:<Subtitle>:<FadeIn>:<FadeOut>:<Stay>
# The durations are in milliseconds, 1000 milliseconds equals 1 second
on-chat-cooldown-actions: # Executes default actions of "BLOCK"
  - "SOUND:UI_BUTTON_CLICK:1:1"
  - "ACTION_BAR:<gray>You are on cooldown!"
```

## Honorable Mentions:
Mentioning system:

![2024-10-15_17 22 56](https://github.com/user-attachments/assets/d717c47b-91e8-4fa8-b5dc-6d465be1eb2b)

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
        <version>1.2.1</version>
    </dependency>
</dependencies>
```

Gradle (Kotlin):

```kt
repositories {
    mavenCentral()
    maven("https://repo.foxikle.dev/flameyos")

}

dependencies {
    implementation("me.flame.communication:EnhancedCommunication:1.2.1")
}
```

You can ask for help in my [discord server](https://discord.gg/Zj6KBS7UwX)

**ChannelsCommunication** and **EnhancedChatModeration** will soon join the party.
