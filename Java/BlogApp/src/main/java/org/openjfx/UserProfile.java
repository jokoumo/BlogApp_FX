package org.openjfx;

public class UserProfile {
    private static String firstName;
    private static String lastName;
    private static String joiningDate;
    private static String birthDate;
    private static String country;
    private static String email;

    public static void setUserProfile(String pFirstName, String pLastName, String pBirthDate,
                                      String pCountry, String pEmail, String pJoiningDate) {
        firstName = pFirstName;
        lastName = pLastName;
        birthDate = pBirthDate;
        country = pCountry;
        email = pEmail;
        joiningDate = pJoiningDate;
    }

    public static void clearUserProfile() {
        firstName = "";
        lastName = "";
        birthDate = "";
        country = "";
        email = "";
        joiningDate = "";
    }

    public static String getUserName() {
        return (firstName + " " + lastName);
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getJoiningDate() {
        return joiningDate;
    }

    public static String getBirthDate() {
        return birthDate;
    }

    public static String getEmail() {
        return email;
    }

    public static String getCountry() {
        return country;
    }
}
