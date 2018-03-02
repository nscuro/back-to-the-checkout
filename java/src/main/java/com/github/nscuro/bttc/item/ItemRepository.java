package com.github.nscuro.bttc.item;

import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findBySku(final String sku);

}
