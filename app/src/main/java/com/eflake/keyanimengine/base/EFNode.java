package com.eflake.keyanimengine.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类,每个节点拥有自己的坐标变换信息transform,可以拥有子元素child
 * 任何对父节点的坐标变换,都将影响到所有子节点
 * 一个子节点,只能拥有一个父节点
 */
public class EFNode implements IEFNode {
    public EFNode mParentNode;
    public List<EFNode> children = new ArrayList<>();
    public String mTag;//标签
    public float mCenterPosX;//X轴中心坐标
    public float mCenterPosY;//Y轴中心坐标
    public float mStartPosX;//X轴左上角坐标
    public float mStartPosY;//Y轴左上角坐标
    public int mWidth;//宽度
    public int mHeight;//高度
    public float mRotation;//旋转角度
    public float mAlpha = 100.0f;//不透明度
    public float mScaleX = 1.0f;//缩放比例
    public float mScaleY = 1.0f;//缩放比例
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

    protected void setCenterPosX(float centerPosX) {
        mCenterPosX = centerPosX;
        mStartPosX = mCenterPosX - mWidth / 2;
    }

    protected void setCenterPosY(float centerPosY) {
        mCenterPosY = centerPosY;
        mStartPosY = mCenterPosY - mHeight / 2;
    }

    /*
    * X相对坐标转绝对坐标
    * */
    protected void convertRelativeToRealPosX() {
        //如果在设置之前的计算，全是基于相对坐标，那么最后在设置真正坐标时，需要把相对坐标转化成绝对坐标
        if (getParentNode() != null) {
            mCenterPosX = mCenterPosX + getParentNode().mCenterPosX;
            mStartPosX = mCenterPosX - mWidth / 2;
        }
    }

    /*
    * Y相对坐标转绝对坐标
    * */
    protected void convertRelativeToRealPosY() {
        //如果在设置之前的计算，全是基于相对坐标，那么最后在设置真正坐标时，需要把相对坐标转化成绝对坐标
        if (getParentNode() != null) {
            mCenterPosY = mCenterPosY + getParentNode().mCenterPosY;
            mStartPosY = mCenterPosY - mHeight / 2;
        }
    }

    /*
    * 如果计算的坐标是相对坐标，转换成绝对坐标
    * */
    protected void convertRelativeToRealPos() {
        convertRelativeToRealPosX();
        convertRelativeToRealPosY();
    }

    /*
    * 如果是相对Scale，转换为绝对Scale
    * */
    /*protected void convertRelativeScaleToRealScale() {
        convertRelativeScaleToRealScaleX();
        convertRelativeScaleToRealScaleY();
    }

    private void convertRelativeScaleToRealScaleX() {
        if (getParentNode() != null) {
            mScaleX = mScaleX + getParentNode().mScaleX;
        }
    }

    private void convertRelativeScaleToRealScaleY() {
        if (getParentNode() != null) {
            mScaleY = mScaleY + getParentNode().mScaleY;
        }
    }*/

    protected void setRotation(float rotation) {
        mRotation = rotation;
    }

    protected void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public void setScaleX(float scaleX) {
        mScaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        mScaleY = scaleY;
    }

    public EFNode getParentNode() {
        return mParentNode;
    }

    public void setParentNode(EFNode parentNode) {
        //设置父节点之前，先将设置坐标为父节点中心位置
        setCenterPosX(parentNode.mCenterPosX);
        setCenterPosY(parentNode.mCenterPosY);
        mParentNode = parentNode;
    }
}
