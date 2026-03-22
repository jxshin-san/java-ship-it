package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackables = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
    private static ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(50);
    private static ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(70);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 5:
                    showBoxContent();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Трекинг посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Тип: 1-Стандарт, 2-Хрупкая, 3-Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Описание:");
        String description = scanner.nextLine();

        System.out.println("Вес:");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("Адрес:");
        String address = scanner.nextLine();

        System.out.println("День отправки:");
        int day = Integer.parseInt(scanner.nextLine());

        if (type == 1) {
            StandardParcel p = new StandardParcel(description, weight, address, day);
            allParcels.add(p);
            standardBox.addParcel(p);
        } else if (type == 2) {
            FragileParcel p = new FragileParcel(description, weight, address, day);
            allParcels.add(p);
            fragileBox.addParcel(p);
            trackables.add(p);
        } else if (type == 3) {
            System.out.println("TTL:");
            int ttl = Integer.parseInt(scanner.nextLine());
            PerishableParcel p = new PerishableParcel(description, weight, address, day, ttl);
            allParcels.add(p);
            perishableBox.addParcel(p);
        }
    }

    private static void sendParcels() {
        for (Parcel p : allParcels) {
            p.packageItem();
            p.deliver();
        }
    }

    private static void calculateCosts() {
        int sum = 0;
        for (Parcel p : allParcels) {
            sum += p.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость: " + sum);
    }

    private static void trackParcels() {
        System.out.println("Введите локацию:");
        String location = scanner.nextLine();
        for (Trackable t : trackables) {
            t.reportStatus(location);
        }
    }

    private static void showBoxContent() {
        System.out.println("1-Стандарт, 2-Хрупкая, 3-Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        if (type == 1) {
            for (StandardParcel p : standardBox.getAllParcels()) {
                System.out.println(p.getDescription());
            }
        } else if (type == 2) {
            for (FragileParcel p : fragileBox.getAllParcels()) {
                System.out.println(p.getDescription());
            }
        } else if (type == 3) {
            for (PerishableParcel p : perishableBox.getAllParcels()) {
                System.out.println(p.getDescription());
            }
        }
    }
}