package com.wia1002occ10g1.dsgroup1facemash;

/**
 *
 * @author Tan Shu Hui, Harishankar Vinod
 */

import java.sql.*;
import java.util.ArrayList;

public class MySQLInterface {
    
    //This username and password has been defined in MySQL as a user to work correctly
    private String user = "useyourownusernamehere";
    private String pwd = "useyourownpasswordhere";
    private Connection con;
    
    public MySQLInterface() throws Exception {
        
        //Calling the Driver Class
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        //Initializing Connection
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facemash",user, pwd);
    }
    
    //Adds data into the User and User_credentials Table
    public void addUserData(String[] returnarray) throws Exception {
        
        //Obtaining all the values from the Inserted Array
        String email = returnarray[0];
        String passwd = returnarray[1];
        long Contact = Long.parseLong(returnarray[2]);
        String username = returnarray[3];
        String name = returnarray[4];
        int bDay = Integer.parseInt(returnarray[5]);
        String bMonth = returnarray[6];
        int bYear = Integer.parseInt(returnarray[7]);
        String Gender = returnarray[8];
        String address = returnarray[9];
        
        //Encrypting the Password Recieved
        String pwd = AES256.encrypt(passwd);
        
        //Initializing the Insert System
        Statement statement = con.createStatement();
        
        //Finding an empty spot to add values in the table
        int id = this.giveAnID();
        
        //Defining values to insert into the User table
        String date = Integer.toString(bYear)+"-"+Integer.toString(convertmonth(bMonth))+"-"+Integer.toString(bDay);
        String query = "insert into user values ('"+id+"', '"+name+"', '"+date+"', '"+address+"', '"+Gender+"', 0)";
        
        //Executing the Query for the User table
        statement.executeUpdate(query);
        
        //Inserting values into the user_credential table
        String current_date = java.time.LocalDate.now().toString();
        String query2 = "insert into user_credential values ('"+id+"', '"+username+"', '"+Contact+"', '"+email+"', '"+pwd+"', '"+current_date+"', '"+current_date+"', '"+id+"')";
        
        //Executing the Query for the user_credential table
        statement.executeUpdate(query2);
        
        //Inserting values into the user_roles table
        //All users are registered as Regular Users by default
        String query3 = "insert into user_roles values ('"+id+"','1')";
        statement.executeUpdate(query3);
        
        //Inserting Relationship status as single in the user_relationships table (default is single)
        String query4 = "insert into user_relationships values ('"+id+"','1')";
        statement.executeUpdate(query4);
    }
    
    //Adds data to all other tables for new user creation:
    public void addExtraDetailsData(String[] returnarray, String email) throws Exception {
        
        //Obtaining Values from inserted Array
        String hobbies = returnarray[0];
        String Jobtitle = returnarray[1];
        String Company = returnarray[2];
        int sDay = Integer.parseInt(returnarray[3]);
        int sMonth = this.convertmonth(returnarray[4]);
        int sYear = Integer.parseInt(returnarray[5]);
        int eDay = Integer.parseInt(returnarray[6]);
        int eMonth = this.convertmonth(returnarray[7]);
        int eYear = Integer.parseInt(returnarray[8]);
        
        //Finding ID
        int id = this.ReturnDatabaseID(email);
        
        //Making start and end dates:
        String sDate = Integer.toString(sYear)+"-"+Integer.toString(sMonth)+"-"+Integer.toString(sDay);
        String eDate = Integer.toString(eYear)+"-"+Integer.toString(eMonth)+"-"+Integer.toString(eDay);
        
        //Initializing Insert System:
        Statement statement = con.createStatement();
        
        //Inserting values into hobbies table
        String query = "insert into user_hobbies values ('"+id+"','"+hobbies+"')";
        statement.executeUpdate(query);
        
        //Inserting values into the user_jobs table:
        String query2 = "insert into user_jobs values ('"+id+"', '"+Jobtitle+"', '"+Company+"','"+sDate+"','"+eDate+"')";
        statement.executeUpdate(query2);
        
    }
    
