package lib.thread;

import lib.buffered.Sprite;
import lib.eventEntity.game.bus.onRunnableEventBus;
import lib.eventEntity.game.login.onGameLoginCompleteEventBus;
import lib.eventEntity.game.login.onGameLoginEventBus;
import lib.eventEntity.game.login.onGameLogoutEventBus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class bufferedWindow {
    JFrame window;
    EventBus bus;
    Render render;

    public bufferedWindow(String title, EventBus bus, int weight, int height){
        this.bus = bus;
        bus.registerEventClass(this);
        bus.callingBus(onGameLoginEventBus.class);

        //默认渲染器
        this.render = new Render(this.bus);
        window = new JFrame(title);
        window.setSize(weight,height);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setContentPane(this.render);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        bus.callingBus(onGameLogoutEventBus.class);
                        super.windowClosing(e);
                    }
                }
        );
        bus.callingBus(onGameLoginCompleteEventBus.class);
        window.setVisible(true);
    }

    public void setRender(Render render){
        this.render = render;
        this.window.setContentPane(this.render);
    }
}