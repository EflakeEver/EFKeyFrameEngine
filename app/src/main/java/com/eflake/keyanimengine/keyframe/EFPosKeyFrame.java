package com.eflake.keyanimengine.keyframe;


import android.view.animation.Interpolator;

import com.eflake.keyanimengine.evaluator.IEFFloatEvaluator;

public class EFPosKeyFrame extends EFKeyFrame implements IEFPosKeyFrame {
    public Interpolator interpolator;//差值器
    public IEFFloatEvaluator evaluator;//估值器

    public EFPosKeyFrame(long time, String value) {
        super(time, value);
    }

    @Override
    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    @Override
    public void setEvaluator(IEFFloatEvaluator evaluator) {
        this.evaluator = evaluator;
    }
}
