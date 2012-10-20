/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import com.google.inject.Inject;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

final class SubBucket implements Bucket
{

    @NotNull
    private final String[] _segments;

    @Inject
    SubBucket(@NotNull Bucket parentBucket, @NotNull String name)
    {
        _segments = Arrays.copyOf(parentBucket.getSegments(), parentBucket.getSegments().length + 1);
        _segments[_segments.length - 1] = name;
    }

    @NotNull
    public String[] getSegments()
    {
        return _segments;
    }

}
