package xyz.deftu.coffeecord.utils;

public class ImageHelper {

    public static String withSize(String imageUrl, int size) {
        return imageUrl + "?size=" + size;
    }

}