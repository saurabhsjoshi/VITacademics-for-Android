package com.collegecode.objects;

/**
 * Created by saurabh on 5/2/14.
 */
public class Mark {

    //CATS
    public Test[] cat = new Test[2];

    //QUIZ
    public Test[] quiz = new Test[3];

    //ASSIGNMENT
    public Test asgn = new Test();

    public Mark(){
        for (int i = 0 ; i<3 ; i++){
            if (i<2)
                cat[i] = new Test();
            if (i<3)
                quiz[i] = new Test();
        }
    }
    public class Test{
        public String name;
        public String status ;
        public String marks;
    }

}
