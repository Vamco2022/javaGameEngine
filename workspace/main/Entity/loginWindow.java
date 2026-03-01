package workspace.main.Entity;

import lib.eventEntity.game.login.onGameLoginEventBus;
import lib.thread.EventBus;
import workspace.main.player.player;

public class loginWindow {
    @onGameLoginEventBus
    public static void onGameLogin(EventBus bus){
        System.out.println("Hello World");
        bus.registerSprite(new player());
    }
}
