/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.timer;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public final class TimerModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        install
            (
                new FactoryModuleBuilder()
                    .implement(DimensionedTimer.class, DimensionedTimerImpl.class)
                    .build(DimensionedTimerFactory.class)
            );
        install
            (
                new FactoryModuleBuilder()
                    .implement(Timer.class, TimerImpl.class)
                    .build(TimerFactory.class)
            );
    }

}
