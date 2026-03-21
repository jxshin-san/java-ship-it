package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();

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
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Тип посылки: 1 — стандартная, 2 — хрупкая, 3 — скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Описание:");
        String description = scanner.nextLine();

        System.out.println("Вес:");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("Адрес:");
        String address = scanner.nextLine();

        System.out.println("День отправки:");
        int sendDay = Integer.parseInt(scanner.nextLine());

        Parcel parcel;

        if (type == 1) {
            parcel = new StandardParcel(description, weight, address, sendDay);
        } else if (type == 2) {
            parcel = new FragileParcel(description, weight, address, sendDay);
        } else {
            System.out.println("Срок хранения:");
            int ttl = Integer.parseInt(scanner.nextLine());
            parcel = new PerishableParcel(description, weight, address, sendDay, ttl);
        }

        allParcels.add(parcel);
    }

    private static void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int total = 0;
        for (Parcel parcel : allParcels) {
            total += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость: " + total);
    }
}