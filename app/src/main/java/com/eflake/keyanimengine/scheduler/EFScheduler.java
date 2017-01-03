package com.eflake.keyanimengine.scheduler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.eflake.keyanimengine.keyframe.EFAnimManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/*
*
* */
public class EFScheduler implements IEFScheduler {

    public static final String TAG = EFScheduler.class.getSimpleName();
    private HashMap<String, IEFUpdate> observers = new LinkedHashMap<>();
    private LinkedList<EFSchedulerChange> observerChanges = new LinkedList<>();

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

        EFSchedulerChange change = new EFSchedulerChange();
        change.setIEFUpdate(observer);
        change.setType(EFSchedulerChange.TYPE_ADD);
        change.setKey(String.valueOf(observer.hashCode()));
        observerChanges.add(change);
        return true;
    }

    @Override
    public boolean removeTarget(IEFUpdate observer) {
        if (observer == null) {
            return false;
        }
        EFSchedulerChange change = new EFSchedulerChange();
        change.setIEFUpdate(observer);
        change.setType(EFSchedulerChange.TYPE_REMOVE);
        change.setKey(String.valueOf(observer.hashCode()));
        observerChanges.add(change);
        return true;
    }

    /*
    * Canvas刷新回调用此方法,通知需要显示的所有EFNode对象
    * */
    public void onCanvasUpdate(int deltaTime, Canvas canvas, Paint defaultPaint) {
//        long oldUpdateTime = System.currentTimeMillis();
        Iterator updateIterator = observers.entrySet().iterator();
        while (updateIterator.hasNext()) {
            Map.Entry<String, IEFUpdate> entry = (Map.Entry<String, IEFUpdate>) updateIterator.next();
            IEFUpdate observer = entry.getValue();
            if (!observer.isPaused()) {
                observer.update(deltaTime);
            }
        }
//        Log.e(TAG, "update cost time = " + String.valueOf(System.currentTimeMillis() - oldUpdateTime));
        long oldDrawTime = System.currentTimeMillis();
        //清屏操作
        defaultPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(defaultPaint);
        defaultPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        Iterator drawIterator = observers.entrySet().iterator();
        while (drawIterator.hasNext()) {
            Map.Entry<String, IEFUpdate> entry = (Map.Entry<String, IEFUpdate>) drawIterator.next();
            IEFUpdate observer = entry.getValue();
            if (!observer.isPaused()) {
                observer.draw(canvas, defaultPaint);
            }
        }
//        Log.e(TAG, "draw cost time = " + String.valueOf(System.currentTimeMillis() - oldUpdateTime));
        long applyChangeTime = System.currentTimeMillis();
        applyObserverChange();
        //        Log.e(TAG, "apply change cost time = " + String.valueOf(System.currentTimeMillis() - applyChangeTime));
    }

    private void applyObserverChange() {
        if (observerChanges.size() > 0) {
            for (int i = 0; i < observerChanges.size(); i++) {
                EFSchedulerChange change = observerChanges.get(i);
                int type = change.getType();
                IEFUpdate observer = change.getIEFUpdate();
                String key = change.getKey();
                if (type == EFSchedulerChange.TYPE_ADD) {
                    if (!observers.containsKey(key)) {
                        observers.put(key, observer);
                    }
                } else if (type == EFSchedulerChange.TYPE_REMOVE) {
                    if (observers.containsKey(key)) {
                        observers.remove(key);
                    }
                }
            }
            observerChanges.clear();
        }
    }

}
