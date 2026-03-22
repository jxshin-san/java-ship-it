package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    @Test
    void shouldCalculateStandardParcelCostCorrectly() {
        StandardParcel parcel = new StandardParcel("Книга", 7, "ул. Пушкина 10", 15);
        assertEquals(14, parcel.calculateDeliveryCost());
    }

    @Test
    void shouldCalculateFragileParcelCostCorrectly() {
        FragileParcel parcel = new FragileParcel("Ваза", 3, "пр. Мира 5", 10);
        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    void shouldCalculatePerishableParcelCostCorrectly() {
        PerishableParcel parcel = new PerishableParcel("Мороженое", 4, "ул. Ленина 7", 20, 3);
        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    void shouldCalculateZeroCostForZeroWeight() {
        assertAll(
                () -> assertEquals(0, new StandardParcel("Пусто", 0, "а", 1).calculateDeliveryCost()),
                () -> assertEquals(0, new FragileParcel("Пусто", 0, "а", 1).calculateDeliveryCost()),
                () -> assertEquals(0, new PerishableParcel("Пусто", 0, "а", 1, 5).calculateDeliveryCost())
        );
    }

    @Test
    void shouldReturnFalseWhenPerishableParcelNotExpired() {
        PerishableParcel parcel = new PerishableParcel("Йогурт", 1, "дом", 10, 5);
        assertFalse(parcel.isExpired(14));
    }

    @Test
    void shouldReturnTrueWhenPerishableParcelExpired() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 2, "кв.5", 5, 2);
        assertTrue(parcel.isExpired(8));
    }

    @Test
    void shouldReturnFalseOnExactlyLastDayOfTtl() {
        PerishableParcel parcel = new PerishableParcel("Сметана", 1, "адрес", 20, 3);
        assertFalse(parcel.isExpired(23));
    }

    @Test
    void shouldAddParcelToBoxWhenWeightWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(20);

        StandardParcel p1 = new StandardParcel("Кирпич", 8, "а", 1);
        StandardParcel p2 = new StandardParcel("Книга", 7, "б", 1);

        box.addParcel(p1);
        box.addParcel(p2);

        List<StandardParcel> parcels = box.getAllParcels();
        assertEquals(2, parcels.size());
        assertEquals(15, parcels.stream().mapToInt(Parcel::getWeight).sum());
    }

    @Test
    void shouldNotAddParcelToBoxWhenWeightExceeded() {
        ParcelBox<FragileParcel> box = new ParcelBox<>(10);

        FragileParcel p1 = new FragileParcel("Ваза", 6, "а", 1);
        FragileParcel p2 = new FragileParcel("Стекло", 5, "б", 1);

        box.addParcel(p1);
        box.addParcel(p2);

        assertEquals(1, box.getAllParcels().size());
        assertEquals("Ваза", box.getAllParcels().get(0).getDescription());
    }

    @Test
    void shouldAddParcelWhenWeightExactlyEqualsMaxLimit() {
        ParcelBox<PerishableParcel> box = new ParcelBox<>(15);

        PerishableParcel p = new PerishableParcel("Сыр", 15, "дом", 10, 4);

        box.addParcel(p);

        assertEquals(1, box.getAllParcels().size());
        assertEquals(15, box.getAllParcels().get(0).getWeight());
    }
}