package com.eflake.keyanimengine.action;


import com.eflake.keyanimengine.base.EFNode;

public interface IEFAction {
    boolean isDone();
    void startWithTarget(EFNode targetNode);
    void stop();
    void update(float time);
    EFNode getTarget();
    void setTarget();
    String getTag();
    void setTag(String tag);
}
