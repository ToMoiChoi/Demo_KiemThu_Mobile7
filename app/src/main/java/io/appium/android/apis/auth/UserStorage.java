package io.appium.android.apis.auth;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static final String FILENAME = "users.txt";

    public static void saveUser(Context context, String username, String email, String password) {
        File file = new File(context.getFilesDir(), FILENAME);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(username + "," + email + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isUsernameTaken(Context context, String username) {
        List<String[]> users = loadUsers(context);
        for (String[] user : users) {
            if (user.length >= 1 && user[0].equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public static List<String[]> loadUsers(Context context) {
        List<String[]> users = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILENAME);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    users.add(line.split(","));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean checkLogin(Context context, String username, String password) {
        List<String[]> users = loadUsers(context);
        for (String[] user : users) {
            if (user.length >= 3 && user[0].equals(username) && user[2].equals(password)) {
                return true;
            }
        }
        return false;
    }
}
