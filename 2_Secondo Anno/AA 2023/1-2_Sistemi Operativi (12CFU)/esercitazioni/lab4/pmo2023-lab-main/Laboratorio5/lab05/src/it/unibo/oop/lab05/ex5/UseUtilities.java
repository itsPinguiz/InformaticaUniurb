package it.unibo.oop.lab05.ex5;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Simple the class to test {it.unibo.oop.lab05.Utilities} class.
 */
public final class UseUtilities {

    private UseUtilities() { }

    /**
     * @param s
     *            unused
     */
    public static void main(final String[] s) {
        final var setA = Set.of("a", "b", "c", "d");
        final var setB = Set.of("c", "d", "e");

        assertEquals(Set.of("a", "b", "c", "d", "e"), Utilities.setUnion(setA, setB));
        assertEquals(Set.of("c", "d"), Utilities.setIntersection(setA, setB));
        assertEquals(Set.of("a", "b", "e"), Utilities.setSymmetricDifference(setA, setB));

        for (int i = 0; i < 10; i++) {
            System.out.println("Random-extracting: " + Utilities.getRandomElement(setA));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("Random-extracting: " + Utilities.getRandomPair(setA, setB));
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (expected.equals(actual)) {
            System.out.println("Ok!");
        } else {
            System.err.println("ERROR: expected " + expected + " but got " + actual);
        }
    }
}
