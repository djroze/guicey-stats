/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.counter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.xdn.statsd.Bucket;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

final class DimensionedCounterImpl implements DimensionedCounter
{

    @NotNull
    private final Bucket _parentBucket;
    @NotNull
    private final Long _flushIntervalMs;
    @NotNull
    private final CounterFactory _counterFactory;

    @NotNull
    private final Map<Object, Counter> _statsDCountersForKeys = new HashMap<Object, Counter>();

    @Inject
    DimensionedCounterImpl
        (
            @NotNull @Assisted Bucket parentBucket,
            @NotNull @Assisted Long flushIntervalMs,
            @NotNull CounterFactory counterFactory
        )
    {
        _parentBucket = parentBucket;
        _counterFactory = counterFactory;
        _flushIntervalMs = flushIntervalMs;
    }

    public void increment(@NotNull String key)
    {
        getStatsDCounter(key).increment();
    }

    public void increment(@NotNull String key, long count)
    {
        getStatsDCounter(key).increment(count);
    }

    private synchronized Counter getStatsDCounter(String key)
    {
        if (!_statsDCountersForKeys.containsKey(key))
        {
            _statsDCountersForKeys.put
                (
                    key,
                    _counterFactory.getStatsDCounter
                        (
                            _parentBucket,
                            key,
                            _flushIntervalMs
                        )
                );
        }
        return _statsDCountersForKeys.get(key);
    }

}
