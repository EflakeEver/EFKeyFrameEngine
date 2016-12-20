package com.eflake.keyanimengine.evaluator;


public class EFKeepEvaluator implements IEFFloatEvaluator {
    @Override
    public float evaluate(float fraction, float startValue, float endValue) {
        return startValue + (endValue - startValue) * 1.0f;
    }
}
