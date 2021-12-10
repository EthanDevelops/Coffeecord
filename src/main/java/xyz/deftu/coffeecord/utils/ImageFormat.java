package xyz.deftu.coffeecord.utils;

public enum ImageFormat {

    PNG("png"),
    JPEG("jpg", "image/jpeg"),
    GIF("gif");

    private final String extension;
    private final String contentType;

    ImageFormat(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    ImageFormat(String extension) {
        this(extension, "image/" + extension);
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

}