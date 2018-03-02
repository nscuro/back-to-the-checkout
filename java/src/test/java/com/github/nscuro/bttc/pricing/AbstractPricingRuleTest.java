package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractPricingRuleTest {

    static PricingRule pricingRule;

    @Test
    void testWithNullItem() {
        assertThat(pricingRule.apply(null, 1)).isEmpty();
    }

    @Test
    void testWithNullQuantity() {
        assertThat(pricingRule.apply(new Item("X", 1), null)).isEmpty();
    }

    @Test
    void testWithZeroQuantity() {
        assertThat(pricingRule.apply(new Item("X", 1), 0)).isEmpty();
    }

    static Condition<Pricing> pricingWithSubtotalAndAffectedQuantity(final int subTotal, final int affectedQuantity) {
        return new Condition<>(pricing ->
                pricing.getSubTotal() == subTotal && pricing.getAffectedQuantity() == affectedQuantity,
                "pricing with subTotal %d and affectedQuantity %d", subTotal, affectedQuantity);
    }

}
