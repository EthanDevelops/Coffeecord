package xyz.deftu.coffeecord.requests;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import xyz.deftu.coffeecord.CoffeecordInfo;

import java.io.IOException;

public class CoffeecordRequestInterceptor implements Interceptor {

    @NotNull
    public Response intercept(@NotNull Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder().addHeader("User-Agent", "DiscordBot (" + CoffeecordInfo.GITHUB + ", " + CoffeecordInfo.VERSION + ")").build());
    }

}