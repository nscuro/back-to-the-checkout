package com.github.nscuro.bttc.item;

public final class Item {

    private final String sku;

    private final int unitPrice;

    public Item(final String sku, final int unitPrice) {
        this.sku = sku;
        this.unitPrice = unitPrice;
    }

    public String getSku() {
        return sku;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

}
