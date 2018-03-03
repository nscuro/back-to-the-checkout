package com.github.nscuro.bttc.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("An Item")
class ItemTest {

    @Nested
    @DisplayName("when comparing with another object")
    class EqualsTest {

        @Test
        @DisplayName("should indicate equality when the other object is identical")
        void testWithIdenticalItems() {
            final Item item = new Item("ABC", 111);

            assertThat(item).isEqualTo(item);
        }

        @Test
        @DisplayName("should indicate equality when the other item has the same SKU")
        void testWithIdenticalSkuAndDifferentUnitPrice() {
            final Item left = new Item("ABC", 222);
            final Item right = new Item("ABC", 333);

            assertThat(left).isEqualTo(right);
        }

        @Test
        @DisplayName("should not indicate equality when the other item has a different SKU")
        void testWithNonIdenticalSkuAndEqualUnitPrice() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("CBA", 111);

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        @DisplayName("should treat SKUs case sensitively")
        void testCaseSensitivityOfSku() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("abc", 111);

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        @DisplayName("should not indicate equality when the other object is of different type")
        void testWithDifferentOtherClass() {
            final Item left = new Item("ABC", 111);
            final String right = "ABC";

            assertThat(left).isNotEqualTo(right);
        }

        @Test
        @DisplayName("should not indicate equality when the other object is null")
        void testWithNull() {
            final Item left = new Item("ABC", 111);
            final Item right = null;

            assertThat(left).isNotEqualTo(right);
            assertThat(right).isNotEqualTo(left);
        }

    }

    @Nested
    @DisplayName("when calculating the hash code")
    class HashCodeTest {

        @Test
        @DisplayName("should produce the same code for identical objects")
        void testWithIdenticalItems() {
            final Item left = new Item("ABC", 111);

            assertThat(left.hashCode()).isEqualTo(left.hashCode());
        }

        @Test
        @DisplayName("should produce the same code for items with identical SKUs")
        void testWithIdenticalSkuAndDifferentUnitPrice() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("ABC", 222);

            assertThat(left.hashCode()).isEqualTo(right.hashCode());
        }

        @Test
        @DisplayName("should produce different codes for items with different SKUs")
        void testWithDifferentSku() {
            final Item left = new Item("ABC", 111);
            final Item right = new Item("CBA", 111);

            assertThat(left.hashCode()).isNotEqualTo(right.hashCode());
        }

    }

}