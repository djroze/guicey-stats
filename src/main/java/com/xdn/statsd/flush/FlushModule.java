/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.flush;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import java.util.Timer;

public final class FlushModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind(Flusher.class).to(FlusherImpl.class).in(Singleton.class);
        bind(Timer.class).annotatedWith(Flush.class).toInstance(new Timer(true));

        install
            (
                new FactoryModuleBuilder()
                    .implement(FlushTimerTask.class, FlushTimerTask.class)
                    .build(FlushTimerTaskFactory.class)
            );
    }

}
