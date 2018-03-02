package com.github.nscuro.bttc;

import com.github.nscuro.bttc.item.ItemRepository;

public final class SupermarketCheckout implements Checkout {

    private final ItemRepository itemRepository;

    public SupermarketCheckout(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void scan(final String sku) {

    }

    @Override
    public int getTotalPrice() {
        return 0;
    }

}
