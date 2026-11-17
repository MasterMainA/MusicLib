package console;

import controllers.ControllerInterface;
import java.util.Scanner;

public class ConsoleMain {
    private final ControllerInterface artistController;
    private final ControllerInterface albumController;
    private final ControllerInterface trackController;
    private final Scanner scanner;

    public ConsoleMain(ControllerInterface artistController, ControllerInterface albumController, ControllerInterface trackController, Scanner scanner) {
        this.artistController = artistController;
        this.albumController = albumController;
        this.trackController = trackController;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Главное меню ---");
            System.out.println("1. Управление исполнителями");
            System.out.println("2. Управление альбомами");
            System.out.println("3. Управление треками");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            String choice = scanner.nextLine();
            if (choice.equals("0")) break;

            ControllerInterface activeController = switch (choice) {
                case "1" -> artistController;
                case "2" -> albumController;
                case "3" -> trackController;
                default -> null;
            };

            if (activeController != null) {
                entityMenu(activeController);
            } else {
                System.out.println("Неверный ввод. Пожалуйста, выберите от 0 до 3.");
            }
        }
    }

    private void entityMenu(ControllerInterface controller) {
        while (true) {
            System.out.println("\n--- Меню сущности ---");
            System.out.println("1. Создать (Create)");
            System.out.println("2. Показать все (Read All)");
            System.out.println("3. Найти по ID (Read)");
            System.out.println("4. Обновить (Update)");
            System.out.println("5. Удалить (Delete)");
            System.out.println("0. Назад");
            System.out.print("Действие: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> controller.create();
                case "2" -> controller.showAll();
                case "3" -> controller.showById();
                case "4" -> controller.update();
                case "5" -> controller.delete();
                case "0" -> { return; }
                default -> System.out.println("Неверный ввод.");
            }
        }
    }
}