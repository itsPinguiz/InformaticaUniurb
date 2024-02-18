# Collections

Ask the teacher for a discussion about the solution **upon the completion of each single exercise**, unless explicitly differently said in the exercise text.

### Using sets

Observe `it.unibo.oop.lab05.ex1.UseCollection`, and use it as example to complete the exercise in `it.unibo.oop.lab05.ex1.UseSet`.
Before proceeding, please read and make sure you understand the following notes.

##### Important Notes

**Natural Ordering**: Elements in a `TreeSet` are sorted by *natural order*, i.e., the type `X` used within `TreeSet<X>` must implement the interface `Comparable<? super X>`. As such, for instance:

* A `TreeSet<String>` allows new elements to be added by using the `add` method because `String` implements `Comparable<String>`;

* conversely, trying to add elements of type `MyType` to a `TreeSet<MyType>`, in the hypothesis that `MyType` **does not** implement `Comparable<MyType>`, would raise a run-time error of type `ClassCastException` (details on exceptions and error handling will be provided in the next lessons).

**Concurrent modifications**: All the iterators created from instances of the `TreeSet<E>` class (actually, iterators created by any of the collections provided with the JDK standard library) are *fail-fast*.

If the collection instance on which the iterator is operating is modified while an iteration is in progress, the iterator will produce a run-time error (a `ConcurrentModificationException`, to be precise).
The reason is that the iterator may end up in an inconsistent state: it is iterating over a collection, but the collection has changed! What is the next element? And, in case of an unordered collection (such as an `HashSet`), how to make sure we are not visiting the same element again?
Since those error may generate inconsistencies which lead to bugs very hard to spot and reproduce, Java takes the conservative stance of halting when such a behavior is detected.
Note that more "permissive" languages (such as Javascript) don't enforce the same policy, and leave to the implementor the burden of dealing with inconsistent iteration states.

* try to write a `for`-each cycle iterating over `TreeSet` (which, internally, generates and uses an `Iterator`), and within the cycle try to remove an nelement from the `TreeSet`. What happens?

The correct way to remove elements from a collections while iterating it is to use the `remove()` method of `Iterator`.
This requires a reference to the iterator, and, as such, cannot be used from within a `for`-each cycle.


### Custom comparators

* Follow the comments in `it.unibo.oop.lab05.ex2.UseSetWithOrder`, and create a program that sorts a `TreeSet<String>` using a custom `Comparator<String>` (to be created separately, from scratch).

* Refer to the [java documentation](https://docs.oracle.com/en/java/javase/17/docs/api/index.html) to understand how to create a `Comparator` [(interface documentation)](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html).

### Warehouse

* Implement `WharehouseImpl.java` and `ProductImpl.java` by following the contracts described in their relative interfaces.

* Refer to the code documentation for details about the implementation

* Run `UseWarehouse` to test the program


### Warehouse, reuse

* This exercise is an extension of the previous: reuse as much as possible of the previously produced (working) code.

* In order to run tests, complete the `main` method of `UseWarehouse.java` initializing correctly the variables of type `Product` and `Warehouse`.


### Utilities

* Implement the functions in the class `Utilities.java`.

* Verify their behavior by using `UseUtilities`.

For better understanding of the symmetric difference operation between two sets refer to the following diagram (in red the expected result):

![Symmetric difference](https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Venn0110.svg/1024px-Venn0110.svg.png)

