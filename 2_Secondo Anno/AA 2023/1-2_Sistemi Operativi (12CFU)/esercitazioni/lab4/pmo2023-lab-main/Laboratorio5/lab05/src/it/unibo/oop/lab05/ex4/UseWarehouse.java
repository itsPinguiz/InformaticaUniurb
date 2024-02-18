package it.unibo.oop.lab05.ex4;

import it.unibo.oop.lab05.ex3.Product;
import it.unibo.oop.lab05.ex3.Warehouse;

/**
 *
 */
public final class UseWarehouse {

    private UseWarehouse() {
    }

    /**
     * @param args
     *            unused
     */
    public static void main(final String[] args) {
        /*
         * Provide a different solution to the previous exercise by implementing
         * two classes: ComparableProduct and OrderedWarehouse. Classes from the
         * previous exercise can be extended and reused, but not modified.
         *
         * The new solution must comply to these new requirements:
         *
         * 1) allProducts() returns the products lexicographically ordered. Prefer
         * solutions that reuse existing code.
         *
         * 2) The implementation of Product must implement Comparable<Product>.
         */
        final Product p1 = null;
        final Product p2 = null;
        final Product p3 = null;

        final Warehouse w = null;
        w.addProduct(p3);
        w.addProduct(p1);
        w.addProduct(p2);

        System.out.println(w.allNames());
        System.out.println(w.allProducts());

        // Changes to the returned set should not affect the warehouse
        final var modifiedProductSet = w.allProducts();
        modifiedProductSet.remove(p1);

        System.out.println(w.allNames());
        System.out.println(w.allProducts());
        System.out.println(modifiedProductSet);
    }

}
