/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package poepart3.pkg3;

public class Login {
    private String userName;
    private String passWord;
    private String cellphone;

    public String registerUser(String name, String password, String cellphone) {
        // Username must contain _ and be <=5 chars, password must be 8+ with capital, number, special char
        if (name.length() > 5 || !name.contains("_")) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (password.length() < 8 || !password.matches(".*[A-Z].") || !password.matches(".[0-9].") || !password.matches(".[!@#$%^&].*")) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        }
        if (!cellphone.startsWith("+27") || cellphone.length() != 12) {
            return "Cellphone number is incorrectly formatted or does not contain an international code.";
        }
        this.userName = name;
        this.passWord = password;
        this.cellphone = cellphone;
        return "Username successfully captured.\nPassword successfully captured.\nCellphone number successfully captured.";
    }

    public boolean registerStatus(String name, String password, String cellphone) {
        return name.length() <= 5 && name.contains("_")
                && password.length() >= 8
                && cellphone.startsWith("+27") && cellphone.length() == 12;
    }

    public boolean loginUser(String name, String password) {
        if (userName == null) {
            return false;
        }
        if (name.equals(userName) && password.equals(passWord)) {
            return true;
        } else {
            return false;
        }
    }

    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Login success";
        } else {
            return "Login failed";
        }
    }
}