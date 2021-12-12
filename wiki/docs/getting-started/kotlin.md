The concept for making a `DiscordClient` is very similar using Kotlin, though we can take advantage of the base DSL.
```kotlin
package com.example.bot

import xyz.deftu.coffeecord.*

class ExampleBot {

    val client = discord {
        token = "token" /* Replace token with your own token. */
    }

    fun start() {
        client.login()
    }

}

fun main() {
    ExampleBot().start()
}
```
The base DSL for creating a `DiscordClient` also has other useful fields we can utilize to write less code,
such as the `eventListeners`, `intents`, `onlineStatus` and `activities` fields.

### Base DSL - `eventListeners`
The event listener field is an `Array<Object>` type and takes any object. It's used to register classes as event listeners with
Coffeecord's event bus.

### Base DSL - `intents`
You can use this field to specify the intents used when connecting to the Discord gateway. You can read more about gateway intents in the [Discord docs](https://discord.com/developers/docs/topics/gateway#gateway-intents). The `intents` field is a `List<GatewayIntent>` type, you can set it using Kotlin's `listOf` function like so:
```kotlin
discord {
    intents = listOf(GatewayIntent...)
}
```

### Base DSL - `onlineStatus`
Bots can set their online status just like users can. Though Coffeecord already provides a simple way to do this with it's `Presence` class, you can start your bot with an online status using this field in the base DSL. It's of the `OnlineStatus` enum type and it can be set like so:
```kotlin
discord {
    onlineStatus = OnlineStatus.ONLINE
}
```

### Base DSL - `actvities`
Just like the `onlineStatus` field, this is another thing from the `Presence` class that is easily configurable. You can start your bot with activities using this field in the base DSL. It's a `Array<Activity>` type and it can be set using Kotlin's `arrayOf` function like so:
```kotlin
discord {
    activities = arrayOf(Activity.playing("with Coffeecord."))
}
```