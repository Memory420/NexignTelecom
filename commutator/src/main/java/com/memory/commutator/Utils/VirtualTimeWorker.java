package com.memory.commutator.Utils;

import java.time.LocalDateTime;

public interface VirtualTimeWorker {
    void onTimeWork(LocalDateTime time);
}
