package com.eflake.keyanimengine.keyframe;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.eflake.keyanimengine.scheduler.IEFUpdate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EFAnimManager implements IEFAnimManager, IEFUpdate {

    public HashMap<String, EFAnim> mAnims = new HashMap<>();//所有已加载的动画
    private boolean mIsPaused;//是否暂停所有动画

    private EFAnimManager() {
    }

    private static EFAnimManager mInstance;

    public static EFAnimManager getInstance() {
        if (mInstance == null) {
            mInstance = new EFAnimManager();
        }
        return mInstance;
    }

    @Override
    public boolean addAnim(String key, EFAnim anim) {
        if (!mAnims.containsKey(key) && anim != null) {
            mAnims.put(key, anim);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAnimByKey(String key) {
        if (mAnims.containsKey(key)) {
            mAnims.remove(key);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void removeAllAnim() {
        mAnims.clear();
    }

    @Override
    public void startAnimByKey(String key) {
        if (!mAnims.containsKey(key)) {
            return;
        }
        EFAnim anim = mAnims.get(key);
        anim.setIsRunning(true);
    }

    @Override
    public void update(int deltaTime) {
        //遍历已添加的Anim
        Iterator<Map.Entry<String, EFAnim>> iterator = mAnims.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EFAnim> entry = iterator.next();
            EFAnim currentAnim = entry.getValue();
            //检查动画是否已经完成,如果完成,则从Map中移除
            if (currentAnim.isDone()) {
                currentAnim.setIsRunning(false);
                removeAnimByKey(entry.getKey());
            }
            //如果动画正在进行,则调用step方法
            if (currentAnim.isRunning()) {
                currentAnim.step(deltaTime);
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint defaultPaint) {
    }

    @Override
    public boolean isPaused() {
        return mIsPaused;
    }
}
