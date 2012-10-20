/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.timer;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.xdn.statsd.Bucket;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

final class DimensionedTimerImpl implements DimensionedTimer
{

    @NotNull
    private final Bucket _parentBucket;
    @NotNull
    private final Long _flushIntervalMs;
    @NotNull
    private final TimerFactory _timerFactory;

    @NotNull
    private final Map<Object, Timer> _timersForKeys = new HashMap<Object, Timer>();

    @Inject
    DimensionedTimerImpl
        (
            @NotNull @Assisted Bucket parentBucket,
            @NotNull @Assisted Long flushIntervalMs,
            @NotNull TimerFactory timerFactory
        )
    {
        _parentBucket = parentBucket;
        _flushIntervalMs = flushIntervalMs;
        _timerFactory = timerFactory;
    }

    public void report(@NotNull String key, long value)
    {
        getTimer(key).report(value);
    }

    private synchronized Timer getTimer(@NotNull String key)
    {
        if (!_timersForKeys.containsKey(key))
        {
            _timersForKeys.put
                (
                    key,
                    _timerFactory.getStatsDTimer
                        (
                            _parentBucket,
                            key,
                            _flushIntervalMs
                        )
                );
        }
        return _timersForKeys.get(key);
    }

}
