package lib.thread;

import lib.buffered.Sprite;
import lib.eventEntity.game.bus.onRunnableEventBus;
import lib.eventEntity.game.login.onGameLogoutEventBus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Render extends JPanel {
    public EventBus bus;

    public Render(EventBus bus){
        this.bus = bus;
        this.bus.registerEventClass(this);
    }

    public void paints(EventBus bus){

    }

    //请不要重写这个方法，除非你自己知道你在干什么
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Sprite[] objects = this.bus.activeObjects.toArray(new Sprite[]{});

        if (!(objects == null)) {
            for (Sprite object : objects) {
                if (object.display) g2d.drawImage(object.getSpriteIcon().getImage(), object.x, object.y, this);
            }
        }
    }
}
