package xyz.deftu.coffeecord;

public class TestBot {

    public void start() {
        DiscordClient client = new DiscordClient();
        client.start("NjkzNjU4MjEyODIxNjMxMDA3.XoARjQ.8BbGUSACk66-6q1X607MmpJWSNg");
    }

    public static void main(String[] args) {
        new TestBot().start();
    }

}