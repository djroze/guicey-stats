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
import com.xdn.statsd.BucketFactory;
import com.xdn.statsd.StatsDClient;
import com.xdn.statsd.flush.Flusher;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class TimerImpl implements Timer
{

    @NotNull
    private final Bucket _bucket;
    @NotNull
    private final StatsDClient _client;
    private final List<String> _backlog;

    @Inject
    TimerImpl
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
            throw new IllegalArgumentException("Flush period must be >= 0");
        }

        _bucket = bucketFactory.getSubBucket(parentBucket, name);
        _client = client;

        if (flushPeriodMillis > 0)
        {
            flusher.register(this, flushPeriodMillis);
            _backlog = new ArrayList<String>();
        }
        else
        {
            _backlog = null;
        }
    }

    public synchronized void report(long value)
    {
        String data = value + "|ms";
        if (_backlog == null)
        {
            send(data);
        }
        else
        {
            _backlog.add(data);
        }
    }

    public synchronized void flush()
    {
        if (_backlog != null)
        {
            final Iterator<String> backlogIt = _backlog.iterator();
            while (backlogIt.hasNext())
            {
                final String data = backlogIt.next();
                send(data);
                backlogIt.remove();
            }
        }
    }

    private synchronized void send(String data)
    {
        _client.send(_bucket, data);
    }

}
