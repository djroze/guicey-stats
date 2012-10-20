/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import com.google.inject.Inject;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

final class StatsDClientImpl implements StatsDClient
{

    @NotNull
    private static final Logger LOG = Logger.getLogger(StatsDClientImpl.class);

    @NotNull
    private final String _segmentSeparator;
    @NotNull
    private final String _valueSeparator;
    @NotNull
    private final DatagramSocket _datagramSocket;
    @NotNull
    private final DatagramPacketFactory _datagramPacketFactory;

    @Inject
    StatsDClientImpl
        (
            @NotNull @SegmentSeparator String segmentSeparator,
            @NotNull @ValueSeparator String valueSeparator,
            @NotNull DatagramSocket datagramSocket,
            @NotNull DatagramPacketFactory datagramPacketFactory
        )
    {
        _segmentSeparator = segmentSeparator;
        _valueSeparator = valueSeparator;
        _datagramSocket = datagramSocket;
        _datagramPacketFactory = datagramPacketFactory;
    }

    public void send(@NotNull Bucket bucket, @NotNull String value)
    {
        final StringBuilder messageBuilder = new StringBuilder();
        for (String segment : bucket.getSegments())
        {
            if (messageBuilder.length() > 0)
            {
                messageBuilder.append(_segmentSeparator);
            }
            messageBuilder.append(segment);
        }
        messageBuilder.append(_valueSeparator);
        messageBuilder.append(value);
        final String message = messageBuilder.toString();
        LOG.trace("Sending message to StatsD: " + message);
        final byte[] messageBytes = message.getBytes();
        try
        {
            _datagramSocket.send
                (
                    _datagramPacketFactory.getDatagramPacket
                        (
                            messageBytes,
                            messageBytes.length
                        )
                );
        }
        catch (UnknownHostException e)
        {
            LOG.error("Caught UnknownHostException trying to send StatsD message", e);
        }
        catch (IOException e)
        {
            LOG.error("Caught IOException trying to send StatsD message", e);
        }
    }

}
