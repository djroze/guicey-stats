/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import com.google.inject.Inject;

import javax.validation.constraints.NotNull;
import java.net.DatagramPacket;
import java.net.InetAddress;

final class DatagramPacketFactoryImpl implements DatagramPacketFactory
{

        @NotNull
        private final InetAddress _inetAddress;
        @NotNull
        private final Integer _port;

        @Inject
        DatagramPacketFactoryImpl(@NotNull @StatsD InetAddress inetAddress, @NotNull @Port Integer port)
        {
                _inetAddress = inetAddress;
                _port = port;
        }

        public DatagramPacket getDatagramPacket(@NotNull byte[] buffer, int bufferLength)
        {
                return new DatagramPacket(buffer, bufferLength, _inetAddress, _port);
        }

}
