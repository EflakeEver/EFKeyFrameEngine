package com.eflake.keyanimengine.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.eflake.keyanimengine.action.EFAction;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类,每个节点拥有自己的坐标变换信息transform,可以拥有子元素child
 * 任何对父节点的坐标变换,都将影响到所有子节点
 * 一个子节点,只能拥有一个父节点
 */
public class EFNode implements IEFNode {
    public List<EFNode> children = new ArrayList<>();
    public String mTag;
    public int mCenterPosY;
    public int mCenterPosX;
    public int mStartPosX = 0;
    public int mStartPosY = 0;
    public int mWidth = 0;
    public int mHeight = 0;
    public Bitmap mBitmap;
    public Matrix mMatrix;
    public boolean mIsNeedPaused;

    @Override
    public void addChild(EFNode node) {

    }

    @Override
    public void removeChild(EFNode node) {

    }

    @Override
    public void removeAllChild(EFNode node) {

    }

    @Override
    public void runAction(EFAction action) {

    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public void setTag(String tag) {
        mTag = tag;
    }

    @Override
    public void update(int deltaTime, Canvas canvas, Paint defaultPaint) {

    }

    @Override
    public void pause(boolean isNeedPause) {
        mIsNeedPaused = isNeedPause;
    }

    @Override
    public boolean isPaused() {
        return mIsNeedPaused;
    }

    @Override
    public void draw(Canvas canvas, Paint defaultPaint) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EFNode efNode = (EFNode) o;

        if (children != null ? !children.equals(efNode.children) : efNode.children != null)
            return false;
        return mTag != null ? mTag.equals(efNode.mTag) : efNode.mTag == null;

    }

    @Override
    public int hashCode() {
        int result = children != null ? children.hashCode() : 0;
        result = 31 * result + (mTag != null ? mTag.hashCode() : 0);
        return result;
    }


}
