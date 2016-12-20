package com.eflake.keyanimengine.evaluator;

/*
*  二阶贝塞尔曲线计算公式：b(t) = (1-t)²p0+2t(1-t)p1+t²p2
* */
public class EFBezier2Evaluator implements IEFBezierFloatEvaluator {

    @Override
    public float evaluate(float fraction, float startValue, float controlValue, float endValue) {
        float leftFraction = 1.0f - fraction;
        return leftFraction * leftFraction * startValue +
                2 * fraction * leftFraction * controlValue +
                fraction * fraction * endValue;
    }

}
