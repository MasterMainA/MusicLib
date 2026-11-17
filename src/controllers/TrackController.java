package controllers;

import models.Track;
import services.ServiceInterface;
import java.util.List;
import java.util.Scanner;

public class TrackController implements ControllerInterface {
    private final ServiceInterface<Track> service;
    private final Scanner scanner;

    public TrackController(ServiceInterface<Track> service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void create() {
        System.out.println("Введите название трека:");
        String title = scanner.nextLine();
        System.out.println("Введите длительность в секундах:");
        int durationSeconds = getIntInput();
        System.out.println("Введите ID альбома (или 0 / 'Сингл', если трек без альбома):");
        String albumInput = scanner.nextLine().trim();
        String albumId = (albumInput.isEmpty() || albumInput.equals("0") 
                || albumInput.equalsIgnoreCase("сингл") 
                || albumInput.equalsIgnoreCase("single"))
                ? "Сингл"
                : albumInput;

        Track track = new Track(title, durationSeconds, albumId);
        service.create(track);
        System.out.println("Трек создан:\n" + track);
    }

    @Override
    public void showAll() {
        List<Track> trackList = service.getAll();
        if (trackList.isEmpty()) {
            System.out.println("Список треков пуст.");
            return;
        }
        trackList.forEach(System.out::println);
    }

    @Override
    public void showById() {
        System.out.println("Введите ID трека:");
        int id = getIntInput();
        Track track = service.getById(id);
        System.out.println(track != null ? track : "Трек не найден.");
    }

    @Override
    public void update() {
        System.out.println("Введите ID трека для обновления:");
        int id = getIntInput();
        Track existing = service.getById(id);

        if (existing == null) {
            System.out.println("Трек не найден.");
            return;
        }

        System.out.println("Введите новое название (старое: " + existing.getTitle() + "):");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            title = existing.getTitle();
        }

        System.out.println("Введите новую длительность в секундах (старая: " + existing.getDurationSeconds() + "):");
        int durationSeconds = getIntInput();

        System.out.println("Введите новый ID альбома (старый: " + existing.getAlbumId() + ", или 0 / 'Сингл'):");
        String albumInput = scanner.nextLine().trim();
        String albumId;
        if (albumInput.isEmpty()) {
            albumId = existing.getAlbumId();
        } else if (albumInput.equals("0") || albumInput.equalsIgnoreCase("сингл") || albumInput.equalsIgnoreCase("single")) {
            albumId = "Сингл";
        } else {
            albumId = albumInput;
        }

        service.update(id, new Track(title, durationSeconds, albumId));
        System.out.println("Обновлено:\n" + service.getById(id));
    }

    @Override
    public void delete() {
        System.out.println("Введите ID трека для удаления:");
        int id = getIntInput();
        System.out.println(service.delete(id) ? "Трек успешно удален." : "Трек не найден.");
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка. Введите целое число.");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }
}