/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import javax.validation.constraints.NotNull;

public interface BucketFactory
{

    @NotNull
    Bucket getStaticBucket(@NotNull String[] segments);

    @NotNull
    Bucket getSubBucket(@NotNull Bucket parent, @NotNull String name);

}
