package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

/**
 * The {@link UnitPricePricingRule} is applicable to all {@link Item}s and simply
 * prices them by their unit price.
 * <p>
 * Applying this rule to a quantity of n will result in a {@link List} of n {@link Pricing}s.
 */
public final class UnitPricePricingRule implements PricingRule {

    /**
     * @param item     The {@link Item} the rule shall be applied to
     * @param quantity The quantity the rule shall be applied to
     * @return The resulting {@link Pricing}s
     */
    @Override
    public List<Pricing> apply(final Item item, final Integer quantity) {
        if (item == null || quantity == null || quantity <= 0) {
            return emptyList();
        }

        return IntStream
                .range(0, quantity)
                .mapToObj(dontCare -> new Pricing(item.getUnitPrice(), 1))
                .collect(Collectors.toList());
    }

}
