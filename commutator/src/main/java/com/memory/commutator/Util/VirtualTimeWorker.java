package com.memory.commutator.Util;

import java.time.LocalDateTime;

public interface VirtualTimeWorker {
    void onTimeWork(LocalDateTime now);
}
