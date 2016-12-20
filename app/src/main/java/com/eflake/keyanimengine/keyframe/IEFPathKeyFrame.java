package com.eflake.keyanimengine.keyframe;


import android.view.animation.Interpolator;

import com.eflake.keyanimengine.evaluator.IEFBezierFloatEvaluator;

public interface IEFPathKeyFrame extends IEFKeyFrame{

    void setInterpolator(Interpolator interpolator);

    void setEvaluator(IEFBezierFloatEvaluator evaluator);
}
