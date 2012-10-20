/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.counter;

import com.xdn.statsd.flush.Flushable;

public interface Counter extends Flushable
{

    void increment();

    void increment(long count);

}
