package com.eflake.keyanimengine.scheduler;


public class EFSchedulerChange {
    public static final int TYPE_ADD = 1;
    public static final int TYPE_REMOVE = 2;
    public IEFUpdate mIEFUpdate;
    public String key;
    public int type;

    public IEFUpdate getIEFUpdate() {
        return mIEFUpdate;
    }

    public void setIEFUpdate(IEFUpdate IEFUpdate) {
        mIEFUpdate = IEFUpdate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
