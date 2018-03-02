package com.github.nscuro.bttc;

public interface Checkout {

    /**
     * @param sku Stock Keeping Unit of the item to scan
     */
    void scan(final String sku);

    /**
     * Get the total price for all previously scanned items in the currency's smallest unit.
     *
     * @return The total price in the currency's smallest unit
     */
    int getTotalPrice();

}
