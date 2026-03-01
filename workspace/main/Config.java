package workspace.main;

import lib.eventEntity.game.bus.onRunnableEventBus;
import lib.eventEntity.game.login.onGameLogoutEventBus;
import lib.thread.EventBus;
import lib.thread.bufferedWindow;
import lib.thread.eventBusListener;
import workspace.main.Entity.loginWindow;

public class Config {
    public static EventBus eventBus = new EventBus();
    public static final String title = "myGame";
    public static final String localPath = System.getProperty("user.dir");
    public static final int FPS = 60;
    public static final int weight = 800;
    public static final int height = 600;

    public static void main(String[] args) {
        //注册你的事件总线
        eventBus.registerEventClass(loginWindow.class);
        //eventBus.registerEventClass(HelloWorld.class);
        //eventBus.registerGlobalValue(GlobalValueBuilder.register(

        //初始化游戏主线程并注册总线监听器
        bufferedWindow mainWindow = new bufferedWindow(title, eventBus, weight, height);
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final boolean[] shutdown = {false};
                            eventBusListener listener = eventBus.registerListener(
                                    onGameLogoutEventBus.class,
                                    new eventBusListener() {
                                        public void onEventCalling(EventBus bus) {
                                            shutdown[0] = true;
                                        }
                                    }
                            );
                            while (!shutdown[0]) {
                                eventBus.callingBus(onRunnableEventBus.class);
                                Thread.sleep(1000 / FPS);
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        thread.run();
    }
}
