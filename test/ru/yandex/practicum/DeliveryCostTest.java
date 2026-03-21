package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostTest {

    @Test
    void standardParcelCostNormal() {
        Parcel p = new StandardParcel("Книга", 10, "Москва", 1);
        assertEquals(20, p.calculateDeliveryCost());
    }

    @Test
    void standardParcelCostZeroWeight() {
        Parcel p = new StandardParcel("Пусто", 0, "Москва", 1);
        assertEquals(0, p.calculateDeliveryCost());
    }

    @Test
    void fragileParcelCostNormal() {
        Parcel p = new FragileParcel("Ваза", 5, "СПб", 1);
        assertEquals(20, p.calculateDeliveryCost());
    }

    @Test
    void perishableParcelCostNormal() {
        Parcel p = new PerishableParcel("Еда", 4, "Казань", 1, 3);
        assertEquals(12, p.calculateDeliveryCost());
    }

    @Test
    void perishableParcelCostZeroWeight() {
        Parcel p = new PerishableParcel("Еда", 0, "Казань", 1, 3);
        assertEquals(0, p.calculateDeliveryCost());
    }

    @Test
    void isExpiredFalseInsideTtl() {
        PerishableParcel p = new PerishableParcel("Еда", 5, "СПб", 1, 3);
        assertFalse(p.isExpired(3));
    }

    @Test
    void isExpiredTrueAfterTtl() {
        PerishableParcel p = new PerishableParcel("Еда", 5, "СПб", 1, 3);
        assertTrue(p.isExpired(5));
    }

    @Test
    void isExpiredBoundaryEqualDay() {
        PerishableParcel p = new PerishableParcel("Еда", 5, "СПб", 1, 3);
        assertFalse(p.isExpired(4));
    }

    @Test
    void parcelBoxAddWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        box.addParcel(new StandardParcel("A", 5, "X", 1));
        box.addParcel(new StandardParcel("B", 5, "X", 1));
        assertEquals(2, box.getAllParcels().size());
    }

    @Test
    void parcelBoxAddOverLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5);
        box.addParcel(new StandardParcel("A", 5, "X", 1));
        box.addParcel(new StandardParcel("B", 1, "X", 1));
        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    void parcelBoxBoundaryExactLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5);
        box.addParcel(new StandardParcel("A", 5, "X", 1));
        assertEquals(1, box.getAllParcels().size());
    }
}