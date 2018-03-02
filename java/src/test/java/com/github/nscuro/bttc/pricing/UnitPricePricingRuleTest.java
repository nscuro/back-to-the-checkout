package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitPricePricingRuleTest extends AbstractPricingRuleTest {

    private static final Item ITEM = new Item("A", 20);

    @BeforeAll
    static void beforeAll() {
        pricingRule = new UnitPricePricingRule();
    }

    @Test
    void testSingleApplication() {
        assertThat(pricingRule.apply(ITEM, 1))
                .haveExactly(1, pricingWithSubtotalAndAffectedQuantity(ITEM.getUnitPrice(), 1));
    }

    @Test
    void testMultipleApplications() {
        assertThat(pricingRule.apply(ITEM, 5))
                .haveExactly(5, pricingWithSubtotalAndAffectedQuantity(ITEM.getUnitPrice(), 1));
    }

}