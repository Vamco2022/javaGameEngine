package lib.thread;

import lib.buffered.Sprite;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class packagedRegisteredMethod{
    Object instance;
    Method method;

    public packagedRegisteredMethod(Object Clazz, Method method){
        this.instance = Clazz;
        this.method = method;
    }
}

public class EventBus {
    Map<Class<?>, ArrayList<packagedRegisteredMethod>> registeredMethods;
    Map<Class<?>, ArrayList<eventBusListener>> registeredEventBusListeners;
    ArrayList<Sprite> activeObjects;

    public EventBus(){
        this.registeredMethods = new HashMap<>();
        this.registeredEventBusListeners = new HashMap<>();
        this.activeObjects = new ArrayList<>();
    }

    public void registerEventClass(Object clazz) {
        Method[] allMethod;
        if (clazz instanceof Class<?>)  allMethod = ((Class<?>) clazz).getDeclaredMethods();
        else allMethod = clazz.getClass().getDeclaredMethods();

        for (Method Voids : allMethod){
            for (Annotation annotation : Voids.getAnnotations()){
                this.addBus(annotation.annotationType(), clazz, Voids);
            }
        }
    }

    public eventBusListener registerListener(Class<?> clazz, eventBusListener base){
        base.listenEvent = clazz;
        this.addListener(clazz, base);
        return base;
    }

    public void registerSprite(Sprite sprite){
        this.activeObjects.add(sprite);
    }

    private void addBus(Class<?> line, Object parents, Method Voids){
        if (this.registeredMethods.get(line) == null){
            ArrayList<packagedRegisteredMethod> voids = new ArrayList<>();
            voids.add(new packagedRegisteredMethod(parents, Voids));
            this.registeredMethods.put(line, voids);
        }else{
            ArrayList<packagedRegisteredMethod> voids = this.registeredMethods.get(line);
            voids.add(new packagedRegisteredMethod(parents, Voids));
            this.registeredMethods.put(line, voids);
        }
    }

    private void addListener(Class<?> line, eventBusListener Voids){
        if (this.registeredEventBusListeners.get(line) == null){
            ArrayList<eventBusListener> voids = new ArrayList<>();
            voids.add(Voids);
            this.registeredEventBusListeners.put(line, voids);
        }else{
            ArrayList<eventBusListener> voids = this.registeredEventBusListeners.get(line);
            voids.add(Voids);
            this.registeredEventBusListeners.put(line, voids);
        }
    }

    public void callingBus(Class<?> bus){
        try {
            ArrayList<packagedRegisteredMethod> methods = this.registeredMethods.get(bus);
            ArrayList<eventBusListener>  eventBusListeners = this.registeredEventBusListeners.get(bus);
            if (!(methods == null)) {
                packagedRegisteredMethod[] voids = methods.toArray(new packagedRegisteredMethod[]{});
                for (packagedRegisteredMethod method : voids) {
                    method.method.setAccessible(true);
                    method.method.invoke(method.instance, this);
                }

            }

            if (!(eventBusListeners == null)){
                eventBusListener[] voids = eventBusListeners.toArray(new eventBusListener[]{});
                for (eventBusListener listener : voids){
                    listener.onEventCalling(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}