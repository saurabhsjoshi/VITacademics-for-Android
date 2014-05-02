package com.collegecode.objects;

/**
 * Created by saurabh on 5/2/14.
 */
public class Mark {

    //CATS
    Test[] cat = new Test[2];

    //QUIZ
    Test[] quiz = new Test[3];

    //ASSIGNMENT
    Test asgn = new Test();

    public class Test{
        String name;
        String status ;
        String marks;
    }

}
