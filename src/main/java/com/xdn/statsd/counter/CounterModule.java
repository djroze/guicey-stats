/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.counter;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public final class CounterModule extends AbstractModule
{

    @Override
    protected void configure()
    {
        install
            (
                new FactoryModuleBuilder()
                    .implement(DimensionedCounter.class, DimensionedCounterImpl.class)
                    .build(DimensionedCounterFactory.class)
            );
        install
            (
                new FactoryModuleBuilder()
                    .implement(Counter.class, CounterImpl.class)
                    .build(CounterFactory.class)
            );
    }

}
