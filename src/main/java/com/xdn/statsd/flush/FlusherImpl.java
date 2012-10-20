/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.flush;

import com.google.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.Timer;

final class FlusherImpl implements Flusher
{

    @NotNull
    private final Timer _flushTimer;
    @NotNull
    private final FlushTimerTaskFactory _flushTimerTaskFactory;

    @Inject
    FlusherImpl
        (
            @NotNull @Flush Timer flushTimer,
            @NotNull FlushTimerTaskFactory flushTimerTaskFactory
        )
    {
        _flushTimer = flushTimer;
        _flushTimerTaskFactory = flushTimerTaskFactory;
    }

    public void register(@NotNull Flushable flushable, @NotNull Long flushPeriodMillis)
    {
        _flushTimer.schedule
            (
                _flushTimerTaskFactory.getFlushTimerTask(flushable),
                0,
                flushPeriodMillis
            );
    }

}
