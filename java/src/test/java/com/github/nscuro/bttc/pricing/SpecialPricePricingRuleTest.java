package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The special price pricing rule")
class SpecialPricePricingRuleTest extends AbstractPricingRuleTest {

    private static final Item ITEM = new Item("A", 40);

    @BeforeAll
    static void beforeAll() {
        pricingRule = new SpecialPricePricingRule("A", 2, 20);
    }

    @Test
    @DisplayName("should return an empty list when the provided quantity is not high enough")
    void testWithTooLittleQuantity() {
        assertThat(pricingRule.apply(ITEM, 1)).isEmpty();
    }

    @Test
    @DisplayName("should produce one pricing when the provided quantity is sufficient for one application")
    void testSingleApplication() {
        assertThat(pricingRule.apply(ITEM, 2))
                .haveExactly(1, pricingWithSubtotalAndAffectedQuantity(60, 2));
    }

    @Test
    @DisplayName("should only produce one pricing when the provided quantity is sufficient for just one application")
    void testSingleApplicationWithRest() {
        assertThat(pricingRule.apply(ITEM, 3))
                .haveExactly(1, pricingWithSubtotalAndAffectedQuantity(60, 2));
    }

    @Test
    @DisplayName("should produce multiple pricings when the provided quantity is a multiple of the required one")
    void testMultipleApplications() {
        assertThat(pricingRule.apply(ITEM, 6))
                .haveExactly(3, pricingWithSubtotalAndAffectedQuantity(60, 2));
    }

}
