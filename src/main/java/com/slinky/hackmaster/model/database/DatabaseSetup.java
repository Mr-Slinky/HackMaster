package com.slinky.hackmaster.model.database;

import java.nio.file.Paths;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.stream.Collectors;

/**
 * Starting point for the configuration of data persistence for the application.
 * Responsible for setting up the database in the APPDATA directory of the
 * user's profile. Only creates the file if it does not already exist.
 *
 * @author Kheagen Haskins
 */
public class DatabaseSetup {

    // ============================== Static ================================ //
    private static final String DB_NAME = "game_scores";
    private static final String DB_DIRECTORY = Paths.get(System.getenv("APPDATA"), "hackmaster").toString();
    private static final String DB_URL = "jdbc:h2:" + DB_DIRECTORY + File.separator + DB_NAME;

    public static void setupDatabase() {
        // Ensure the directory exists
        File dbDir = new File(DB_DIRECTORY);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        // Check if this is the first time the application is running
        if (isFirstRun()) {
            setup();
        }
    }

    // ========================== Helper Methods ============================ //
    private static boolean isFirstRun() {
        // For simplicity, check if the database file exists
        File dbFile = new File(DB_DIRECTORY, DB_NAME + ".mv.db");
        return !dbFile.exists();
    }

    private static void setup() {
        try (Connection conn = DriverManager.getConnection(DB_URL, "sa", "")) {
            executeSqlFile(conn, "/schema/create_table.sql");
            System.out.println("Database and table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeSqlFile(Connection conn, String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                DatabaseSetup.class.getResourceAsStream(filePath)))) {
            String sql = reader.lines().collect(Collectors.joining("\n"));
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

// ============================== Fields ================================ //
// =========================== Constructors ============================= //
// ============================== Getters =============================== //
// ============================== Setters =============================== //
// ============================ API Methods ============================= //