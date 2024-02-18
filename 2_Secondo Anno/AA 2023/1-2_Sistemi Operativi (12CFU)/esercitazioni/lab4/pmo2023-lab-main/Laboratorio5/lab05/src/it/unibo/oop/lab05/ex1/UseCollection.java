package it.unibo.oop.lab05.ex1;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Example class using {@link java.util.Collection}.
 *
 */
public final class UseCollection {

    private UseCollection() {
    }

    /**
     * @param args
     *            unused
     */
    public static void main(final String[] args) {

        /*
         * Unless you need to perform specific operations, always use the most
         * generic interface.
         * If we use `var` instead of specifying Collection<Integer> what type
         * will `coll` have?
         */
        final Collection<Integer> coll = new ArrayList<>();
        coll.addAll(Arrays.asList(1, 3, 5, 7, 9, 11)); // var-args
        System.out.println(coll); // [1, 3, 5, 7, 9, 11]

        coll.add(13);
        coll.add(15);
        coll.add(15);
        coll.remove(7);
        System.out.println(coll);

        coll.removeAll(Arrays.asList(11, 13, 15));
        System.out.println(coll);
        coll.retainAll(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(coll);
        System.out.println(coll.contains(3));
        System.out.println(Arrays.toString(coll.toArray()));
    }
}
