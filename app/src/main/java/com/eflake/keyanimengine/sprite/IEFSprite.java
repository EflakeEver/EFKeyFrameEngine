package com.eflake.keyanimengine.sprite;


import android.content.Context;

public interface IEFSprite {
    void initBitmapPath(Context context, String path);

    void initBitmapRes(Context context, int resId);

    void calculateNormalPosAndRect(float startPosX, float startPosY);

    void calculateAnchorPosAndRect(float anchorPosX, float anchorPosY, int anchorPointType);
}
