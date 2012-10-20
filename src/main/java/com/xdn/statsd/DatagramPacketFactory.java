/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import javax.validation.constraints.NotNull;
import java.net.DatagramPacket;

interface DatagramPacketFactory
{

    DatagramPacket getDatagramPacket(@NotNull byte[] buffer, int bufferLength);

}
