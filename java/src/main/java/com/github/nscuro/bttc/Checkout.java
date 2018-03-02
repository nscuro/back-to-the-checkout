package com.github.nscuro.bttc;

public interface Checkout {

    void scan(final String sku);

    int getTotalPrice();

}
