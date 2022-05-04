package com.montclair.mhaskep1.registerandlogin.service;

import com.montclair.mhaskep1.registerandlogin.DataProvider.LoginProvider;
import com.montclair.mhaskep1.registerandlogin.model.User;

import java.util.Date;

/**
 * Login service for user relation opreations
 */
public class LoginService {

    /**
     * Find user specified in login screen
     * @param email
     * @param password
     * @return
     */
    public static User findUserByLogin(String email, String password){

        if(email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return null;
        }

        return LoginProvider.findUserByLogin(email, password);


    }

    /**
     * Register new user
     *
     * @param fName
     * @param lName
     * @param date
     * @param email
     * @param password
     * @return
     */
    public static User addUser(String fName, String lName, Date date, String email, String password){
        return LoginProvider.addUser(fName, lName, email, password, date);


    }

    /**
     * To validate if any user is present
     * @return
     */
    public static boolean hasUsers() {
        return LoginProvider.hasUsers();
    }
}
