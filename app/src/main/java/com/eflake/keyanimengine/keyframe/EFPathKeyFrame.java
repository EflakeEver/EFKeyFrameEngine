package com.eflake.keyanimengine.keyframe;


import android.view.animation.Interpolator;

import com.eflake.keyanimengine.evaluator.IEFBezierFloatEvaluator;

public class EFPathKeyFrame extends EFKeyFrame implements IEFPathKeyFrame {
    public Interpolator interpolator;//差值器
    //TODO 目前关键帧区间内，只支持二阶贝塞尔曲线（仅一个控制点），后续待扩展
    public IEFBezierFloatEvaluator evaluator;//估值器
    public String control;//控制点

    public EFPathKeyFrame(long time, String value, String controlPointValue) {
        super(time, value);
        this.control = controlPointValue;
    }

    @Override
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public void setEvaluator(IEFBezierFloatEvaluator evaluator) {
        this.evaluator = evaluator;
    }
}
