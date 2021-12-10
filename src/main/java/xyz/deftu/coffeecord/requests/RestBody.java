package xyz.deftu.coffeecord.requests;

import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import xyz.deftu.coffeecord.DiscordClient;

import java.nio.charset.StandardCharsets;

public class RestBody extends RequestBody {

    private final String content;

    public RestBody(String content) {
        this.content = content;
    }

    public RestBody() {
        this(null);
    }

    public RestBody(DiscordClient client, JsonObject content) {
        this(client.getGson().toJson(content));
    }

    public MediaType contentType() {
        return MediaType.get("application/json");
    }

    public void writeTo(@NotNull BufferedSink bufferedSink) {
        Buffer buffer = bufferedSink.getBuffer();
        if (content != null) {
            buffer.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }

}