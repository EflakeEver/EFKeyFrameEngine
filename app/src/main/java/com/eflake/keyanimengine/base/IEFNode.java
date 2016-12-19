package com.eflake.keyanimengine.base;


public interface IEFNode {
    void addChild(EFNode node);

    void removeChild(EFNode node);

    void removeAllChild(EFNode node);

    String getTag();

    void setTag(String tag);

    void pause(boolean isNeedPause);

    boolean isPaused();

}
