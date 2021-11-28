# Creating
The proper way to create `Message` objects is by using `Message.newBuilder().build()` and using that in any methods where it's required.

# Use-cases
## Internal
The `Message` class is used frequently in the library's internals, especially inside events and classes that extend `BaseChannel`.
## User
Obviously, the `Message` class is used when responding to interactions or `MESSAGE_CREATE` events. Most of the time this class won't be used in other areas unless needed in a specific situation.

# Usage
## Good
```java
@EventSubscriber
public void onMessageCreate(MessageCreateEvent event) {
    if (event.message.getContent().toLowerCase().contains("hello")) {
        event.respond(Message.newBuilder()
                        .content("Hello!")
                        .build());
    }
}
```

## Bad
```java
@EventSubscriber
public void onMessageCreate(MessageCreateEvent event) {
    if (event.message.getContent().toLowerCase().contains("hello")) {
        event.respond(new Message("Hello!", false, new ArrayList(), new ArraList()));
    }
}
```