package com.github.nscuro.bttc.pricing;

import com.github.nscuro.bttc.item.Item;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A {@link PricingRule} is a {@link BiFunction} that takes an {@link Item} and its quantity
 * and produces zero or more {@link Pricing}s, where one {@link Pricing} is the result of
 * one formal application of this rule.
 */
@FunctionalInterface
public interface PricingRule extends BiFunction<Item, Integer, List<Pricing>> {
}
