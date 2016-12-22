package com.eflake.keyanimengine.evaluator;


public interface IEFBezierFloatEvaluator {
    int TYPE_X = 0;
    int TYPE_Y = 1;

    float evaluate(float fraction, float startValue, float endValue, int type);
}
