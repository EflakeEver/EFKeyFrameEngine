package com.eflake.keyanimengine.scheduler;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.eflake.keyanimengine.base.EFNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
*
* */
public class EFScheduler implements IEFScheduler {

    private HashMap<String, EFNode> observers = new HashMap<>();

    private EFScheduler() {
    }

    private static EFScheduler mInstance;

    public static EFScheduler getInstance() {
        if (mInstance == null) {
            mInstance = new EFScheduler();
        }
        return mInstance;
    }

    @Override
    public boolean addTarget(EFNode observer) {
        boolean result;
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
    public boolean removeTarget(EFNode node) {
        if (node == null) {
            return false;
        }

        observers.remove(node.hashCode());
        return true;
    }

    /*
    * Canvas刷新回调用此方法,通知需要显示的所有EFNode对象
    * */
    public void update(int deltaTime, Canvas canvas, Paint defaultPaint) {
        long oldTime = System.currentTimeMillis();
        Iterator iterator = observers.entrySet().iterator();
        //TODO 如果耗时过长,需要把这个时间也考虑加进入deltaTime中
        while (iterator.hasNext()) {
            Map.Entry<String, EFNode> entry = (Map.Entry<String, EFNode>) iterator.next();
            String key = entry.getKey();
            EFNode node = entry.getValue();
            if (!node.isPaused()) {
                node.update(deltaTime,canvas,defaultPaint);
                //TODO 节点的子控件,也需要支持更新

            }
        }
//        Log.e("eflake", "**update cost = " + String.valueOf(System.currentTimeMillis() - oldTime));
    }

}
