package xyz.deftu.blurp4j.requests;

import xyz.deftu.blurp4j.DiscordClient;

import java.util.function.Consumer;

public interface RestAction<T> {

    DiscordClient getClient();

    void queue(Consumer<? super T> success, Consumer<? super Throwable> failure);
    default void queue() {
        queue(null, null);
    }

    T complete();

}