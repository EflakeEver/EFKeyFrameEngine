package com.eflake.keyanimengine.keyframe;


import android.view.animation.Interpolator;

import com.eflake.keyanimengine.evaluator.IEFFloatEvaluator;

public interface IEFPosKeyFrame extends IEFKeyFrame{

    void setInterpolator(Interpolator interpolator);

    void setEvaluator(IEFFloatEvaluator evaluator);
}
