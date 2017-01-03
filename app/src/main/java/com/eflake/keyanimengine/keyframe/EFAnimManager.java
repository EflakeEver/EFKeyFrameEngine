package com.eflake.keyanimengine.keyframe;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.eflake.keyanimengine.scheduler.EFSchedulerChange;
import com.eflake.keyanimengine.scheduler.IEFUpdate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class EFAnimManager implements IEFAnimManager, IEFUpdate {

    public HashMap<String, EFAnim> mAnims = new HashMap<>();//所有已加载的动画
    private LinkedList<EFAnimChange> animChanges = new LinkedList<>();
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
    public void addAnim(String key, EFAnim anim) {
        EFAnimChange change = new EFAnimChange();
        change.setKey(key);
        change.setType(EFAnimChange.TYPE_ADD);
        change.setAnim(anim);
        animChanges.add(change);
    }

    @Override
    public void removeAnimByKey(String key) {
        EFAnimChange change = new EFAnimChange();
        change.setKey(key);
        change.setType(EFAnimChange.TYPE_REMOVE);
        change.setAnim(null);
        animChanges.add(change);
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
        Iterator<Map.Entry<String, EFAnim>> iterator = mAnims.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EFAnim> entry = iterator.next();
            EFAnim currentAnim = entry.getValue();
            //检查动画是否已经完成,如果完成,则从Map中移除
            if (currentAnim.isDone()) {
                currentAnim.setIsRunning(false);
                //TODO 调用顺序问题
                currentAnim.onAnimDone();
                removeAnimByKey(entry.getKey());
            }
            //如果动画正在进行,则调用step方法
            if (currentAnim.isRunning()) {
                currentAnim.draw(canvas, defaultPaint);
            }
        }
        applyAnimChange();
    }

    private void applyAnimChange() {
        if (animChanges.size() > 0) {
            for (int i = 0; i < animChanges.size(); i++) {
                EFAnimChange change = animChanges.get(i);
                int type = change.getType();
                EFAnim anim = change.getAnim();
                String key = change.getKey();
                if (type == EFSchedulerChange.TYPE_ADD) {
                    if (!mAnims.containsKey(key)) {
                        mAnims.put(key, anim);
                    }
                } else if (type == EFSchedulerChange.TYPE_REMOVE) {
                    if (mAnims.containsKey(key)) {
                        Iterator<Map.Entry<String, EFElement>> iterator = mAnims.get(key).mElements.entrySet().iterator();
                        if (iterator.hasNext()) {
                            Map.Entry<String, EFElement> entry = iterator.next();
                            if (!entry.getValue().mBitmap.isRecycled() && entry.getValue().mBitmap != null) {
                                entry.getValue().mBitmap.recycle();
                                entry.getValue().mBitmap = null;
                            }
                        }
                        mAnims.remove(key);
                    }
                }
            }
            animChanges.clear();
        }
    }

    @Override
    public boolean isPaused() {
        return mIsPaused;
    }
}
