package xyz.deftu.coffeecord.entities.guild;

public enum Region {
    AMSTERDAM("amsterdam", "Amsterdam", "\uD83C\uDDF3\uD83C\uDDF1", false),
    BRAZIL("brazil", "Brazil", "\uD83C\uDDE7\uD83C\uDDF7", false),
    EUROPE("europe", "Europe", "\uD83C\uDDEA\uD83C\uDDFA", false),
    EU_CENTRAL("eu-central", "EU Central", "\uD83C\uDDEA\uD83C\uDDFA", false),
    EU_WEST("eu-west", "EU West", "\uD83C\uDDEA\uD83C\uDDFA", false),
    FRANKFURT("frankfurt", "Frankfurt", "\uD83C\uDDE9\uD83C\uDDEA", false),
    HONG_KONG("hongkong", "Hong Kong", "\uD83C\uDDED\uD83C\uDDF0", false),
    JAPAN("japan", "Japan", "\uD83C\uDDEF\uD83C\uDDF5", false),
    SOUTH_KOREA("south-korea", "South Korea", "\uD83C\uDDF0\uD83C\uDDF7", false),
    LONDON("london", "London", "\uD83C\uDDEC\uD83C\uDDE7", false),
    RUSSIA("russia", "Russia", "\uD83C\uDDF7\uD83C\uDDFA", false),
    INDIA("india", "India", "\uD83C\uDDEE\uD83C\uDDF3", false),
    SINGAPORE("singapore", "Singapore", "\uD83C\uDDF8\uD83C\uDDEC", false),
    SOUTH_AFRICA("southafrica", "South Africa", "\uD83C\uDDFF\uD83C\uDDE6", false),
    SYDNEY("sydney", "Sydney", "\uD83C\uDDE6\uD83C\uDDFA", false),
    US_CENTRAL("us-central", "US Central", "\uD83C\uDDFA\uD83C\uDDF8", false),
    US_EAST("us-east", "US East", "\uD83C\uDDFA\uD83C\uDDF8", false),
    US_SOUTH("us-south", "US South", "\uD83C\uDDFA\uD83C\uDDF8", false),
    US_WEST("us-west", "US West", "\uD83C\uDDFA\uD83C\uDDF8", false),

    VIP_AMSTERDAM("vip-amsterdam", "Amsterdam (VIP)", "\uD83C\uDDF3\uD83C\uDDF1", true),
    VIP_BRAZIL("vip-brazil", "Brazil (VIP)", "\uD83C\uDDE7\uD83C\uDDF7", true),
    VIP_EU_CENTRAL("vip-eu-central", "EU Central (VIP)", "\uD83C\uDDEA\uD83C\uDDFA", true),
    VIP_EU_WEST("vip-eu-west", "EU West (VIP)", "\uD83C\uDDEA\uD83C\uDDFA", true),
    VIP_FRANKFURT("vip-frankfurt", "Frankfurt (VIP)", "\uD83C\uDDE9\uD83C\uDDEA", true),
    VIP_JAPAN("vip-japan", "Japan (VIP)", "\uD83C\uDDEF\uD83C\uDDF5", true),
    VIP_SOUTH_KOREA("vip-south-korea", "South Korea (VIP)", "\uD83C\uDDF0\uD83C\uDDF7", true),
    VIP_LONDON("vip-london", "London (VIP)", "\uD83C\uDDEC\uD83C\uDDE7", true),
    VIP_SINGAPORE("vip-singapore", "Singapore (VIP)", "\uD83C\uDDF8\uD83C\uDDEC", true),
    VIP_SOUTH_AFRICA("vip-southafrica", "South Africa (VIP)", "\uD83C\uDDFF\uD83C\uDDE6", true),
    VIP_SYDNEY("vip-sydney", "Sydney (VIP)", "\uD83C\uDDE6\uD83C\uDDFA", true),
    VIP_US_CENTRAL("vip-us-central", "US Central (VIP)", "\uD83C\uDDFA\uD83C\uDDF8", true),
    VIP_US_EAST("vip-us-east", "US East (VIP)", "\uD83C\uDDFA\uD83C\uDDF8", true),
    VIP_US_SOUTH("vip-us-south", "US South (VIP)", "\uD83C\uDDFA\uD83C\uDDF8", true),
    VIP_US_WEST("vip-us-west", "US West (VIP)", "\uD83C\uDDFA\uD83C\uDDF8", true),

    UNKNOWN("", "Unknown Region", null, false),

    AUTOMATIC("automatic", "Automatic", null, false);

    private final String key;
    private final String name;
    private final String emoji;
    private final boolean vip;

    Region(String key, String name, String emoji, boolean vip) {
        this.key = key;
        this.name = name;
        this.emoji = emoji;
        this.vip = vip;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public boolean isVip() {
        return vip;
    }

    public static Region from(String key) {
        for (Region value : values()) {
            if (value.key.equals(key)) {
                return value;
            }
        }

        return UNKNOWN;
    }

}