package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The unit price pricing rule")
class UnitPricePricingRuleTest extends AbstractPricingRuleTest {

    private static final Item ITEM = new Item("A", 20);

    @BeforeAll
    static void beforeAll() {
        pricingRule = new UnitPricePricingRule();
    }

    @Test
    @DisplayName("should produce a single pricing when the provided quantity is 1")
    void testSingleApplication() {
        assertThat(pricingRule.apply(ITEM, 1))
                .haveExactly(1, pricingWithSubtotalAndAffectedQuantity(ITEM.getUnitPrice(), 1));
    }

    @Test
    @DisplayName("should produce n pricings for a provided quantity of n")
    void testMultipleApplications() {
        assertThat(pricingRule.apply(ITEM, 5))
                .haveExactly(5, pricingWithSubtotalAndAffectedQuantity(ITEM.getUnitPrice(), 1));
    }

}