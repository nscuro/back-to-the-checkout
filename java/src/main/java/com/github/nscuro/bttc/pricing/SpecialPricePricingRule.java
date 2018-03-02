package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

/**
 * This is the "special price" {@link PricingRule} from the Kata.
 * <p>
 * It basically represents a pricing of "buy n, get x off".
 */
public final class SpecialPricePricingRule implements PricingRule {

    private final String sku;

    private final int requiredQuantity;

    private final int discount;

    /**
     * @param sku              The SKU this rule shall apply to
     * @param requiredQuantity Amount of items required for this rule to apply
     * @param discount         The discount price per rule application in the currencies smallest unit
     */
    public SpecialPricePricingRule(final String sku, final int requiredQuantity, final int discount) {
        this.sku = sku;
        this.requiredQuantity = requiredQuantity;
        this.discount = discount;
    }

    @Override
    public List<Pricing> apply(final Item item, final Integer quantity) {
        if (item == null || quantity == null || quantity <= 0 || !sku.equals(item.getSku())) {
            return emptyList();
        }

        final int applicationCount = quantity / requiredQuantity;

        return IntStream
                .range(0, applicationCount)
                .mapToObj(dontCare -> {
                    final int standardPrice = requiredQuantity * item.getUnitPrice();

                    return new Pricing(standardPrice - discount, requiredQuantity);
                })
                .collect(Collectors.toList());
    }

}
