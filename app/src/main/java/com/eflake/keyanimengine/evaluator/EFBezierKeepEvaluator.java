package com.eflake.keyanimengine.evaluator;

/*
*  二阶贝塞尔曲线计算公式：b(t) = (1-t)²p0+2t(1-t)p1+t²p2
* */
public class EFBezierKeepEvaluator implements IEFBezierFloatEvaluator {

    @Override
    public float evaluate(float fraction, float startValue, float controlValue, float endValue) {
        return endValue;
    }

}
