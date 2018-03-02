package com.github.nscuro.bttc;

import com.github.nscuro.bttc.item.Item;
import com.github.nscuro.bttc.item.ItemRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;

public final class SupermarketCheckout implements Checkout {

    private final ItemRepository itemRepository;

    private final Map<Item, Integer> items;

    public SupermarketCheckout(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
        return 0;
    }

    Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

}
