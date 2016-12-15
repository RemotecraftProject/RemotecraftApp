package com.zireck.remotecraft.domain.executor;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
  Scheduler getScheduler();
}
