/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.timer;

import javax.validation.constraints.NotNull;

public interface DimensionedTimer
{

    void report(@NotNull String key, long value);

}
