/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.counter;

import com.xdn.statsd.Bucket;

import javax.validation.constraints.NotNull;

public interface CounterFactory
{

    Counter getStatsDCounter(@NotNull Bucket parentBucket, @NotNull String name, @NotNull Long flushIntervalMillis);

}
