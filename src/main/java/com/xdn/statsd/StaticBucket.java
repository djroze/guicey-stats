/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

final class StaticBucket implements Bucket
{

    @NotNull
    private final String[] _segments;

    StaticBucket(@NotNull String[] segments)
    {
        _segments = Arrays.copyOf(segments, segments.length);
        for (String segment : _segments)
        {
            if (segment == null)
            {
                throw new IllegalArgumentException("Segments must all be non-null");
            }
        }
    }

    @NotNull
    public String[] getSegments()
    {
        return _segments;
    }

    @Override
    public boolean equals(Object o)
    {
        return
            (this == o)
                ||
            (
                !(o == null || getClass() != o.getClass())
                    &&
                Arrays.equals(_segments, ((StaticBucket) o)._segments)
            );
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(_segments);
    }

}
