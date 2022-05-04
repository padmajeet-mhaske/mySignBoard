package com.montclair.mhaskep1.registerandlogin.DataProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.montclair.mhaskep1.registerandlogin.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Database interactive service
 * as of now it is using only shared preferences but can be coupled with Database datasource too
 */
public class LoginProvider {

    private String user = "prefUsers";
    private static SharedPreferences mMyPreferences;
    private static Context mContext;
    private static final String key = "AllUserCount";
    //

    public static void Initialize(Context ctxt){
        mContext = ctxt;
        //
        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    // Within Singleton class

    public static void writePreference(String key, Integer value){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putInt(key, value);
        e.commit();
    }
    public static void writePreference(String key, Set<String> value){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putStringSet(key, value);
        e.commit();
    }

    /**
     * Find user in DB
     * @param email
     * @param password
     * @return
     */
    public static User findUserByLogin(String email, String password) {


        Set<String> userAtt = mMyPreferences.getStringSet(getUserKey(email, password), null);

        if(userAtt != null){
            User user = getUserByAtt(userAtt);

            if(user != null && password.equals(user.getPassword())){
                return user;
            } else {
                return null;
            }
        } else
            return null;

    }

    private static User getUserByAtt(Set<String> userAtt) {
        User u = new User();
        for(String att : userAtt){
            if(att.startsWith("fn")){
                u.setFirstName(att.substring(3));
            } else if(att.startsWith("ln")){
                u.setLastName(att.substring(3));
            } else if(att.startsWith("em")){
                u.setEmail(att.substring(3));
            } else if(att.startsWith("pw")){
                u.setPassword(att.substring(3));
            } else if(att.startsWith("do")){
                u.setDob(new Date(att.substring(3)));
            }  else if (att.startsWith("yc")){
                u.setYesCount(Integer.valueOf(att.substring(3)));
            } else if(att.startsWith("nc")){
                u.setNoCount(Integer.valueOf(att.substring(3)));
            }
        }

        return u;
    }

    private static Set<String>  getStringSetForUser(User user) {
        Set<String> att = new LinkedHashSet<>();

        att.add("fn:".concat(user.getFirstName()));
        att.add("ln:".concat(user.getLastName()));
        att.add("em:".concat(user.getEmail()));
        att.add("pw:".concat(user.getPassword()));
        att.add("do:".concat(user.getDob().toString()));
        att.add("yc:".concat(String.valueOf(user.getYesCount())));
        att.add("nc:".concat(String.valueOf(user.getNoCount())));


        return att;
    }

    /**
     * Register new user
     *
     * @param fName
     * @param lName
     * @param email
     * @param password
     * @param date
     * @return
     */
    public static User addUser(String fName, String lName, String email, String password, Date date) {
        User user = new User(fName, lName, email, password, date);
        int allUser = mMyPreferences.getInt(key, 0);

        writePreference(email, getStringSetForUser(user));
        writePreference(key, allUser + 1);

        return user;
    }

    /**
     * Search user by login cred
     * @param email
     * @param password
     * @return
     */
    private static String getUserKey(String email, String password) {
        return String.format("%s", email, password);
    }

    /**
     * User if present.
     * @return
     */
    public static boolean hasUsers() {
        int allUser = mMyPreferences.getInt(key, 0);

        return allUser > 0;
    }
}
