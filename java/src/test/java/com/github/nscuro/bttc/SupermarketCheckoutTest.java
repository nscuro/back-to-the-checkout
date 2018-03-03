package com.github.nscuro.bttc;

import com.github.nscuro.bttc.item.Item;
import com.github.nscuro.bttc.item.ItemRepository;
import com.github.nscuro.bttc.pricing.PricingRule;
import com.github.nscuro.bttc.pricing.SpecialPricePricingRule;
import com.github.nscuro.bttc.pricing.UnitPricePricingRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("The supermarket checkout")
class SupermarketCheckoutTest {

    @Nested
    @DisplayName("when calculating the total price")
    class GetTotalPriceTest {

        private Checkout checkout;

        @BeforeEach
        void beforeEach() {
            final List<PricingRule> pricingRules = Arrays.asList(
                    new SpecialPricePricingRule("A", 3, 20),
                    new SpecialPricePricingRule("B", 2, 15),
                    new UnitPricePricingRule()
            );

            checkout = new SupermarketCheckout(new TestItemRepository(), pricingRules);
        }

        @DisplayName("should correctly sum up multiple unit prices when no special rules apply")
        @ParameterizedTest(name = "[{index}] items={0} expectedTotalPrice={1}")
        @CsvSource({
                "'', 0",
                "A, 50",
                "AB, 80",
                "CDBA, 115"
        })
        void testUnitPricesOnly(final String items, final int expectedTotalPrice) {
            skuStreamFromString(items)
                    .forEach(checkout::scan);

            assertThat(checkout.getTotalPrice())
                    .isEqualTo(expectedTotalPrice);
        }

        @DisplayName("should correctly consider pricing rules when processing larger item quantities")
        @ParameterizedTest(name = "[{index}] items={0} expectedTotalPrice={1}")
        @CsvSource({
                "AA, 100",
                "AAA, 130",
                "AAAA, 180",
                "AAAAA, 230",
                "AAAAAA, 260",
                "AAAB, 160",
                "AAABB, 175",
                "AAABBD, 190",
                "DABABA, 190"
        })
        void testLargerQuantities(final String items, final int expectedTotalPrice) {
            skuStreamFromString(items)
                    .forEach(checkout::scan);

            assertThat(checkout.getTotalPrice())
                    .isEqualTo(expectedTotalPrice);
        }

        @Test
        @DisplayName("should be predictable for sequentially increasing item quantities")
        void testIncremental() {
            assertThat(checkout.getTotalPrice())
                    .isEqualTo(0);

            checkout.scan("A");
            assertThat(checkout.getTotalPrice()).isEqualTo(50);

            checkout.scan("B");
            assertThat(checkout.getTotalPrice()).isEqualTo(80);

            checkout.scan("A");
            assertThat(checkout.getTotalPrice()).isEqualTo(130);

            checkout.scan("A");
            assertThat(checkout.getTotalPrice()).isEqualTo(160);

            checkout.scan("B");
            assertThat(checkout.getTotalPrice()).isEqualTo(175);
        }

    }

    @Nested
    @DisplayName("when scanning a SKU")
    class ScanTest {

        private ItemRepository itemRepositoryMock;

        private SupermarketCheckout checkout;

        @BeforeEach
        void beforeEach() {
            itemRepositoryMock = mock(ItemRepository.class);

            checkout = new SupermarketCheckout(itemRepositoryMock, emptyList());
        }

        @Test
        @DisplayName("should increase the item's quantity when the same SKU has been scanned before")
        void testWithExistingItem() {
            final Item item = new Item("X", 333);

            given(itemRepositoryMock.findBySku("X"))
                    .willReturn(Optional.of(item));

            assertThat(checkout.getItems().get(item)).isNull();

            checkout.scan("X");

            assertThat(checkout.getItems().get(item)).isEqualTo(1);

            checkout.scan("X");

            assertThat(checkout.getItems().get(item)).isEqualTo(2);
        }

        @Test
        @DisplayName("should throw an exception when no item was found for the SKU provided")
        void testWithNonExistingItem() {
            given(itemRepositoryMock.findBySku("X"))
                    .willReturn(Optional.empty());

            assertThatExceptionOfType(NoSuchElementException.class)
                    .isThrownBy(() -> checkout.scan("X"));
        }

    }

    private static class TestItemRepository implements ItemRepository {

        private static final List<Item> ITEMS = Arrays.asList(
                new Item("A", 50),
                new Item("B", 30),
                new Item("C", 20),
                new Item("D", 15)
        );

        @Override
        public Optional<Item> findBySku(final String sku) {
            return ITEMS
                    .stream()
                    .filter(item -> item.getSku().equals(sku))
                    .findAny();
        }

    }

    private static Stream<String> skuStreamFromString(final String string) {
        return string.chars()
                .mapToObj(integralChar -> (char) integralChar)
                .map(String::valueOf);
    }


}
