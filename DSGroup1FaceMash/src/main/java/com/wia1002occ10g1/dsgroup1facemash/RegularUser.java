package com.wia1002occ10g1.dsgroup1facemash;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Harishankar Vinod and utilizes MySQL created by Tan Shu Hui
 */
public class RegularUser implements Person {
    
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
    
    private String name, bday, address, gender, username, email, creation_date, hobbies, jobtitle, company, started_date, end_date;
    private int partner_id, relationship_status_id;
    private long contact;
    private ArrayList<Person> friends;
    private int degree;
    
    public RegularUser(int id) throws Exception {
        MySQLInterface sqlinterface = new MySQLInterface();
        //Retrieving all User Details:
        String[] userinfo = sqlinterface.ReturnUserDetails(id);
        
        //Storing in an instance variables;
        this.name = userinfo[0];
        this.bday = userinfo[1];
        this.address = userinfo[2];
        this.gender = userinfo[3];
        this.username = userinfo[4];
        if (userinfo[5] != null) {
            this.contact = Long.parseLong(userinfo[5]);
        }
        this.email = userinfo[6];
        this.creation_date = userinfo[7];
        this.hobbies = userinfo[8];
        this.jobtitle = userinfo[9];
        this.company = userinfo[10];
        this.started_date = userinfo[11];
        this.end_date = userinfo[12];
        if (userinfo[13] != null) {
            this.partner_id = Integer.parseInt(userinfo[13]);
        }
        else if (userinfo[13] == null ) {
            this.partner_id = 0; //Partner id of 0 indicates no partner
        }
        if (userinfo[14] != null) {
            if (userinfo[13] == null) {
                this.relationship_status_id = 1; //to handle case where no partner info but relationship status id is present
            }
            else {
                this.relationship_status_id = Integer.parseInt(userinfo[14]);
            }
        }
        else if (userinfo[14] == null) {
            this.relationship_status_id = 1; //Default is kept as Single
        }
        
        sqlinterface.closeConnection();
        
        this.friends = new ArrayList();
        this.degree = 0;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addFriend(Person friend) {
        friends.add(friend);
    }    
    
    @Override
    public List<Person> getFriends() {
        return friends;
    }
    
    //Uses the same indexing method as given above
    @Override
    public String[] RetrievePersonDetails() {
        String[] returnval = {name, bday, address, gender, username,Long.toString(contact), email, creation_date, hobbies, jobtitle, company, started_date, end_date, Integer.toString(partner_id), Integer.toString(relationship_status_id)};
        return returnval;
    }
    
    @Override
    public int getID() throws Exception {
        MySQLInterface sqlinterface = new MySQLInterface();
        int returnval =  sqlinterface.ReturnDatabaseID(this.email);
        sqlinterface.closeConnection();
        return returnval;
    }    
    
    @Override
    public void setDegree(int degree) {
        this.degree = degree;
    }
    
    @Override
    public int getDegree() {
        return this.degree;
    }
    
    @Override
    public void clearFriendList() {
        //Clearing List
        this.friends.clear();
    }
    
    @Override
    public void addPartner(int PartnerID) {
        //Adding Partner here
        this.partner_id = PartnerID;
    }
    
    @Override
    public void removePartner() {
        //Removing Partner here
        this.partner_id = 0; //Resetting to default no partner status
    }
    
    @Override
    public void UpdateRelationshipStatus(int newStatus) throws Exception {
        //Updating Relationship status here:
        this.relationship_status_id = newStatus;
        //Obtaining Current user ID here
        int user_id = this.getID();
        //Updating Relationship Status
        MySQLInterface sqlclient = new MySQLInterface();
        sqlclient.updateRelationshipStatus(user_id, newStatus);
        sqlclient.closeConnection();
    }
    
    @Override
    public boolean isSingle() {
        boolean returnval = false;
        if (this.relationship_status_id == 1) {
            returnval = true;
        }
        return returnval;
    }
    
    @Override
    public boolean isComplicated() {
        boolean returnval = false;
        if (this.relationship_status_id == 4) {
            returnval = true;
        }
        return returnval;
    }
    
    @Override
    public void UpdateSpecificDetails(String[] updates) throws Exception {
        //Initializing MySQl Interface for Month Conversion
        MySQLInterface sql = new MySQLInterface();
        //Obtaining Values from Inserted Array
        String address1 = updates[0];
        String hobbies1 = updates[1];
        String jobtitle1 = updates[2];
        String company1 = updates[3];
        int sDay = Integer.parseInt(updates[4]);
        int sMonth = sql.convertmonth(updates[5]);
        int sYear = Integer.parseInt(updates[6]);
        int eDay = Integer.parseInt(updates[7]);
        int eMonth = sql.convertmonth(updates[8]);
        int eYear = Integer.parseInt(updates[9]);
        
        //Making start and end dates:
        String sDate = Integer.toString(sYear)+"-"+Integer.toString(sMonth)+"-"+Integer.toString(sDay);
        String eDate = Integer.toString(eYear)+"-"+Integer.toString(eMonth)+"-"+Integer.toString(eDay);
        
        //Updating All values
        this.address = address1;
        this.hobbies = hobbies1;
        this.jobtitle = jobtitle1;
        this.company = company1;
        this.started_date = sDate;
        this.end_date = eDate;
        
        //Closing SQL Connection
        sql.closeConnection();
    }
    
    @Override
    public int getPartnerID() {
        return this.partner_id;
    }
}
