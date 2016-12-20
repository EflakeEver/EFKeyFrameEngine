package com.eflake.keyanimengine.evaluator;


public class EFLinearEvaluator implements IEFFloatEvaluator {

    @Override
    public float evaluate(float fraction, float startValue, float endValue) {
        return startValue + (endValue - startValue) * fraction;
    }

}
