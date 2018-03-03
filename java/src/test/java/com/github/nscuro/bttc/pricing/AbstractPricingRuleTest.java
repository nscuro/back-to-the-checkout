package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractPricingRuleTest {

    private static final Item TEST_ITEM = new Item("X", 1);

    static PricingRule pricingRule;

    @Test
    @DisplayName("should return an empty list when the provided Item is null")
    void testWithNullItem() {
        assertThat(pricingRule.apply(null, 1)).isEmpty();
    }

    @Test
    @DisplayName("should return an empty list when the provided quantity is null")
    void testWithNullQuantity() {
        assertThat(pricingRule.apply(TEST_ITEM, null)).isEmpty();
    }

    @Test
    @DisplayName("should return an empty list when the provided quantity is zero")
    void testWithZeroQuantity() {
        assertThat(pricingRule.apply(TEST_ITEM, 0)).isEmpty();
    }

    @Test
    @DisplayName("should return an empty list when the provided quantity is negative")
    void testWithNegativeQuantity() {
        assertThat(pricingRule.apply(TEST_ITEM, -1)).isEmpty();
    }

    static Condition<Pricing> pricingWithSubtotalAndAffectedQuantity(final int subTotal, final int affectedQuantity) {
        return new Condition<>(pricing ->
                pricing.getSubTotal() == subTotal && pricing.getAffectedQuantity() == affectedQuantity,
                "pricing with subTotal %d and affectedQuantity %d", subTotal, affectedQuantity);
    }

}
