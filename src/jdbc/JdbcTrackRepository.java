package jdbc;

import models.Track;
import repositories.RepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTrackRepository implements RepositoryInterface<Track> {

    @Override
    public void create(Track item) {
        String sql = "INSERT INTO tracks (title, duration_seconds, album_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getDurationSeconds());
            ps.setString(3, item.getAlbumId());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    item.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании трека в БД", e);
        }
    }

    @Override
    public Track getById(int id) {
        String sql = "SELECT * FROM tracks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске трека по ID", e);
        }
        return null;
    }

    @Override
    public List<Track> getAll() {
        List<Track> list = new ArrayList<>();
        String sql = "SELECT * FROM tracks ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка треков", e);
        }
        return list;
    }

    @Override
    public void update(int id, Track item) {
        String sql = "UPDATE tracks SET title = ?, duration_seconds = ?, album_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getDurationSeconds());
            ps.setString(3, item.getAlbumId());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении трека", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tracks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении трека", e);
        }
    }

    private Track mapRow(ResultSet rs) throws SQLException {
        Track track = new Track();
        track.setId(rs.getInt("id"));
        track.setTitle(rs.getString("title"));
        track.setDurationSeconds(rs.getInt("duration_seconds"));
        track.setAlbumId(rs.getString("album_id"));
        return track;
    }
}