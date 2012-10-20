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
import com.xdn.statsd.BucketFactory;
import com.xdn.statsd.StatsDClient;
import com.xdn.statsd.flush.Flusher;

import javax.validation.constraints.NotNull;

final class CounterImpl implements Counter
{

    @NotNull
    private final Bucket _bucket;
    @NotNull
    private final StatsDClient _client;
    private final boolean _buffered;

    private volatile long _count = 0;

    @Inject
    CounterImpl
        (
            @Assisted @NotNull Bucket parentBucket,
            @Assisted @NotNull String name,
            @Assisted @NotNull Long flushPeriodMillis,
            @NotNull BucketFactory bucketFactory,
            @NotNull StatsDClient client,
            @NotNull Flusher flusher
        )
    {
        if (flushPeriodMillis < 0)
        {
            throw new IllegalArgumentException("Flush period must be a positive integer");
        }

        _bucket = bucketFactory.getSubBucket(parentBucket, name);
        _client = client;

        if (flushPeriodMillis > 0)
        {
            flusher.register(this, flushPeriodMillis);
            _buffered = true;
        }
        else
        {
            _buffered = false;
        }
    }

    public synchronized void increment()
    {
        increment(1);
    }

    public synchronized void increment(long count)
    {
        _count += count;
        if (!_buffered)
        {
            flush();
        }
    }

    public synchronized void flush()
    {
        _client.send(_bucket, _count + "|c");
        _count = 0L;
    }

}
