package com.eflake.keyanimengine.keyframe;


public interface IEFAnim {
    void setDuration(long duration);

    void addElement(String key, EFElement element);

    void removeAllElements();

    void removeElementByKey(String key);

    void setViewPort(EFViewPort viewPort);

    void setIsRunning(boolean isRunning);

    boolean isRunning();

    void step(long deltaTime);

    boolean isDone();

    int getViewPortPosX(int currentPosX);

    int getViewPortPosY(int currentPosY);

    void setName(String name);

    String getName();

}
