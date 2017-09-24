package sscherbina.codingtask.checkout;

import org.junit.Assert;
import org.junit.Test;

public class CheckoutTest {

    final Checkout<String> checkout = new Checkout<>(item -> {
        // Just mock-up, real code should look-ups prices from external storage like database
        switch (item) {
            case "A":
                return Price.base(40).addSpecial(3, 70);
            case "B":
                return Price.base(10).addSpecial(2, 15);
            case "C":
                return Price.base(30);
            case "D":
                return Price.base(25);
            default:
                throw new IllegalArgumentException(
                        "No price for item: " + item);
        }
    });

    @Test
    public void testCheckoutEmpty() {
        Assert.assertEquals(
                0
                , checkout.begin()
                        .totalCost());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckoutWrong() {
        Assert.assertEquals(
                0
                , checkout.begin()
                        .addItem("Wrong")
                        .totalCost());
    }

    @Test
    public void testCheckoutA() {
        Assert.assertEquals(
                40/*A*/
                , checkout.begin()
                        .addItem("A")
                        .totalCost());
    }

    @Test
    public void testCheckout2A() {
        Assert.assertEquals(

                40/*A*/
                        + 40/*A*/

                , checkout.begin()

                        .addItem("A")
                        .addItem("A")

                        .totalCost());
    }

    @Test
    public void testCheckout3A() {
        Assert.assertEquals(

                70/*3 for A*/

                , checkout.begin()

                        .addItem("A")
                        .addItem("A")
                        .addItem("A")

                        .totalCost());
    }

    @Test
    public void testCheckout4A() {
        Assert.assertEquals(

                70/*3 for A*/
                        + 40/*A*/

                , checkout.begin()

                        .addItem("A")
                        .addItem("A")
                        .addItem("A")
                        .addItem("A")

                        .totalCost());
    }

    @Test
    public void testCheckoutABCD() {
        Assert.assertEquals(

                40/*A*/
                        + 10/*B*/
                        + 30/*C*/
                        + 25/*D*/

                , checkout.begin()

                        .addItem("A")
                        .addItem("B")
                        .addItem("C")
                        .addItem("D")

                        .totalCost());
    }

    @Test
    public void testCheckout4A4B4C4D() {
        Assert.assertEquals(

                70/*3 for A*/
                        + 40/*A*/
                        + 15/*2 for B*/
                        + 15/*2 for B*/
                        + 30/*C*/ * 4
                        + 25/*A*/ * 4

                , checkout.begin()

                        .addItem("A").addItem("B").addItem("C").addItem("D")
                        .addItem("A").addItem("B").addItem("C").addItem("D")
                        .addItem("A").addItem("B").addItem("C").addItem("D")
                        .addItem("A").addItem("B").addItem("C").addItem("D")

                        .totalCost());
    }

}