    //Returns a Decrypted Password for a given Email
    public String decryptPassword(String email) throws Exception {
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("select password from user_credential where email = '"+email+"'");
        String A = null;
        while (result.next()) {
            A = result.getString("password");
        }
        return AES256.decrypt(A);
    }
    
    //Checks if the given email id is present or not
    public boolean isEmailPresent(String email) throws Exception {
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("select * from user_credential where email = '"+email+"'");
        String A = null;
        while (result.next()) {
            A = result.getString("email");
        }
        if (A == null) {
            return false;
        }
        else {
            return true;
        }
    }
    
    //Checks the next available id in the database
    public int giveAnID() throws Exception {
        int i=1;
        boolean A = false;
        while (true) {
            A = this.CheckEmpty(i);
            if (A == true) {
                return i;
            }
            else if (A == false) {
                i++;
                continue;
            }
        }
    }
    
    //Checks whether a given id is available or not
    public boolean CheckEmpty(int id) throws Exception {
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("select * from user where id ="+id);
        String A = null;
        while (result.next()) {
            A = result.getString("id");
        }
        if (A == null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //Returns User ID for a given Email
    //As per Specification:
    // - 0 for Admin Role
    // - 1 for User Role
    public int ReturnUserRole(String email) throws Exception {
        Statement statement  = con.createStatement();
        int id = this.ReturnDatabaseID(email);
        ResultSet result = statement.executeQuery("select role_id from user_roles where user_id = "+id);
        String A = null;
        while (result.next()) {
            A = result.getString("role_id");
        }
        return Integer.parseInt(A);
    }
    
    //Same as above, just returns on the basis of Database ID
    public int ReturnUserRole(int id) throws Exception {
        Statement statement  = con.createStatement();
        ResultSet result = statement.executeQuery("select role_id from user_roles where user_id = "+id);
        String A = null;
        while (result.next()) {
            A = result.getString("role_id");
        }
        return Integer.parseInt(A);
    }
    
    //Returns user ID for a given Email
    public int ReturnDatabaseID(String email) throws Exception {
        Statement statement  = con.createStatement();
        ResultSet result = statement.executeQuery("select id from user_credential where email = '"+email+"'");
        String A = null;
        while (result.next()) {
            A = result.getString("id");
        }
        return Integer.parseInt(A);
    }
    
    //Returns User Details for a given User ID in the form of a String array (except for Friends Info)
    //This method returns select details from all tables and instantiates them in instance variables for quick usage;
    //The Returned String is an array with the following info:
    // index 0 : name
    // index 1 : bday
    // index 2 : address
    // index 3 : gender
    // index 4 : username
    // index 5 : contact
    // index 6 : email
    // index 7 : creation_date
    // index 8 : hobbies (in csv format)
    // index 9 : jobtitle
    // index 10: company
    // index 11: started_date
    // index 12: end_date
    // index 13: partner_id
    // index 14: relationship_status_id
    public String[] ReturnUserDetails(int id) throws Exception {
        //Return String array
        String[] returnarray = new String[15];
        
        //Creating Connection
        Statement statement = con.createStatement();
        
        //Returning Values from user table:
        String userquery = "select name, birthday, Address, gender from user where id = "+id;
        ResultSet uRS = statement.executeQuery(userquery);
        while (uRS.next()) {
            returnarray[0] = uRS.getString("name");
            returnarray[1] = uRS.getString("birthday");
            returnarray[2] = uRS.getString("Address");
            returnarray[3] = uRS.getString("gender");
        }
        
        //Returning values from user_credential table
        String usercredquery = "select username, contact, email, creation_date from user_credential where id = "+id;
        ResultSet uCRS = statement.executeQuery(usercredquery);
        while (uCRS.next()) {
            returnarray[4] = uCRS.getString("username");
            returnarray[5] = uCRS.getString("contact");
            returnarray[6] = uCRS.getString("email");
            returnarray[7] = uCRS.getString("creation_date");
        }
        
        //Returning values from user_hobbies
        String userhobbyquery = "select hobbies from user_hobbies where user_id = "+id;
        ResultSet uHRS = statement.executeQuery(userhobbyquery);
        while (uHRS.next()) {
            returnarray[8] = uHRS.getString("hobbies");
        }
        
        //Returning values from User_Jobs
        String userjobsquery = "select jobtitle, company, started_date, end_date from user_jobs where user_id = "+id;
        ResultSet uJRS = statement.executeQuery(userjobsquery);
        while (uJRS.next()) {
            returnarray[9] = uJRS.getString("jobtitle");
            returnarray[10] = uJRS.getString("company");
            returnarray[11] = uJRS.getString("started_date");
            returnarray[12] = uJRS.getString("end_date");
        }
        
        //Returning values from user_partner
        String userpartnerquery = "select partner_id from user_partner where user_id = "+id+" UNION select user_id from user_partner where partner_id = "+id;
        ResultSet uPRS = statement.executeQuery(userpartnerquery);
        while (uPRS.next()) {
            returnarray[13] = uPRS.getString("partner_id");
        }
        
        //Returning values from Relationship Status
        String userrelationquery = "select relationship_status_id from user_relationships where user_id = "+id;
        ResultSet uRRS = statement.executeQuery(userrelationquery);
        while (uRRS.next()) {
            returnarray[14] = uRRS.getString("relationship_status_id");
        }
        
        //Returning all values back
        return returnarray;
    }
    
    //Returns a 2-D array of friend ids to be added into the Social Network
    public int[][] InitiateNetworkFriends() throws Exception{
        Statement statement = con.createStatement();
        //Finding number of friends to be added to the network
        String countquery = "select count(user_id) from user_friends";
        ResultSet countinfo = statement.executeQuery(countquery);
        int count = 0;
        while (countinfo.next()) {
            count = countinfo.getInt("count(user_id)");
        }
        
        //Return Value:
        int[][] UserFriends = new int[count][2];
        
        String friendpairsquery = "select * from user_friends";
        ResultSet friendsinfo = statement.executeQuery(friendpairsquery);
        
        int xcount = 0;
        while (friendsinfo.next()) {
            UserFriends[xcount][0] = friendsinfo.getInt("user_id");
            UserFriends[xcount][1] = friendsinfo.getInt("friend_id");
            xcount++;
        }
        
        return UserFriends;
    }
    
    //Returns all the friends a person has in their friends list
    public ArrayList<Integer> returnFriends(int id) throws Exception {
        Statement statement = con.createStatement();
        ArrayList<Integer> returnval = new ArrayList<>();
        //Query to return all the friends of a person
        String friendidquery = "select friend_id from user_friends where user_id = "+id+" UNION select user_id from user_friends where friend_id = "+id;
        ResultSet friendidinfo = statement.executeQuery(friendidquery);
        //Returning Values
        while (friendidinfo.next()) {
            returnval.add(friendidinfo.getInt("friend_id"));
        }
        //Returning Friends ArrayList
        return returnval;
    }
    
    //Adds a Friend connection in the Database
    public void addFriend(Person p1, Person p2) throws Exception{
        //Getting IDs for each Person
        int id1 = p1.getID();
        int id2 = p2.getID();
        //Creating Statement
        Statement statement = con.createStatement();
        //Query to be used:
        String addFriendquery = "insert into user_friends values ("+id1+","+id2+")";
        statement.executeUpdate(addFriendquery);
    }
    
    //To Remove a Friend Connection in the Database
    public void removeFriend(Person toBeRemoved, Person whoWantsToRemove) throws Exception {
        //Getting iD of the People
        int personToBeRemovedID = toBeRemoved.getID();
        int personRemovingID = whoWantsToRemove.getID();
        //Creating Statement
        Statement statement = con.createStatement();
        //Creating two queries for this purpose
        //Idea being, that one of them will remove 0 rows, while the other will definitely remove 1 row
        String query1 = "delete from user_friends where user_id = "+personToBeRemovedID+" and friend_id = "+personRemovingID;
        String query2 = "delete from user_friends where friend_id = "+personToBeRemovedID+" and user_id = "+personRemovingID;
        //Running both queries, because one of them will work
        statement.executeUpdate(query1);
        statement.executeUpdate(query2);
        
    }
    
    //Adds a Friend Request into the user_friend_requests database
    public void sendFriendRequest(Person from, Person to) throws Exception {
        //Getting IDs of each
        int fromid = from.getID();
        int toid = to.getID();
        //Checking the Person already has a Friend Request From this person:
        ArrayList<Integer> frenreqs = this.retrieveFriendRequests(to);
        if (frenreqs.contains(fromid) == false) {
            //Creating Statement
            Statement statement = con.createStatement();
            //Query to be used:
            String sendFriendReqQuery = "insert into user_friend_requests values ("+fromid+","+toid+")";
            statement.executeUpdate(sendFriendReqQuery);
        }
    }
    
    //Removes a Friend Request from user_friend_requests database
    public void removeFriendRequest(Person toBeRemoved, Person whoWantsToRemove) throws Exception {
        //Getting iD of the People
        int personToBeRemovedID = toBeRemoved.getID();
        int personRemovingID = whoWantsToRemove.getID();
        //Creating Statement
        Statement statement = con.createStatement();
        //Query to be used:
        String removeFrenReq = "delete from user_friend_requests where from_id = "+personToBeRemovedID+" and to_id = "+personRemovingID;
        statement.executeUpdate(removeFrenReq);
    }
    
    //Adds a Partner into the Database
    public void addPartner(int tobeAddedID, int whoisAddingID) throws Exception {
        //Creating Statement
        Statement statement = con.createStatement();
        //Query to be used:
        String addPartner = "insert into user_partner values ("+whoisAddingID+", "+tobeAddedID+")";
        statement.executeUpdate(addPartner);
    }
    
    public void removePartner(int partnertoRemove, int whoisRemoving) throws Exception {
        Statement statement = con.createStatement();
        //Query to be used:
        String removepartner1 = "delete from user_partner where user_id = "+partnertoRemove+" and partner_id = "+whoisRemoving;
        String removepartner2 = "delete from user_partner where user_id = "+whoisRemoving+" and partner_id = "+partnertoRemove;
        //Executing Commands (as either one of them will lead to succesful deletion)
        statement.executeUpdate(removepartner1);
        statement.executeUpdate(removepartner2);
    }
    
    //Updates the Relationship status of the Person
    public void updateRelationshipStatus(int User, int newStatus) throws Exception {
        Statement statement = con.createStatement();
        //Statement for achieving this
        String query = "update user_relationships set relationship_status_id = "+newStatus+" where user_id = "+User;
        //Executing Query
        statement.executeUpdate(query);
    }
    
    //Retrieves all Friend Requests that a person has, as an ArrayList of IDs:
    public ArrayList<Integer> retrieveFriendRequests(Person user) throws Exception {
        //Declaring Return Value
        ArrayList<Integer> returnval = new ArrayList<>();
        //Getting ID of the Person
        int user_id = user.getID();
        //Creating statement
        Statement statement = con.createStatement();
        //Query to be used:
        String retrieverequests = "select from_id from user_friend_requests where to_id = "+user_id;
        ResultSet requests = statement.executeQuery(retrieverequests);
        while (requests.next()) {
            returnval.add(requests.getInt("from_id"));
        }
        return returnval;
    }
    
    //Returns an array of user database IDs to instantiate the network
    public int[] DatabaseIDList() throws Exception {
        //Finding Number of users first:
        int size = this.DatabaseUserCount();
        Statement statement = con.createStatement();
        //Issuing Query to return all IDs
        String useridlistquery = "select id from user";
        ResultSet values = statement.executeQuery(useridlistquery);
        int[] returnval = new int[size];
        int x = 0;
        while (values.next()) {
            returnval[x] = values.getInt("id");
            x++;
        }
        return returnval;
    }
    
    //Returns a count of number of Users in the Database
    public int DatabaseUserCount() throws Exception {
        Statement statement = con.createStatement();
        //Issuing count query
        String usercountquery = "select count(id) from user";
        ResultSet numbers = statement.executeQuery(usercountquery);
        int returnval = 0;
        while (numbers.next()) {
            returnval = numbers.getInt("count(id)");
        }
        return returnval;
    }
    
    //Updates select info in the given Database about a user
    //Updates Address, hobby, jobtitle, company, started_date, end_date
    public void UpdateSpecificDetails(String[] updates, Person user) throws Exception {
        
        //Returning Person ID
        int userid = user.getID();
        
        //Obtaining Values from Inserted Array
        String address = updates[0];
        String hobbies = updates[1];
        String jobtitle = updates[2];
        String company = updates[3];
        int sDay = Integer.parseInt(updates[4]);
        int sMonth = this.convertmonth(updates[5]);
        int sYear = Integer.parseInt(updates[6]);
        int eDay = Integer.parseInt(updates[7]);
        int eMonth = this.convertmonth(updates[8]);
        int eYear = Integer.parseInt(updates[9]);
        
        //Making start and end dates:
        String sDate = Integer.toString(sYear)+"-"+Integer.toString(sMonth)+"-"+Integer.toString(sDay);
        String eDate = Integer.toString(eYear)+"-"+Integer.toString(eMonth)+"-"+Integer.toString(eDay);
        
        //Initializing Insert System:
        Statement statement = con.createStatement();
        
        //Updating Hobby Table values
        String query = "update user_hobbies set hobbies = '"+hobbies+"' where user_id = "+userid;
        statement.executeUpdate(query);
        
        //Updating Address Values
        String query3 = "update user set Address = '"+address+"' where id = "+userid;
        statement.executeUpdate(query3);
        
        //Updating User Jobs Table:
        String query2 = "update user_jobs set jobtitle = '"+jobtitle+"', company = '"+company+"', started_date = '"+sDate+"', end_date = '"+eDate+"' where user_id = "+userid;
        statement.executeUpdate(query2);
    }
    
    //Deletes Users from Facemash (ADMIN PRIVELIGES ONLY)
    public void DeleteFromDatabase(Person user) throws Exception {
        
        //Returning Person ID:
        int userid = user.getID();
        
        //Issuing Delete Command (which deletes everywhere else too because of Cascading)
        Statement statement = con.createStatement();
        String query = "delete from user where id = "+userid;
        statement.executeUpdate(query);
    }
    
    //Closes the MySQL connection safely
    public void closeConnection() throws Exception {
        this.con.close();
    }
    
    //Converts Months from String to Numbers
    public int convertmonth(String month)  {
        int returnval = 0;
        switch (month) {
            case "January":
                returnval = 1;
                break;
            case "February":
                returnval = 2;
                break;
            case "March":
                returnval = 3;
                break;
            case "April":
                returnval = 4;
                break;
            case "May":
                returnval = 5;
                break;
            case "June":
                returnval = 6;
                break;
            case "July":
                returnval = 7;
                break;
            case "August":
                returnval = 8;
                break;
            case "September":
                returnval = 9;
                break;
            case "October":
                returnval = 10;
                break;
            case "Novemeber":
                returnval = 11;
                break;
            case "December":
                returnval = 12;
                break;
        }
        return returnval;
    }
}