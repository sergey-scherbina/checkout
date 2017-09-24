package sscherbina.codingtask.checkout;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Checkout total price stateful computation.
 * <p>
 * Requires provided external storage for prices look-ups.
 * <p>
 * Every accumulation/computation "transaction" should starts from begin() method call.
 * <p>
 * Provides "fluent"-like DSL API chains.
 *
 * @param <Item> A type of items representation.
 */
public class Checkout<Item> {

    /**
     * Current state of items quantities.
     */
    private final Map<Item, Integer> items = new HashMap<>();

    /**
     * External look-up mechanism for prices.
     */
    private Function<Item, Price> priceList;

    /**
     * Checkout mechanism constructor.
     *
     * @param priceList External look-up mechanism for prices.
     */
    public Checkout(final Function<Item, Price> priceList) {
        this.priceList = priceList;
    }

    /**
     * Resets (clears) state of a checkout mechanism.
     *
     * @return itself for fluency
     */
    public synchronized Checkout<Item> begin() {
        items.clear();
        return this;
    }

    /**
     * Adds items one by one.
     *
     * @param item an item
     * @return itself for fluency
     */
    public synchronized Checkout<Item> addItem(final Item item) {
        items.merge(item, 1, Integer::sum);
        return this;
    }

    /**
     * Computes total cost of added items.
     *
     * @return total cost of added items.
     */
    public synchronized long totalCost() {
        return items.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .mapToLong(entry -> priceList.apply(entry.getKey())
                        .cost(entry.getValue()))
                .sum();
    }
}