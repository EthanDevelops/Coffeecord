package xyz.deftu.coffeecord.entities.guild;

import xyz.deftu.coffeecord.entities.Permission;

import java.util.List;

public class PermissionOverwrite {

    private final long id;
    private final PermissionOverwriteType type;
    private final List<Permission> allow, deny;

    public PermissionOverwrite(long id, PermissionOverwriteType type, List<Permission> allow, List<Permission> deny) {
        this.id = id;
        this.type = type;
        this.allow = allow;
        this.deny = deny;
    }

}