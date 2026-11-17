import console.ConsoleMain;
import controllers.*;
import repositories.*;
import services.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArtistRepository artistRepo = new ArtistRepository();
        AlbumRepository albumRepo = new AlbumRepository();
        TrackRepository trackRepo = new TrackRepository();

        ArtistService artistService = new ArtistService(artistRepo, albumRepo);
        AlbumService albumService = new AlbumService(albumRepo, trackRepo, artistRepo);
        TrackService trackService = new TrackService(trackRepo, albumRepo);

        ArtistController artistController = new ArtistController(artistService, scanner);
        AlbumController albumController = new AlbumController(albumService, scanner);
        TrackController trackController = new TrackController(trackService, scanner);

        ConsoleMain app = new ConsoleMain(artistController, albumController, trackController, scanner);
        app.start();

        scanner.close();
    }
}