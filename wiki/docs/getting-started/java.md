Using the example below, we can create a `DiscordClient` so our bot can come online, though without functionality.
```java
package com.example.bot;

import xyz.deftu.coffeecord.DiscordClient;

public class ExampleBot {
    private static final ExampleBot INSTANCE = new ExampleBot();

    private DiscordClient client;

    public void start() {
        client = new DiscordClient();
        client.login("token"); /* Replace token with your own token. */
    }

    public static void main(String[] args) {
        INSTANCE.start();
    }

    public static ExampleBot getInstance() {
        return INSTANCE;
    }
}
```