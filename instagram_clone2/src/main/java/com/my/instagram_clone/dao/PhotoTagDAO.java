package com.my.instagram_clone.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhotoTagDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/instagram_clone_db?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root";

    private static final String INSERT_PHOTO_TAG_SQL = 
        "INSERT IGNORE INTO photo_tags (photo_id, tag_id) VALUES (?, ?)";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Insert a photo-tag link. IGNORE duplicates.
    public boolean insertPhotoTag(int photoId, int tagId) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PHOTO_TAG_SQL)) {

            ps.setInt(1, photoId);
            ps.setInt(2, tagId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
