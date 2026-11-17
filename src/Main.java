import console.ConsoleMain;
import controllers.*;
import jdbc.JdbcAlbumRepository;
import jdbc.JdbcArtistRepository;
import jdbc.JdbcTrackRepository;
import repositories.RepositoryInterface;
import models.*;
import services.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RepositoryInterface<Artist> artistRepo = new JdbcArtistRepository();
        RepositoryInterface<Album> albumRepo = new JdbcAlbumRepository();
        RepositoryInterface<Track> trackRepo = new JdbcTrackRepository();

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