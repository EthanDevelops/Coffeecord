package xyz.deftu.blurp4j.interfaces;

public interface IBuilder<T> {
    T build();
    default T create() {
        return build();
    }
}