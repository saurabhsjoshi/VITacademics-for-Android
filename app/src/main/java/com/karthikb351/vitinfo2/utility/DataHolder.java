package com.karthikb351.vitinfo2.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.contract.WithdrawnCourse;
import com.orm.SugarTransactionHelper;

import java.util.List;

/**
 * Created by karthikbalakrishnan on 05/07/15.
 */
public class DataHolder {
    // User data
    private static String registerNumber;
    private static String dateOfBirth;
    private static String mobileNumber;
    private static String campus;
    private static String latestVersion;
    private static String earliestSupportedVersion;
    private static List<Message> messages;
    private static List<Contributor> contributors;
    private static String semester;
    private static List<Course> courses;
    private static List<WithdrawnCourse> withdrawnCourses;
    private static String coursesRefreshed;
    private static List<Grade> grades;
    private static List<GradeCount> gradeCounts;
    private static List<SemesterWiseGrade> semesterWiseGrades;
    private static String gradesRefreshed;
    private static List<Friend> friends;
    private static String token;
    private static String tokenIssued;

    public static void refreshData(final Context c, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    SugarTransactionHelper.doInTansaction(new SugarTransactionHelper.Callback() {
                        @Override
                        public void manipulateInTransaction() {
                            messages = Message.listAll(Message.class);
                            contributors = Contributor.listAll(Contributor.class);
                            courses = Course.listAll(Course.class);
                            withdrawnCourses = WithdrawnCourse.listAll(WithdrawnCourse.class);
                            grades = Grade.listAll(Grade.class);
                            gradeCounts = GradeCount.listAll(GradeCount.class);
                            semesterWiseGrades = SemesterWiseGrade.listAll(SemesterWiseGrade.class);
                            friends = Friend.listAll(Friend.class);
                        }
                    });
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean s) {
                super.onPostExecute(s);
                if (s) {

                    SharedPreferences sharedPreferences = c.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);

                    campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
                    registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
                    dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
                    mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);
                    latestVersion = sharedPreferences.getString(Constants.KEY_ANDROID_LATEST_VERSION, null);
                    earliestSupportedVersion = sharedPreferences.getString(Constants.KEY_ANDROID_SUPPORTED_VERSION, null);
                    semester = sharedPreferences.getString(Constants.KEY_SEMESTER, null);
                    coursesRefreshed = sharedPreferences.getString(Constants.KEY_COURSES_REFRESHED, null);
                    gradesRefreshed = sharedPreferences.getString(Constants.KEY_GRADES_REFRESHED, null);
                    token = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN, null);
                    tokenIssued = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN_ISSUED, null);
                    resultListener.onSuccess();
                } else {
                    resultListener.onFailure();
                }
            }
        }.execute(true);

    }

    public static String getRegisterNumber() {
        return registerNumber;
    }

    public static void setRegisterNumber(String registerNumber) {
        DataHolder.registerNumber = registerNumber;
    }

    public static String getDateOfBirth() {
        return dateOfBirth;
    }

    public static void setDateOfBirth(String dateOfBirth) {
        DataHolder.dateOfBirth = dateOfBirth;
    }

    public static String getMobileNumber() {
        return mobileNumber;
    }

    public static void setMobileNumber(String mobileNumber) {
        DataHolder.mobileNumber = mobileNumber;
    }

    public static String getCampus() {
        return campus;
    }

    public static void setCampus(String campus) {
        DataHolder.campus = campus;
    }

    public static String getLatestVersion() {
        return latestVersion;
    }

    public static void setLatestVersion(String latestVersion) {
        DataHolder.latestVersion = latestVersion;
    }

    public static String getEarliestSupportedVersion() {
        return earliestSupportedVersion;
    }

    public static void setEarliestSupportedVersion(String earliestSupportedVersion) {
        DataHolder.earliestSupportedVersion = earliestSupportedVersion;
    }

    public static List<Message> getMessages() {
        return messages;
    }

    public static void setMessages(List<Message> messages) {
        DataHolder.messages = messages;
    }

    public static List<Contributor> getContributors() {
        return contributors;
    }

    public static void setContributors(List<Contributor> contributors) {
        DataHolder.contributors = contributors;
    }

    public static String getSemester() {
        return semester;
    }

    public static void setSemester(String semester) {
        DataHolder.semester = semester;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static void setCourses(List<Course> courses) {
        DataHolder.courses = courses;
    }

    public static List<WithdrawnCourse> getWithdrawnCourses() {
        return withdrawnCourses;
    }

    public static void setWithdrawnCourses(List<WithdrawnCourse> withdrawnCourses) {
        DataHolder.withdrawnCourses = withdrawnCourses;
    }

    public static String getCoursesRefreshed() {
        return coursesRefreshed;
    }

    public static void setCoursesRefreshed(String coursesRefreshed) {
        DataHolder.coursesRefreshed = coursesRefreshed;
    }

    public static List<Grade> getGrades() {
        return grades;
    }

    public static void setGrades(List<Grade> grades) {
        DataHolder.grades = grades;
    }

    public static List<GradeCount> getGradeCounts() {
        return gradeCounts;
    }

    public static void setGradeCounts(List<GradeCount> gradeCounts) {
        DataHolder.gradeCounts = gradeCounts;
    }

    public static List<SemesterWiseGrade> getSemesterWiseGrades() {
        return semesterWiseGrades;
    }

    public static void setSemesterWiseGrades(List<SemesterWiseGrade> semesterWiseGrades) {
        DataHolder.semesterWiseGrades = semesterWiseGrades;
    }

    public static String getGradesRefreshed() {
        return gradesRefreshed;
    }

    public static void setGradesRefreshed(String gradesRefreshed) {
        DataHolder.gradesRefreshed = gradesRefreshed;
    }

    public static List<Friend> getFriends() {
        return friends;
    }

    public static void setFriends(List<Friend> friends) {
        DataHolder.friends = friends;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        DataHolder.token = token;
    }

    public static String getTokenIssued() {
        return tokenIssued;
    }

    public static void setTokenIssued(String tokenIssued) {
        DataHolder.tokenIssued = tokenIssued;
    }
}
