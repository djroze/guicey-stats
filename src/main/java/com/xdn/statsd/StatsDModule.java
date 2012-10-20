/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.xdn.statsd.StatsDClientImpl;
import com.xdn.statsd.counter.CounterModule;
import com.xdn.statsd.flush.FlushModule;
import com.xdn.statsd.timer.TimerModule;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class StatsDModule extends AbstractModule
{

    @NotNull
    private static final String SEGMENT_SEPARATOR = ".";
    @NotNull
    private static final String VALUE_SEPARATOR = ":";

    @Override
    protected void configure()
    {
        bind(BucketFactory.class).to(BucketFactoryImpl.class);
        bind(DatagramPacketFactory.class).to(DatagramPacketFactoryImpl.class);
        bind(StatsDClient.class).to(StatsDClientImpl.class);

        bind(String.class).annotatedWith(SegmentSeparator.class).toInstance(SEGMENT_SEPARATOR);
        bind(String.class).annotatedWith(ValueSeparator.class).toInstance(VALUE_SEPARATOR);

        install(new CounterModule());
        install(new FlushModule());
        install(new TimerModule());
    }

    @Provides
    @NotNull
    @StatsD
    InetAddress provideInetAddress(@NotNull @Hostname String hostname) throws UnknownHostException
    {
        return InetAddress.getByName(hostname);
    }

    @Provides
    @NotNull
    Bucket provideBucket
        (
            @NotNull BucketFactory bucketFactory,
            @NotNull @RootPath String[] rootPathEntries
        )
    {
        return bucketFactory.getStaticBucket(rootPathEntries);
    }

}
