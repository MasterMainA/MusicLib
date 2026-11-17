package jdbc;

import models.Artist;
import repositories.RepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcArtistRepository implements RepositoryInterface<Artist> {

    @Override
    public void create(Artist item) {
        String sql = "INSERT INTO artists (name, genre, country) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getGenre());
            ps.setString(3, item.getCountry());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    item.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании артиста в БД", e);
        }
    }

    @Override
    public Artist getById(int id) {
        String sql = "SELECT * FROM artists WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске артиста по ID", e);
        }
        return null;
    }

    @Override
    public List<Artist> getAll() {
        List<Artist> list = new ArrayList<>();
        String sql = "SELECT * FROM artists ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка артистов", e);
        }
        return list;
    }

    @Override
    public void update(int id, Artist item) {
        String sql = "UPDATE artists SET name = ?, genre = ?, country = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getGenre());
            ps.setString(3, item.getCountry());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении артиста", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM artists WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении артиста", e);
        }
    }

    // Маппер из строки ResultSet в объект Artist
    private Artist mapRow(ResultSet rs) throws SQLException {
        Artist artist = new Artist();
        artist.setId(rs.getInt("id"));
        artist.setName(rs.getString("name"));
        artist.setGenre(rs.getString("genre"));
        artist.setCountry(rs.getString("country"));
        return artist;
    }
}