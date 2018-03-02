package com.github.nscuro.bttc.pricing;

/**
 * {@link Pricing} is used to indicate the price of a given amount of items and
 * is typically the result of an application of {@link PricingRule}.
 * <p>
 * A {@link Pricing} however does not indicate which item has been priced
 * and thus explicitly relies on being used in a context that can receive this
 * information from elsewhere.
 */
public final class Pricing {

    private final int subTotal;

    private final int affectedQuantity;

    /**
     * @param subTotal         The subtotal in the currencies smallest unit
     * @param affectedQuantity Amount of items affected by the given subtotal
     */
    public Pricing(final int subTotal, final int affectedQuantity) {
        this.subTotal = subTotal;
        this.affectedQuantity = affectedQuantity;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public int getAffectedQuantity() {
        return affectedQuantity;
    }

}
