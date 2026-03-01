package build.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class initWorkspace {
    public static final String workspacePath = System.clearProperty("user.dir");
    public static void main(String[] args) {
        try {
            System.out.println("Building workspace...");

            File workspaceFolder = new File(workspacePath + "\\workspace");
            File mainFolder = new File(workspaceFolder + "\\main");
            File sourceFolder = new File(workspaceFolder + "\\source");
            File configFile = new File(mainFolder + "\\Config.java");
            workspaceFolder.mkdir();
            mainFolder.mkdir();
            sourceFolder.mkdir();
            configFile.createNewFile();
            //后期添加：检验自定义workspace构造，支持导入idea

            FileOutputStream fos = new FileOutputStream(configFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(
                    """
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
        //eventBus.registerEventClass(HelloWorld.class);
        //eventBus.registerGlobalValue(GlobalValueBuilder.register(

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

                            """
            );
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
