package com.my.instagram_clone.dao;

import java.sql.*;	

public class FollowDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/instagram_clone_db";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root"; // Update if needed

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void follow(int followerId, int followeeId) {
        String sql = "INSERT INTO follows (follower_id, followee_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, followerId);
            stmt.setInt(2, followeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unfollow(int followerId, int followeeId) {
        String sql = "DELETE FROM follows WHERE follower_id = ? AND followee_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, followerId);
            stmt.setInt(2, followeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFollowing(int followerId, int followeeId) {
        String sql = "SELECT 1 FROM follows WHERE follower_id = ? AND followee_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, followerId);
            stmt.setInt(2, followeeId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
