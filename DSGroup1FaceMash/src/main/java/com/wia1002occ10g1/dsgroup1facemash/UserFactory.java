package com.wia1002occ10g1.dsgroup1facemash;

/**
 *
 * @author Harishankar Vinod
 */

//User Factory Implemented (This is an implementation of Factory Design Pattern)
// - 0 Returns new Admin User with Admin Priveliges
// - 1 Returns new Regular user with normal Priveliges
public class UserFactory {
    public Person CreateUser(int UserType, int UserID) throws Exception{
        Person returnval = null;
        switch (UserType) {
            case 0:
                returnval =  new AdminUser(UserID);
                break;
            case 1:
                returnval =  new RegularUser(UserID);
                break;
        }
        return returnval;
    }
}
