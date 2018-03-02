package com.github.nscuro.bttc;

import com.github.nscuro.bttc.item.Item;
import com.github.nscuro.bttc.item.ItemRepository;
import com.github.nscuro.bttc.pricing.Pricing;
import com.github.nscuro.bttc.pricing.PricingRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;

public final class SupermarketCheckout implements Checkout {

    private final ItemRepository itemRepository;

    private final List<PricingRule> pricingRules;

    private final Map<Item, Integer> items;

    public SupermarketCheckout(final ItemRepository itemRepository,
                               final List<PricingRule> pricingRules) {
        this.itemRepository = itemRepository;
        this.pricingRules = pricingRules;
        this.items = new HashMap<>();
    }

    @Override
    public void scan(final String sku) {
        final Item item = itemRepository.findBySku(sku)
                .orElseThrow(() -> new NoSuchElementException(format("no item with sku \"%s\" found", sku)));

        final int currentItemQuantity = Optional
                .ofNullable(items.putIfAbsent(item, 0))
                .orElse(0);

        items.put(item, currentItemQuantity + 1);
    }

    @Override
    public int getTotalPrice() {
        return items
                .entrySet()
                .stream()
                .mapToInt(this::applyPricingRules)
                .sum();
    }

    private int applyPricingRules(final Map.Entry<Item, Integer> itemEntry) {
        return pricingRules
                .stream()
                .map(pricingRule -> pricingRule.apply(itemEntry.getKey(), itemEntry.getValue()))
                .filter(applications -> !applications.isEmpty())
                .flatMap(List::stream)
                .reduce(new Pricing(0, 0), (left, right) -> {
                    if (itemEntry.getValue() < (left.getAffectedQuantity() + right.getAffectedQuantity())) {
                        return left;
                    } else {
                        return new Pricing(
                                left.getSubTotal() + right.getSubTotal(),
                                left.getAffectedQuantity() + right.getAffectedQuantity()
                        );
                    }
                })
                .getSubTotal();
    }

    Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

}
