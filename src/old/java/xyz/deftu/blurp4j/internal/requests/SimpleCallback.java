package xyz.deftu.blurp4j.internal.requests;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.BiConsumer;

public class SimpleCallback implements Callback {

    private final BiConsumer<Call, IOException> failure;
    private final BiConsumer<Call, Response> response;

    public SimpleCallback(BiConsumer<Call, IOException> failure, BiConsumer<Call, Response> response) {
        this.failure = failure;
        this.response = response;
    }

    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        this.failure.accept(call, e);
    }

    public void onResponse(@NotNull Call call, @NotNull Response response) {
        this.response.accept(call, response);
    }

}