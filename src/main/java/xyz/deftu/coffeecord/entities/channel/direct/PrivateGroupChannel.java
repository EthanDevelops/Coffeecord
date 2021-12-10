package xyz.deftu.coffeecord.entities.channel.direct;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.coffeecord.requests.types.MessageSendRequest;
import xyz.deftu.coffeecord.requests.types.UserRequest;
import xyz.deftu.coffeecord.utils.ImageFormat;

import java.util.List;

public class PrivateGroupChannel extends BasePrivateChannel {

    public static final String ICON_URL = "avatars/%s/%s.%s";

    private final String name;
    private final String icon;
    private final List<User> users;
    private final long ownerId;
    private User ownerCache;

    public PrivateGroupChannel(DiscordClient client, long id, long lastMessageId, String name, String icon, List<User> users, long ownerId) {
        super(client, id, lastMessageId);
        this.name = name;
        this.icon = icon;
        this.users = users;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getIconRaw() {
        return icon;
    }

    public String getIconUrl(ImageFormat format) {
        return String.format(ICON_URL, getId(), getIconRaw(), format.getExtension());
    }

    public String getIconUrl() {
        return getIconUrl(ImageFormat.PNG);
    }

    public List<User> getUsers() {
        return users;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public User getOwner() {
        if (ownerId == -1)
            return null;
        User value = client.getRestRequester().request(new UserRequest(client, ownerId));
        if (ownerCache == null)
            ownerCache = value;
        return value;
    }

    public User getOwnerCache() {
        if (ownerCache == null)
            return getOwner();
        return ownerCache;
    }

    public final Message send(Message message) {
        return client.getRestRequester().request(new MessageSendRequest(
                client,
                message,
                getId()
        ));
    }

}