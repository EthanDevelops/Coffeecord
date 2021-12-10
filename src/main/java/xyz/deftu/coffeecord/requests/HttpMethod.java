package xyz.deftu.coffeecord.requests;

import okhttp3.Request;
import okhttp3.RequestBody;

public enum HttpMethod {
    GET,
    POST,

    PUT,
    DELETE,

    PATCH;

    public Request.Builder apply(Request.Builder builder, RequestBody body) {
        return apply(this, builder, body);
    }

    public Request.Builder apply(Request.Builder builder) {
        return apply(builder, null);
    }

    public static Request.Builder apply(HttpMethod method, Request.Builder builder, RequestBody body) {
        switch (method) {
            case GET:
                builder = builder.get();
                break;
            case POST:
                builder = builder.post(body);
                break;
            case PUT:
                builder = builder.put(body);
                break;
            case DELETE:
                builder = builder.delete();
                break;
            case PATCH:
                builder = builder.patch(body);
        }

        return builder;
    }

    public static Request.Builder apply(HttpMethod method, Request.Builder builder) {
        if (method == GET || method == DELETE) {
            return apply(method, builder, null);
        } else {
            throw new IllegalStateException();
        }
    }

}