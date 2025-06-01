package com.my.instagram_clone.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.my.instagram_clone.model.Tag;

public class TagDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/instagram_clone_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root"; // replace with actual password

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Get tag by name
    public Tag getTagByName(String tagName) {
        String sql = "SELECT * FROM tags WHERE tag_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tagName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Tag(
                    rs.getInt("id"),
                    rs.getString("tag_name"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get tag by ID
    public Tag getTagById(int id) {
        String sql = "SELECT * FROM tags WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Tag(
                    rs.getInt("id"),
                    rs.getString("tag_name"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert tag if not exists
    public Tag insertTagIfNotExists(String tagName) {
        Tag existingTag = getTagByName(tagName);
        if (existingTag != null) {
            return existingTag;
        }

        String insertSQL = "INSERT INTO tags (tag_name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tagName);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return getTagById(id); // get full info including created_at
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
