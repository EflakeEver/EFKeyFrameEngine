package com.eflake.keyanimengine.scheduler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.eflake.keyanimengine.keyframe.EFAnimManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
*
* */
public class EFScheduler implements IEFScheduler {

    public static final String TAG = EFScheduler.class.getSimpleName();
    private HashMap<String, IEFUpdate> observers = new HashMap<>();

    private EFScheduler() {
    }

    private static EFScheduler mInstance;

    public static EFScheduler getInstance() {
        if (mInstance == null) {
            mInstance = new EFScheduler();
            init();
        }
        return mInstance;
    }

    private static void init() {
        //初始化时,把AnimManager添加为观察者
        mInstance.addTarget(EFAnimManager.getInstance());
    }

    @Override
    public boolean addTarget(IEFUpdate observer) {
        if (observer == null) {
            return false;
        }

        if (observers.containsKey(observer)) {
            return false;
        }

        observers.put(observer.hashCode() + "", observer);

        return true;
    }

    @Override
    public boolean removeTarget(IEFUpdate observer) {
        if (observer == null) {
            return false;
        }

        if (!observers.containsKey(observer)) {
            return false;
        }

        observers.remove(observer.hashCode());
        return true;
    }

    /*
    * Canvas刷新回调用此方法,通知需要显示的所有EFNode对象
    * */
    public void update(int deltaTime, Canvas canvas, Paint defaultPaint) {
        long oldUpdateTime = System.currentTimeMillis();
        Iterator updateIterator = observers.entrySet().iterator();
        //TODO 如果耗时过长,需要把这个时间也考虑加进入deltaTime中
        while (updateIterator.hasNext()) {
            Map.Entry<String, IEFUpdate> entry = (Map.Entry<String, IEFUpdate>) updateIterator.next();
            String key = entry.getKey();
            IEFUpdate observer = entry.getValue();
            if (!observer.isPaused()) {
                observer.update(deltaTime);
            }
        }
        Log.e(TAG, "update cost time = " + String.valueOf(System.currentTimeMillis() - oldUpdateTime));
        long oldDrawTime = System.currentTimeMillis();
        Iterator drawIterator = observers.entrySet().iterator();
        while (drawIterator.hasNext()) {
            Map.Entry<String, IEFUpdate> entry = (Map.Entry<String, IEFUpdate>) drawIterator.next();
            IEFUpdate observer = entry.getValue();
            if (!observer.isPaused()) {
                observer.draw(canvas, defaultPaint);
            }
        }
        Log.e(TAG, "draw cost time = " + String.valueOf(System.currentTimeMillis() - oldUpdateTime));
    }
    
}
