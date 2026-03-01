package workspace.main.player;

import lib.buffered.Sprite;
import workspace.main.Config;

import java.io.File;

public class player extends Sprite {
    public player(){
        super();
        this.Image = new File(Config.localPath + "\\source\\images\\player.png");
        this.x = 0;
        this.y = 0;
        this.id = "player";
        System.out.println(this.Image.getAbsolutePath());
        System.out.println(this.Image.exists());
    }
}
