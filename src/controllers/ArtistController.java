package controllers;

import models.Artist;
import services.ServiceInterface;
import java.util.Scanner;

public class ArtistController implements ControllerInterface {
    private final ServiceInterface<Artist> service;
    private final Scanner scanner;

    public ArtistController(ServiceInterface<Artist> service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override public void create() {
        System.out.println("Введите имя исполнителя:");
        String name = scanner.nextLine();
        System.out.println("Введите жанр:");
        String genre = scanner.nextLine();
        System.out.println("Введите страну:");
        String country = scanner.nextLine();
        Artist artist = new Artist(name, genre, country);
        service.create(artist);
        System.out.println("Объект создан:\n" + artist);
    }

    @Override public void showAll() {
        service.getAll().forEach(System.out::println);
    }

    @Override public void showById() {
        System.out.println("Введите ID исполнителя:");
        int id = getIntInput();
        Artist artist = service.getById(id);
        System.out.println(artist != null ? artist : "Исполнитель не найден.");
    }

    @Override public void update() {
        System.out.println("Введите ID исполнителя для обновления:");
        int id = getIntInput();
        if (service.getById(id) == null) {
            System.out.println("Исполнитель не найден.");
            return;
        }
        System.out.println("Введите новое имя:");
        String name = scanner.nextLine();
        System.out.println("Введите новый жанр:");
        String genre = scanner.nextLine();
        System.out.println("Введите новую страну:");
        String country = scanner.nextLine();
        service.update(id, new Artist(name, genre, country));
        System.out.println("Обновлено:\n" + service.getById(id));
    }

    @Override public void delete() {
        System.out.println("Введите ID для удаления:");
        int id = getIntInput();
        if (service.delete(id)) {
            System.out.println("Исполнитель успешно удален. Данные в связанных альбомах обновлены (ID исполнителя: Неизвестно).");
        } else {
            System.out.println("Объект не найден.");
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Введите целое число.");
            scanner.next();
        }
        int res = scanner.nextInt();
        scanner.nextLine();
        return res;
    }
}