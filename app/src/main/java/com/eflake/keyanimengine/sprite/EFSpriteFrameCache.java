package com.eflake.keyanimengine.sprite;


import android.content.Context;

import com.eflake.keyanimengine.utils.LoadImgUtils;

import java.util.HashMap;

public class EFSpriteFrameCache {

    public static final String KSYUN_FRAMES = "ksyun_frames";
    private HashMap<String, EFSpriteFrame> frames = new HashMap<>();

    private EFSpriteFrameCache() {
    }

    private static EFSpriteFrameCache mInstance;

    public static EFSpriteFrameCache getInstance() {
        if (mInstance == null) {
            mInstance = new EFSpriteFrameCache();
        }
        return mInstance;
    }

    public void addSpriteFrameByName(Context context, String frameName) {
        EFSpriteFrame spriteFrame = new EFSpriteFrame(context, getFramePathByName(frameName), 0, 0);
        if (!frames.containsKey(frameName)) {
            frames.put(frameName, spriteFrame);
        }
    }

    public EFSpriteFrame getSpriteFrameByName(String fireworkName) {
        if (frames.containsKey(fireworkName)) {
            return frames.get(fireworkName);
        } else {
            return null;
        }
    }

    private String getFramePathByName(String frameName) {
        return LoadImgUtils.getFrameAnimPath(frameName);
    }

}