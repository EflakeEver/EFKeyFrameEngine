package com.eflake.keyanimengine.sprite;


import android.content.Context;

public interface IEFSprite {
    void initBitmapPath(Context context, String path);

    void initBitmapRes(Context context, int resId);

    void calculateNormalPosAndRect(int startPosX, int startPosY);

    void calculateAnchorPosAndRect(int anchorPosX, int anchorPosY, int anchorPointType);
}
