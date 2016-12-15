package com.eflake.keyanimengine.scheduler;


public interface IEFScheduler {
    boolean addTarget(IEFUpdate observer);
    boolean removeTarget(IEFUpdate observer);
}
