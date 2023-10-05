package com.wia1002occ10g1.dsgroup1facemash;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Aiman Alias, Afiq Hakimi, Harishankar Vinod, Zul Azhn, and interfaces with MySQL created by Tan Shu Hui
 */
public class TheSocialNetwork {
    
    //Necessary Declarations
    private List<Person> people;
    
    public TheSocialNetwork() {
        this.people = new ArrayList<>();
    }

    public void addPerson(Person person) {
        people.add(person);
    }
    
    public void addFriendship(int person1, int person2) throws Exception {
        Person p1 = getPersonByID(person1);
        Person p2 = getPersonByID(person2);

        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Person not found");
        }

        p1.addFriend(p2);
        p2.addFriend(p1);
    }

    public List<Person> getMutualFriends(int person1, int person2) throws Exception {
        Person p1 = getPersonByID(person1);
        Person p2 = getPersonByID(person2);

        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Person not found");
        }

        List<Person> mutualFriends = new ArrayList<>();
        for (Person friend1 : p1.getFriends()) {
            if (p2.getFriends().contains(friend1)) {
                mutualFriends.add(friend1);
            }
        }

        return mutualFriends;
    }
    
    public Person getPersonByID(int id) throws Exception {
        for (Person person : people) {
            if (person.getID() == id) {
                return person;
            }
        }
        return null;
    }
    
    public Person getPersonByName(String name) throws Exception {
        for (Person person : people) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
    
    public int getDegreeOfConnection(Person source, Person destination) {
       Queue<Person> queue = new LinkedList<>();
       Set<Person> visited = new HashSet<>();

       queue.add(source);
       visited.add(source);
       int degree = 0;

       while (!queue.isEmpty() && degree <= 3) {
           int levelSize = queue.size();

           for (int i = 0; i < levelSize; i++) {
               Person currentUser = queue.poll();

               if (currentUser == destination) {
                   return degree;
               }

               for (Person friend : currentUser.getFriends()) {
                   if (!visited.contains(friend)) {
                       queue.add(friend);
                       visited.add(friend);
                   }
               }
           }

           degree++;
       }

       return -1; // Return -1 if no connection is found within the maximum degree
    }
    
    public List<Person> getRecommendedFriends(int personID, boolean newUserStatus) throws Exception {
        List<Person> returnval = null;
        
        //Recommendations if the user is not new
        if (newUserStatus == false) {
            
            Person person = getPersonByID(personID);
            if (person == null) {
                throw new IllegalArgumentException("Person not found");
            }

            List<Person> recommendedFriends = new ArrayList<>();
            Set<Person> visited = new HashSet<>();
            Queue<Person> queue = new LinkedList<>();

            queue.offer(person);
            visited.add(person);

            while (!queue.isEmpty()) {
                Person currentPerson = queue.poll();
                int currentDegree = currentPerson.getDegree();

                for (Person friend : currentPerson.getFriends()) {
                    if (!visited.contains(friend)) {
                        friend.setDegree(currentDegree + 1);
                        visited.add(friend);
                        queue.offer(friend);

                        if (currentDegree + 1 > 1) {
                            recommendedFriends.add(friend);
                        }
                    }
                }
            }

            recommendedFriends.sort(new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    if (p1.getDegree() == p2.getDegree()) {
                        return p1.getName().compareToIgnoreCase(p2.getName());
                    }
                    return Integer.compare(p1.getDegree(), p2.getDegree());
                }
            });
            
            if (recommendedFriends.isEmpty()) {
                //Since an Empty List is not usable, we fallback to using the new user recommendation engine when this happens
                returnval = this.getRecommendedFriends(personID, true);
            }
            else {
                returnval =  recommendedFriends;
            }
        }
        //Recommendations if the user is new
        else if (newUserStatus == true) {
            
            Person newperson = getPersonByID(personID);
            List<Person> recommendedFriends = new ArrayList<>(people);
            recommendedFriends.removeIf(person -> person.getName().equalsIgnoreCase(newperson.getName()));

            recommendedFriends.sort(new Comparator<Person>() {
                @Override
                public int compare(Person p1, Person p2) {
                    int p1FriendsCount = p1.getFriends().size();
                    int p2FriendsCount = p2.getFriends().size();

                    if (p1FriendsCount == p2FriendsCount) {
                        return p1.getName().compareToIgnoreCase(p2.getName());
                    }

                    return Integer.compare(p2FriendsCount, p1FriendsCount);
                }
            });

            returnval = recommendedFriends;
        }
        
        //Returning the Values
        return returnval;
    }

    public Person findUser(Person source, int userID) throws Exception {
        if (source.getID() == userID) {
            return source;
        }

        Queue<Person> queue = new LinkedList<>();
        Set<Person> visited = new HashSet<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Person currentUser = queue.poll();

            if (currentUser.getID() == userID) {
                return currentUser;
            }

            for (Person friend : currentUser.getFriends()) {
                if (!visited.contains(friend)) {
                    queue.add(friend);
                    visited.add(friend);
                }
            }
        }

        return null; // User not found
    }
    
    //Regenerates Friend List to account for Updates
    public void regenFriendList(int id) throws Exception {
        //Finding current User
        Person current_user = this.getPersonByID(id);
        //Clearing their existing friends List
        current_user.clearFriendList();
        //Inserting in the Updated Friend List
        MySQLInterface sqlclient = new MySQLInterface();
        ArrayList<Integer> friends = sqlclient.returnFriends(id);
        for (int i: friends) {
            current_user.addFriend(this.getPersonByID(i));
        }
        //Closing MySQL Connection
        sqlclient.closeConnection();
    }
    
    //Implements Fuzzy Search algorithm to search amongst all the people in the network:
    public List<Person> fuzzySearch(String searchQuery) throws Exception {
        
        //Adding all names from existing People List
        List<String> names = new ArrayList<>();
        for (Person i: this.people) {
            names.add(i.getName());
        }
        
        List<String> matchingNames = new ArrayList<>();
        List<Person> matchingPeople = new ArrayList<>();

        for (String name : names) {
            if (this.isFuzzyMatch(name, searchQuery)) {
                matchingNames.add(name);
                matchingPeople.add(this.getPersonByName(name));
            }
        }

        return matchingPeople;
    }

    public boolean isFuzzyMatch(String name, String searchQuery) {
        name = name.toLowerCase();
        searchQuery = searchQuery.toLowerCase();

        int j = 0; // Index for searchQuery
        for (int i = 0; i < name.length(); i++) {
            if (j < searchQuery.length() && name.charAt(i) == searchQuery.charAt(j)) {
                j++;
            }
        }

        return j == searchQuery.length();
    }
    
    public void addMoreFriends(Person p1, Person p2) throws Exception {
        //Initializing Database Connection
        MySQLInterface sqlclient = new MySQLInterface();
        //Adding the Friend
        sqlclient.addFriend(p1, p2);
        //Adding the Friendship for each person too
        this.addFriendship(p1.getID(), p2.getID());
        //Closing sql interface
        sqlclient.closeConnection();
    }
    
    public void removeFriend(Person p1, Person p2) throws Exception {
        //Initializing Database Connection
        MySQLInterface sqlclient = new MySQLInterface();
        //Removing Friends
        sqlclient.removeFriend(p1, p2);
        //Regnerating Friend Lists for both people
        this.regenFriendList(p1.getID());
        this.regenFriendList(p2.getID());
        //Closing SQL Connection
        sqlclient.closeConnection();
    }
    
    //Accepts a Friend Request
    public void AcceptFriendRequest(int Sender, int Receiver) throws Exception {
        //Obtain the People here
        Person theonewhosent = this.getPersonByID(Sender);
        Person theonewhorecieved = this.getPersonByID(Receiver);
        //Remove the Friend Request
        MySQLInterface sqlclient = new MySQLInterface();
        sqlclient.removeFriendRequest(theonewhosent, theonewhorecieved);
        sqlclient.closeConnection();
        //Add the Friend in the Database, as well as on the network
        this.addMoreFriends(theonewhosent, theonewhorecieved);
    }
    
    //Adds a Partner relationship between two People
    public void addPartners(Person p1, Person p2, int newstatus) throws Exception {
        MySQLInterface sqlclient = new MySQLInterface();
        sqlclient.addPartner(p1.getID(), p2.getID());
        p1.addPartner(p2.getID());
        p2.addPartner(p1.getID());
        p1.UpdateRelationshipStatus(newstatus);
        p2.UpdateRelationshipStatus(newstatus);
        sqlclient.closeConnection();
    }
    
    //Removes a Partner Relationship between two People
    public void removePartners(Person p1, Person p2, int newStatus) throws Exception {
        MySQLInterface sqlclient = new MySQLInterface();
        sqlclient.removePartner(p1.getID(), p2.getID());
        p1.removePartner();
        p2.removePartner();
        p1.UpdateRelationshipStatus(newStatus);
        p2.UpdateRelationshipStatus(newStatus);
        sqlclient.closeConnection();
    }
    
    //Returns a List of people who are single and are friends of user
    public List<Person> isSingleAndFriend(Person user) {
        List<Person> returnval = new ArrayList<>();
        //Retrieve User's Friend List First
        List<Person> userfriends = user.getFriends();
        //Iterate through and check each user for whether they are single
        for (Person i: userfriends) {
            if (i.isSingle() == true) {
                returnval.add(i);
            }
        }
        return returnval;
    }
    
    //Returns a list of People currently Loaded in the Database, except for the Person who called this Function
    public List<Person> returnPeopleExceptMe(Person user) throws Exception {
        //Creating a Copy of the Person List
        List<Person> returnpeople = new ArrayList<>(this.people);
        //Removing the user who called this function from the List
        returnpeople.remove(user);
        //Returning the new list
        return returnpeople;
    }
    
    
    //Delete a Person forever from a Database (And from all instance variables)
    public void theNeuralyzer(Person user) throws Exception {
        
        //Check if any People have the User in their Relationships
        if (user.isSingle() == false && user.isComplicated() == false) {
            int partnerid = user.getPartnerID();
            Person partner = this.getPersonByID(partnerid);
            //Removing Partners First, so that both are set to Single
            this.removePartners(user, partner, 1);
        }
        
        //Delete the User from the Database
        MySQLInterface sqlinterface = new MySQLInterface();
        sqlinterface.DeleteFromDatabase(user);
        sqlinterface.closeConnection();
        
        //Adding a small time delay to account for Database updation
        Thread.sleep(6000);
        
        //Removing User from the People List
        this.people.remove(user);
        //Removing anyone who has this person in their Friends
        for (Person i: this.people) {
            this.regenFriendList(i.getID());
        }
    }
}
