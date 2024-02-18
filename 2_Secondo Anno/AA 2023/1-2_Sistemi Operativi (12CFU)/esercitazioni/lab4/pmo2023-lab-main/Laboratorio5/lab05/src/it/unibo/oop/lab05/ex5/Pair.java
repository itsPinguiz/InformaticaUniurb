package it.unibo.oop.lab05.ex5;

/**
 * A class used to enclose two objects.
 * 
 * Important note: when creating real projects, it is much, much, much better
 * to rely on widely used libraries than to write tons of boilerplate code
 * yourself (or copy the code we provide you). Good implementations of mutable
 * and immutable pairs are available in Apache Commons Lang, Apache Commons
 * Math, and Jool (joo-lambda by Jooq).
 *
 * @param <X>
 *            type of the first {@link Object}
 * @param <Y>
 *            type of the second {@link Object}
 */
public final class Pair<X, Y> {

    private final X first;
    private final Y second;

    /**
     * @param o1
     *            the first object
     * @param o2
     *            the second object
     */
    public Pair(final X o1, final Y o2) {
        this.first = o1;
        this.second = o2;
    }

    /**
     * @return the first object
     */
    public X getFirst() {
        return this.first;
    }

    /**
     * @return the second object
     */
    public Y getSecond() {
        return this.second;
    }

    /**
     * Returns a string representation of the pair.
     * 
     * @return a string representing this pair's state
     */
    public String toString() {
        return "Pair [first=" + first + ", second=" + second + "]";
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        /*
         * Xor of hashes. It is usually a decent hashing method, and it is
         * easier to understand than the auto-generated hashCode() by Eclipse.
         * However, for serious use cases, a good hashing library and algorithm is
         * warmly recommended. For instance, Google Guava provides a number of
         * good and fast hashing algorithms (Murmur 3 is an extremely good
         * solution).
         */
        return first.hashCode() ^ second.hashCode();
    }

    /**
     * Compares this pair to the specified object. The result is true if and
     * only if the argument is not null and is a Pair object containing the same
     * pair of object.
     * 
     * @param obj the object to compare this pair against
     * 
     * @return true if the given pair is equal to this pair
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Pair) {
            final var p = (Pair<?, ?>) obj;
            return areEquals(first, p.first) && areEquals(second, p.second);
        }
        return false;
    }

    private static boolean areEquals(final Object o1, final Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

}
