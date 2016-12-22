package com.eflake.keyanimengine.evaluator;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/*
*  贝塞尔曲线计算公式
*  默认为一阶，
*  一阶：b(t) = (1-t)p0+tp1
*  二阶：b(t) = (1-t)²p0+2t(1-t)p1+t²p2
*  三阶：b(t) = (1-t)³p0+3(1-t)²tp1+3(1-t)t²p2+t³p3
*
*  n阶计算公式：
*          n
*  b(t) =  ∑ pi
*         t=0
* */
public class EFBezierSuperEvaluator implements IEFBezierFloatEvaluator {

    public List<PointF> controlPointList = new ArrayList<>();

    @Override
    public float evaluate(float fraction, float startValue, float endValue, int type) {
        float leftFraction = 1.0f - fraction;
        float result = 0.0f;
        switch (controlPointList.size()) {
            case 0:
                //一阶
                result = leftFraction * startValue + fraction * endValue;
                break;
            case 1:
                //二阶
                if (type == IEFBezierFloatEvaluator.TYPE_X) {
                    result = leftFraction * leftFraction * startValue +
                            2 * fraction * leftFraction * controlPointList.get(0).x +
                            fraction * fraction * endValue;
                } else {
                    result = leftFraction * leftFraction * startValue +
                            2 * fraction * leftFraction * controlPointList.get(0).y +
                            fraction * fraction * endValue;
                }

                break;
            case 2:
                //三阶
                if (type == IEFBezierFloatEvaluator.TYPE_X) {
                    result = leftFraction * leftFraction * leftFraction * startValue
                            + 3 * leftFraction * leftFraction * fraction * controlPointList.get(0).x
                            + 3 * leftFraction * fraction * fraction * controlPointList.get(1).x
                            + fraction * fraction * fraction * endValue;
                } else {
                    result = leftFraction * leftFraction * leftFraction * startValue
                            + 3 * leftFraction * leftFraction * fraction * controlPointList.get(0).y
                            + 3 * leftFraction * fraction * fraction * controlPointList.get(1).y
                            + fraction * fraction * fraction * endValue;
                }

                break;
        }
        return result;
    }


    public void initControlPointList(float controlPointX, float controlPointY) {
        resetList();
        controlPointList.add(new PointF(controlPointX, controlPointY));
    }

    public void initControlPointList(float controlPointX, float controlPointY, float anotherControlPointX, float anotherControlPointY) {
        resetList();
        controlPointList.add(new PointF(controlPointX, controlPointY));
        controlPointList.add(new PointF(anotherControlPointX, anotherControlPointY));
    }

    private void resetList() {
        if (controlPointList.size() > 0) {
            controlPointList.clear();
        }
    }

//    @Override
//    public float evaluate(float fraction, float startValue, float controlValue, float endValue) {
//        float leftFraction = 1.0f - fraction;
//        return leftFraction * leftFraction * startValue +
//                2 * fraction * leftFraction * controlValue +
//                fraction * fraction * endValue;
//    }

}
