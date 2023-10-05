package com.wia1002occ10g1.dsgroup1facemash;

import java.util.List;

/**
 *
 * @author Harishankar Vinod
 */

//Factory Design Pattern created for a Person.
public interface Person {
    
    //Retrieves Person Details from the Database
    public String[] RetrievePersonDetails();
    
    //Imports all friends from the database
    public List<Person> getFriends();
    
    //Retrieves name of User
    public String getName();
    
    //Retrieves database ID of the person
    public int getID() throws Exception;
    
    //Sets the value for a Degree instance variable relevant to the current person
    public void setDegree(int degree);
    
    //Retrieves a Degree instance variable relevant to the current person
    public int getDegree();
    
    //Adds a friend and stores it in the Friend ArrayList
    public void addFriend(Person friend);
    
    //Clears Friend List for Friend List Regeneration
    public void clearFriendList();
    
    //Adds a Partner for the User
    public void addPartner(int PartnerID);
    
    //Removes the User's Partner
    public void removePartner();
    
    //Updates User's Relationship status
    public void UpdateRelationshipStatus(int newStatus) throws Exception;
    
    //Checks if whether a Person is in a Relationship
    public boolean isSingle();
    
    //Checks if the Person's Relationship is in Complicated Situation
    public boolean isComplicated();
    
    //Updates Very Specific Details of the User:
    public void UpdateSpecificDetails(String[] info) throws Exception;
    
    //Retrieves Partner ID
    public int getPartnerID();
    
}
