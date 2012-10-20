/*
 * Copyright (c) 2012 XDN Technologies, Inc.
 *
 * This file is part of Guicey Stats, an open source project released under the MIT license.
 * See the file "LICENSE" for details.
 */

package com.xdn.statsd;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StaticBucketTest
{

    @DataProvider(name = "segments")
    Object[][] provideSegments()
    {
        return new Object[][]
            {
                { new String[]{ } },
                { new String[]{ "" } },
                { new String[]{ "a" } },
                { new String[]{ "b.c" } },
                { new String[]{ "d", "e" } }
            };
    }

    @DataProvider(name = "differentSegments")
    Object[][] provideDifferentSegments()
    {
        return new Object[][]
            {
                { new String[]{ }, new String[]{ "" } },
                { new String[]{ }, new String[]{ "a" } },
                { new String[]{ "b", "c" }, new String[]{ "b.c" } },
                { new String[]{ "d.", "e" }, new String[]{ "d.e" } },
                { new String[]{ "f" }, new String[]{ "F" } },
                { new String[]{ "g" }, new String[]{ "H", "i" } }
            };
    }

    @Test
    public void testNullSegments()
    {
        try
        {
            new StaticBucket(new String[]{ null });
            fail();
        }
        catch (IllegalArgumentException e)
        {
        }
        try
        {
            new StaticBucket(new String[]{ "a", null });
            fail();
        }
        catch (IllegalArgumentException e)
        {
        }
        try
        {
            new StaticBucket(new String[]{ null, "b" });
            fail();
        }
        catch (IllegalArgumentException e)
        {
        }
        try
        {
            new StaticBucket(new String[]{ "c", null, "d" });
            fail();
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    @Test(dataProvider = "segments")
    public void testGetSegments(String[] segments)
    {
        assertEquals(new StaticBucket(segments).getSegments(), segments);
    }

    @Test(dataProvider = "segments")
    public void testEquals(String[] segments)
    {
        assertTrue(new StaticBucket(segments).equals(new StaticBucket(segments)));
    }

    @Test(dataProvider = "differentSegments")
    public void testNotEquals(String[] firstSegments, String[] secondSegments)
    {
        assertFalse(new StaticBucket(firstSegments).equals(new StaticBucket(secondSegments)));
    }

    @Test(dataProvider = "segments")
    public void testHashCode(String[] segments)
    {
        assertEquals(new StaticBucket(segments).hashCode(), new StaticBucket(segments).hashCode());
    }

}
