package com.example;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Database {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.json";
    private static final String COMPLAINTS_FILE = DATA_DIR + "/complaints.json";
    private static final Gson gson = new Gson();
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    public static void initializeDatabase() {
        try {
            Path dataDir = Paths.get(DATA_DIR);
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);

            if (!Files.exists(Paths.get(USERS_FILE))) {
                List<User> defaultUsers = new ArrayList<>();
                User admin = new User("admin", "admin123", "admin@crimesystem.com",
                        "System Administrator", "9999999999", "Police Station", "ADMIN");
                defaultUsers.add(admin);
                saveUsers(defaultUsers);
            }

            if (!Files.exists(Paths.get(COMPLAINTS_FILE))) saveComplaints(new ArrayList<>());

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error initializing database", e);
        }
    }

    // Users
    public static List<User> loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(USERS_FILE)));
            Type listType = new TypeToken<List<User>>(){}.getType();
            List<User> users = gson.fromJson(json, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<User> users) {
        Path usersPath = Paths.get(USERS_FILE);
        try {
            String json = gson.toJson(users);
            // write atomically: write to temp file then move
            Path tmp = usersPath.resolveSibling(usersPath.getFileName().toString() + ".tmp");
            Files.write(tmp, json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            Files.move(tmp, usersPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.err.println("Error saving users (Database.saveUsers): - Database.java:65" + e.getMessage());
        }
    }

    public static boolean addUser(User user) {
        List<User> users = loadUsers();
        boolean usernameExists = users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
        if (usernameExists) return false;
        users.add(user);
        saveUsers(users);
        return true;
    }

    public static User authenticateUser(String username, String password) {
        Optional<User> user = loadUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
        return user.orElse(null);
    }

    // Complaints
    public static List<Complaint> loadComplaints() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(COMPLAINTS_FILE)));
            Type listType = new TypeToken<List<Complaint>>(){}.getType();
            List<Complaint> complaints = gson.fromJson(json, listType);
            return complaints != null ? complaints : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void saveComplaints(List<Complaint> complaints) {
        Path complaintsPath = Paths.get(COMPLAINTS_FILE);
        try {
            String json = gson.toJson(complaints);
            Path tmp = complaintsPath.resolveSibling(complaintsPath.getFileName().toString() + ".tmp");
            Files.write(tmp, json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            Files.move(tmp, complaintsPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.err.println("Error saving complaints (Database.saveComplaints): - Database.java:105" + e.getMessage());
        }
    }

    public static boolean addComplaint(Complaint complaint) {
        List<Complaint> complaints = loadComplaints();
        complaints.add(complaint);
        saveComplaints(complaints);
        return true;
    }

    public static boolean updateComplaint(Complaint updatedComplaint) {
        List<Complaint> complaints = loadComplaints();
        for (int i = 0; i < complaints.size(); i++) {
            if (complaints.get(i).getComplaintId().equals(updatedComplaint.getComplaintId())) {
                complaints.set(i, updatedComplaint);
                saveComplaints(complaints);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteComplaint(String complaintId) {
        List<Complaint> complaints = loadComplaints();
        boolean removed = complaints.removeIf(c -> c.getComplaintId().equals(complaintId));
        if (removed) saveComplaints(complaints);
        return removed;
    }

    public static List<Complaint> getComplaintsByUserId(String userId) {
        return loadComplaints().stream().filter(c -> c.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public static List<Complaint> getComplaintsByStatus(String status) {
        return loadComplaints().stream().filter(c -> c.getStatus().equals(status)).collect(Collectors.toList());
    }

    public static Complaint getComplaintById(String complaintId) {
        return loadComplaints().stream().filter(c -> c.getComplaintId().equals(complaintId)).findFirst().orElse(null);
    }
}