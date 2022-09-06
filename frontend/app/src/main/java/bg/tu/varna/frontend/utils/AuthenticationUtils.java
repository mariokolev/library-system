package bg.tu.varna.frontend.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import bg.tu.varna.frontend.model.User;

public class AuthenticationUtils {

    public static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, User user) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putLong("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putStringSet("permissions", new HashSet<>(user.getPermissions()));
        editor.putString("token", user.getToken());
        editor.putString("role", user.getRole());

        if (user.getLibraryId() != null) {
            editor.putLong("library_id", user.getLibraryId());
        }

        editor.apply();
    }

    public static Long getUserId(Context context) {
        return getSharedPreference(context).getLong("id", 0L);
    }

    public static String getUsername(Context context) {
        return getSharedPreference(context).getString("username", null);
    }

    public static Set<String> getPermissions(Context context) {
        return getSharedPreference(context).getStringSet("permissions", new HashSet<>());
    }

    public static String getToken(Context context) {
        return getSharedPreference(context).getString("token", null);
    }

    public static String getRole(Context context) {
        return getSharedPreference(context).getString("role", null);
    }

    public static Long getLibraryId(Context context) {
        return getSharedPreference(context).getLong("library_id", 0L);
    }

    public static void unsetUser(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.clear();
        editor.apply();
    }

    public static Boolean isLoggedIn(Context context) {
        return getUsername(context) != null;
    }
}
