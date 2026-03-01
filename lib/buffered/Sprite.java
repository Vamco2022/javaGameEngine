package lib.buffered;

import lib.eventEntity.game.bus.onObjectRecycledEventBus;
import lib.thread.EventBus;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Sprite {
    public File Image;
    public ImageIcon icon;
    public int x,y;
    public String id;
    public String uuid;
    public boolean display;

    public Sprite(){
        this.display = true;
        this.uuid = createUUID();
    }

    private String createUUID(){
        return String.valueOf(new Random().nextInt(0,1000000));
    }

    public JLabel getSpriteJLabel(){
        JLabel label = new JLabel(this.icon);
        label.setBounds(this.x, this.y, this.icon.getIconWidth(), this.icon.getIconHeight());
        return label;
    }

    public ImageIcon getSpriteIcon(){
        this.icon = new ImageIcon(this.Image.getAbsolutePath());
        return new ImageIcon(this.Image.getAbsolutePath());
    }

    public void onLoop(EventBus bus){
        //This method is running on every frame!
    }

    public void recycleObject(EventBus bus){
        bus.callingBus(onObjectRecycledEventBus.class);
    }
}
