package sscherbina.codingtask.checkout;

/**
 * Price represented as function from quantity to its cost.
 * <p>
 * Prices and quantities are represented as integers in atomic units,
 * like cents for price and units or grams for quantities.
 * User interface representation should convert them from/to conventional decimal values,
 * dollars for prices and kilograms for quantites.
 */
@FunctionalInterface
interface Price {
    /**
     * Computes cost for quantity of an item.
     *
     * @param qty Quantity of an item.
     * @return Cost of quantity of an item.
     */
    long cost(int qty);

    /**
     * Constructors of base price for one item unit.
     *
     * @param price Base price of one unit.
     * @return Cost of quantity by base price.
     */
    static Price base(final int price) {
        return qty -> qty * price;
    }

    /**
     * Builder for special price of units group -  "buy n of them, and they’ll cost you y cents".
     * Special prices should be added in increasing order of quantities - from smaller to bigger.
     *
     * @param forQty Quantity of special price.
     * @param price  Special price for quantity above.
     * @return
     */
    default Price addSpecial(final int forQty, final int price) {
        return qty -> (price * (qty / forQty)) + this.cost(qty % forQty);
    }
}
