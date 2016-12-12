package com.eflake.keyanimengine.scheduler;


import com.eflake.keyanimengine.base.EFNode;

public interface IEFScheduler {
    boolean addTarget(EFNode node);
    boolean removeTarget(EFNode node);
}
