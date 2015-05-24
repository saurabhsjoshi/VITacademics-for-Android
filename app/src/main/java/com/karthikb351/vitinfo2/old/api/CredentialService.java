package com.karthikb351.vitinfo2.old.api;

/**
 * Created by karthikbalakrishnan on 11/03/15.
 */
public class CredentialService {

    private static String campus;
    private static String regno;
    private static String dob;

    public static String getCampus() {
        return campus;
    }

    public static void setCampus(String campus) {
        CredentialService.campus = campus;
    }

    public static String getRegno() {
        return regno;
    }

    public static void setRegno(String regno) {
        CredentialService.regno = regno;
    }

    public static String getDob() {
        return dob;
    }

    public static void setDob(String dob) {
        CredentialService.dob = dob;
    }
}
