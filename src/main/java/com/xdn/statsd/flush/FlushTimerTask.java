/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd.flush;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.validation.constraints.NotNull;
import java.util.TimerTask;

final class FlushTimerTask extends TimerTask
{

    @NotNull
    private final Flushable _flushable;

    @Inject
    FlushTimerTask(@Assisted @NotNull Flushable flushable)
    {
        _flushable = flushable;
    }

    @Override
    public void run()
    {
        _flushable.flush();
    }

}
