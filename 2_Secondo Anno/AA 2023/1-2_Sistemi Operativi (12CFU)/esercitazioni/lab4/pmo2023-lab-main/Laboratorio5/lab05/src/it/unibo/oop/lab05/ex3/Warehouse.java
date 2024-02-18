package it.unibo.oop.lab05.ex3;

import java.util.Set;

/**
 * This interface models a warehouse.
 * 
 */
public interface Warehouse {

    /**
     * Adds a product, assuming there is no other with same name already there.
     * 
     * @param p
     *            the product to add
     */
    void addProduct(Product p);

    /**
     * This method allows to get all the registered product names. It can be
     * used safely: a change in the returned {@link Set} will not be reflected
     * in the warehouse (it creates a defensive copy).
     * 
     * @return a collection of all registered products names.
     */
    Set<String> allNames();

    /**
     * This method allows to get all the registered products. It can be used
     * safely: a change in the returned {@link Set} will not be reflected in the
     * warehouse (it creates a defensive copy).
     * 
     * @return a collection of all registered products.
     */
    Set<Product> allProducts();

    /**
     * This method checks whether a product is stored in the {@link Warehouse}.
     * It must run in constant time.
     * 
     * @param p
     *            the product
     * @return true if the {@link Warehouse} contains the {@link Product}.
     */
    boolean containsProduct(Product p);

    /**
     * Given a product, returns its quantity in stock.
     * 
     * @param name
     *            the product's name
     * @return the amount of the product with that name, or -1 if it is not
     *         there
     */
    double getQuantity(String name);

}
