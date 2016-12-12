package com.eflake.keyanimengine.action;

import com.eflake.keyanimengine.base.EFNode;

/**
 * 作为指派给EFNode的命令存在,在时间段内or瞬时,赋予EFNode一个动作行为
 */
public class EFAction implements IEFAction {
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void startWithTarget(EFNode targetNode) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update(float time) {

    }

    @Override
    public EFNode getTarget() {
        return null;
    }

    @Override
    public void setTarget() {

    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public void setTag(String tag) {

    }
}
