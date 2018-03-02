package com.github.nscuro.bttc.item;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Nested
    class EqualsTest {

        @Test
        void testWithIdenticalItems() {
            final Item item = new Item("ABC", 111);

            assertThat(item).isEqualTo(item);
        }

        @Test
        void testWithIdenticalSkuAndDifferentUnitPrice() {
            final Item left = new Item("ABC", 222);
            final Item right = new Item("ABC", 333);

            assertThat(left).isEqualTo(right);
        }

        @Test
        void testWithNonIdenticalSkuAndEqualUnitPrice() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("CBA", 111);

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        void testCaseSensitivityOfSku() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("abc", 111);

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        void testWithDifferentOtherClass() {
            final Item left = new Item("ABC", 111);
            final String right = "ABC";

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        void testWithNull() {
            final Item left = new Item("ABC", 111);
            final Item right = null;

            assertThat(left).isNotEqualTo(right);
            assertThat(right).isNotEqualTo(left);
        }

    }

    @Nested
    class HashCodeTest {

        @Test
        void testWithIdenticalItems() {
            final Item left = new Item("ABC", 111);

            assertThat(left.hashCode()).isEqualTo(left.hashCode());
        }

        @Test
        void testWithIdenticalSkuAndDifferentUnitPrice() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("ABC", 222);

            assertThat(left.hashCode()).isEqualTo(right.hashCode());
        }

        @Test
        void testWithDifferentSku() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("CBA", 111);

            assertThat(left.hashCode()).isNotEqualTo(right.hashCode());
        }

    }

    @Test
    void testToString() {
        final Item item = new Item("XYZ", 55);

        assertThat(item.toString()).isEqualTo("Item{sku=XYZ, unitPrice=55}");
    }

}