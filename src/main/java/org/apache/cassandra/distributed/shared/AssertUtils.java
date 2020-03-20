package org.apache.cassandra.distributed.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AssertUtils {

    public static void assertRows(Object[][] actual, Object[]... expected)
    {
        assertEquals(rowsNotEqualErrorMessage(actual, expected),
                     expected.length, actual.length);

        for (int i = 0; i < expected.length; i++)
        {
            Object[] expectedRow = expected[i];
            Object[] actualRow = actual[i];
            assertTrue(rowsNotEqualErrorMessage(actual, expected),
                       Arrays.equals(expectedRow, actualRow));
        }
    }

    public static void assertRow(Object[] actual, Object... expected)
    {
        assertTrue(rowNotEqualErrorMessage(actual, expected),
                   Arrays.equals(actual, expected));
    }

    public static void assertRows(Iterator<Object[]> actual, Iterator<Object[]> expected)
    {
        while (actual.hasNext() && expected.hasNext())
            assertRow(actual.next(), expected.next());

        assertTrue("Resultsets have different sizes", actual.hasNext() == expected.hasNext());
    }

    public static void assertRows(Iterator<Object[]> actual, Object[]... expected)
    {
        assertRows(actual, new Iterator<Object[]>() {

            int i = 0;
            @Override
            public boolean hasNext() {
                return i < expected.length;
            }

            @Override
            public Object[] next() {
                return expected[i++];
            }
        });
    }

    public static String rowNotEqualErrorMessage(Object[] actual, Object[] expected)
    {
        return String.format("Expected: %s\nActual:%s\n",
                             Arrays.toString(expected),
                             Arrays.toString(actual));
    }

    public static String rowsNotEqualErrorMessage(Object[][] actual, Object[][] expected)
    {
        return String.format("Expected: %s\nActual: %s\n",
                             rowsToString(expected),
                             rowsToString(actual));
    }

    public static String rowsToString(Object[][] rows)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        boolean isFirst = true;
        for (Object[] row : rows)
        {
            if (isFirst)
                isFirst = false;
            else
                builder.append(",");
            builder.append(Arrays.toString(row));
        }
        builder.append("]");
        return builder.toString();
    }

    public static Object[][] toObjectArray(Iterator<Object[]> iter)
    {
        List<Object[]> res = new ArrayList<>();
        while (iter.hasNext())
            res.add(iter.next());

        return res.toArray(new Object[res.size()][]);
    }

    public static Object[] row(Object... expected)
    {
        return expected;
    }

    public static void assertEquals(String message, long expected, long actual) {
        if (expected != actual)
            fail(message);
    }

    public static void assertNotEquals(String message, long expected, long actual) {
        if (expected == actual)
            fail(message);
    }

    public static void assertNotNull(String message, Object object) {
        if (object == null)
            fail(message);
    }

    public static void assertTrue(String message, boolean condition) {
        if (!condition)
            fail(message);
    }

    public static void assertFalse(String message, boolean condition) {
        if (condition)
            fail(message);
    }


    public static void fail(String message) {
        throw new AssertionError(message);
    }

}