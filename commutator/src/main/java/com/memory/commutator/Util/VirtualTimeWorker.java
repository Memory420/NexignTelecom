package com.memory.commutator.Util;

import java.time.LocalDateTime;

/**
 * Метод onTimeWork для обработки тиков виртуального времени.
 */
public interface VirtualTimeWorker {
    void onTimeWork(LocalDateTime now);
}
