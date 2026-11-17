package controllers;

import models.Album;
import services.ServiceInterface;
import java.util.Scanner;

public class AlbumController implements ControllerInterface {
    private final ServiceInterface<Album> service;
    private final Scanner scanner;

    public AlbumController(ServiceInterface<Album> service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void create() {
        System.out.println("Введите название альбома:");
        String title = scanner.nextLine();
        System.out.println("Введите год выпуска:");
        int releaseYear = getIntInput();
        System.out.println("Введите ID исполнителя (или 0 / 'Неизвестно', если исполнитель неизвестен):");
        String artistInput = scanner.nextLine().trim();
        String artistId = (artistInput.isEmpty() || artistInput.equals("0") 
                || artistInput.equalsIgnoreCase("неизвестно") 
                || artistInput.equalsIgnoreCase("unknown"))
                ? "Неизвестно"
                : artistInput;

        Album album = new Album(title, releaseYear, artistId);
        service.create(album);
        System.out.println("Альбом создан:\n" + album);
    }

    @Override
    public void showAll() {
        if (service.getAll().isEmpty()) {
            System.out.println("Список альбомов пуст.");
            return;
        }
        service.getAll().forEach(System.out::println);
    }

    @Override
    public void showById() {
        System.out.println("Введите ID альбома:");
        int id = getIntInput();
        Album album = service.getById(id);
        System.out.println(album != null ? album : "Альбом не найден.");
    }

    @Override
    public void update() {
        System.out.println("Введите ID альбома для обновления:");
        int id = getIntInput();
        Album existing = service.getById(id);

        if (existing == null) {
            System.out.println("Альбом не найден.");
            return;
        }

        System.out.println("Введите новое название (старое: " + existing.getTitle() + "):");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            title = existing.getTitle();
        }

        System.out.println("Введите новый год выпуска (старый: " + existing.getReleaseYear() + "):");
        int releaseYear = getIntInput();

        System.out.println("Введите новый ID исполнителя (или 0 / 'Неизвестно', старый: " + existing.getArtistId() + "):");
        String artistInput = scanner.nextLine().trim();
        String artistId;
        if (artistInput.isEmpty()) {
            artistId = existing.getArtistId();
        } else if (artistInput.equals("0") || artistInput.equalsIgnoreCase("неизвестно") || artistInput.equalsIgnoreCase("unknown")) {
            artistId = "Неизвестно";
        } else {
            artistId = artistInput;
        }

        service.update(id, new Album(title, releaseYear, artistId));
        System.out.println("Обновлено:\n" + service.getById(id));
    }

    @Override
    public void delete() {
        System.out.println("Введите ID альбома для удаления:");
        int id = getIntInput();
        if (service.delete(id)) {
            System.out.println("Альбом успешно удален. Данные в связанных треках обновлены (ID альбома: Сингл).");
        } else {
            System.out.println("Альбом не найден.");
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка. Введите целое число.");
            scanner.next();
        }
        int res = scanner.nextInt();
        scanner.nextLine();
        return res;
    }
}