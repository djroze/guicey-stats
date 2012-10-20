/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import org.testng.annotations.Test;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.testng.Assert.assertEquals;

public class DatagramPacketFactoryImplTest
{

    @Test
    public void testGetDatagramPacket() throws UnknownHostException
    {
        final byte[] buffer = { (byte) 0 };
        final InetAddress inetAddress = InetAddress.getLocalHost();
        final int port = 1;

        final DatagramPacketFactory datagramPacketFactory = new DatagramPacketFactoryImpl(inetAddress, port);
        final DatagramPacket datagramPacket = datagramPacketFactory.getDatagramPacket(buffer, buffer.length);

        assertEquals(datagramPacket.getData(), buffer);
        assertEquals(datagramPacket.getAddress(), inetAddress);
        assertEquals(datagramPacket.getPort(), port);
    }

}
