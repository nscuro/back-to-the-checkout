package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialPricePricingRuleTest extends AbstractPricingRuleTest {

    private static final Item ITEM = new Item("A", 40);

    @BeforeAll
    static void beforeAll() {
        pricingRule = new SpecialPricePricingRule("A", 2, 20);
    }

    @Test
    void testWithTooLittleQuantity() {
        assertThat(pricingRule.apply(ITEM, 1)).isEmpty();
    }

    @Test
    void testSingleApplication() {
        assertThat(pricingRule.apply(ITEM, 2))
                .haveExactly(1, pricingWithSubtotalAndAffectedQuantity(60, 2));
    }

    @Test
    void testSingleApplicationWithRest() {
        assertThat(pricingRule.apply(ITEM, 3))
                .haveExactly(1, pricingWithSubtotalAndAffectedQuantity(60, 2));
    }

    @Test
    void testMultipleApplications() {
        assertThat(pricingRule.apply(ITEM, 6))
                .haveExactly(3, pricingWithSubtotalAndAffectedQuantity(60, 2));
    }

}
