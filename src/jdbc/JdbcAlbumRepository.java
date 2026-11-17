package jdbc;

import models.Album;
import repositories.RepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAlbumRepository implements RepositoryInterface<Album> {

    @Override
    public void create(Album item) {
        String sql = "INSERT INTO albums (title, release_year, artist_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getReleaseYear());
            ps.setString(3, item.getArtistId());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    item.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании альбома в БД", e);
        }
    }

    @Override
    public Album getById(int id) {
        String sql = "SELECT * FROM albums WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске альбома по ID", e);
        }
        return null;
    }

    @Override
    public List<Album> getAll() {
        List<Album> list = new ArrayList<>();
        String sql = "SELECT * FROM albums ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка альбомов", e);
        }
        return list;
    }

    @Override
    public void update(int id, Album item) {
        String sql = "UPDATE albums SET title = ?, release_year = ?, artist_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getReleaseYear());
            ps.setString(3, item.getArtistId());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении альбома", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM albums WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении альбома", e);
        }
    }

    private Album mapRow(ResultSet rs) throws SQLException {
        Album album = new Album();
        album.setId(rs.getInt("id"));
        album.setTitle(rs.getString("title"));
        album.setReleaseYear(rs.getInt("release_year"));
        album.setArtistId(rs.getString("artist_id"));
        return album;
    }
}