/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import javax.validation.constraints.NotNull;

final class BucketFactoryImpl implements BucketFactory
{

    @NotNull
    public Bucket getStaticBucket(@NotNull String[] segments)
    {
        return new StaticBucket(segments);
    }

    @NotNull
    public Bucket getSubBucket(@NotNull Bucket parent, @NotNull String name)
    {
        return new SubBucket(parent, name);
    }

}
