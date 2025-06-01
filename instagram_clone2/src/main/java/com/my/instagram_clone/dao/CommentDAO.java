package com.my.instagram_clone.dao;

import java.sql.*;
import java.util.*;
import com.my.instagram_clone.model.Comment;

public class CommentDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/instagram_clone_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root"; // replace with your actual MySQL password

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean addComment(int userId, int photoId, String text) {
        String sql = "INSERT INTO comments (user_id, photo_id, comment_text) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, photoId);
            stmt.setString(3, text);
            return stmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Comment> getCommentsByPhotoId(int photoId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.comment_text, c.created_at, u.username " +
                     "FROM comments c JOIN users u ON c.user_id = u.id " +
                     "WHERE c.photo_id = ? ORDER BY c.created_at DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, photoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comment.setUsername(rs.getString("username"));
                comments.add(comment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }
}
