package com.my.instagram_clone.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.my.instagram_clone.model.Photo;

public class PhotoDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/instagram_clone_db?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root";

    private static final String SELECT_ALL_PHOTOS =
        "SELECT p.id, p.image_url, p.caption, p.created_at, p.user_id, u.username " +
        "FROM photos p JOIN users u ON p.user_id = u.id ORDER BY p.created_at DESC";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Photo> getAllPhotos() {
        List<Photo> photos = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PHOTOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Photo photo = new Photo();
                photo.setId(rs.getInt("id"));
                photo.setImageUrl(rs.getString("image_url"));
                photo.setCaption(rs.getString("caption"));
                photo.setCreatedAt(rs.getTimestamp("created_at"));
                photo.setUserId(rs.getInt("user_id"));
                photo.setUsername(rs.getString("username"));
                photos.add(photo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public int savePhoto(int userId, String imageUrl, String caption) {
        String sql = "INSERT INTO photos (user_id, image_url, caption, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setString(2, imageUrl);
            ps.setString(3, caption);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) return -1;

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                else return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
