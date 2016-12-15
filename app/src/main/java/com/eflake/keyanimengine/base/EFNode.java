package com.eflake.keyanimengine.base;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类,每个节点拥有自己的坐标变换信息transform,可以拥有子元素child
 * 任何对父节点的坐标变换,都将影响到所有子节点
 * 一个子节点,只能拥有一个父节点
 */
public class EFNode implements IEFNode {
    public List<EFNode> children = new ArrayList<>();
    public String mTag;//标签
    public int mParentPosX;//X轴相对坐标
    public int mParentPosY;//Y轴相对坐标
    public int mCenterPosX;//X轴中心坐标
    public int mCenterPosY;//Y轴中心坐标
    public int mStartPosX;//X轴左上角坐标
    public int mStartPosY;//Y轴左上角坐标
    public int mWidth;//宽度
    public int mHeight;//高度
    public int mRotation;//旋转角度
    public float mAlpha;//不透明度
    public float mScale;//缩放比例
    public boolean mShowing;//是否显示

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
    public String getTag() {
        return mTag;
    }

    @Override
    public void setTag(String tag) {
        mTag = tag;
    }

    @Override
    public void pause(boolean isNeedPause) {
        mShowing = isNeedPause;
    }

    @Override
    public boolean isPaused() {
        return mShowing;
    }

    @Override
    public void update(int deltaTime) {

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
