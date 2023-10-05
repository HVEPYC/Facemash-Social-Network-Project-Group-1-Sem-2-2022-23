package com.wia1002occ10g1.dsgroup1facemash;

/**
 *
 * @author Group 1 of WIA1002 OCC 10
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class mainprogram {
    public static void main(String[] args) throws Exception{
        
        //Declarations:
        WindowManager A = new WindowManager(); //Creates a Window Object
        MySQLInterface sqlmanager = new MySQLInterface(); //Creates a new MySQL Session with the MySQLInterface
        boolean isNewUser = false; //To check if the account created is a new one or not
        
        //Main Program Loop:
        mainloop:
        while (true) {
            
            //Displaying the Start Menu
            String output = A.StartMenu();
            
            //Using the Button Data from the Menu to determine menus to be opened.
            if (output.equals("login")) {
                //Starting Login Menu when needed
                //Forever Login Loop which breaks only when correct credentials used.
                loginloop:
                while (true) {
                    String object2 = A.LoginMenu();
                    if (object2.equals("close")) {
                        break loginloop;
                    }
                    else {
                        //Returning Login Info from Menu
                        String[] dataarray = A.getDataCarrierData();
                        String emailreturned = dataarray[0];
                        String pwdreturned = dataarray[1];
                        if (sqlmanager.isEmailPresent(emailreturned) == true) {
                            String pass = sqlmanager.decryptPassword(emailreturned);
                            if (pwdreturned.equals(pass) == true) {
                                A.ErrorPopup("Access Granted");
                                
                                //Main Window Code Here:
                                int mainprogstatus = mainwindowcode(emailreturned, isNewUser);
                                if (mainprogstatus == 0) {
                                    break loginloop;
                                }
                                else if (mainprogstatus == 1) {
                                    isNewUser = false;
                                    continue mainloop;
                                }
                            }
                            else if (pwdreturned.equals(pass) == false) {
                                A.ErrorPopup("Incorrect Password");
                                continue loginloop;
                            }
                        }
                        else if (sqlmanager.isEmailPresent(emailreturned) == false) {
                            A.ErrorPopup("This Email does not exist in our Database");
                            continue loginloop;
                        }
                    }
                }
                break mainloop;
            }
            else if (output.equals("newuser")) {
                if (isNewUser == true) {
                    A.ErrorPopup("Login With your newly Created Account First");
                    continue mainloop;
                }
                else if (isNewUser == false) {
                    //Starting Sign Up Menu when Required
                    newuserloop:
                    while (true) {
                        String object3 = A.SignUpMenu();
                        if (object3.equals("close")) {
                            break newuserloop;
                        }
                        else {
                            //Returning data from User
                            String[] dataarray = A.getDataCarrierData();
                            String emailreturned = dataarray[0];
                            if (sqlmanager.isEmailPresent(emailreturned) == false) {
                                //Adding data to SQL
                                sqlmanager.addUserData(dataarray);
                                A.ErrorPopup("New User Registered Successfully");
                                isNewUser = true;
                                continue mainloop;
                                //The Program Goes back to the Main Loop, where the User can now log into their account
                            }
                            else if (sqlmanager.isEmailPresent(emailreturned) == true) {
                                A.ErrorPopup("This Email Already Exists, use another Email");
                                continue newuserloop;
                            }
                        }
                    }
                    break mainloop; 
                }
            }
            else if (output.equals("close")) {
                break mainloop;
            }
        }
        //Since Programs terminate here, the Connection is to be closed here.
        sqlmanager.closeConnection();
    }
    
    
    //Main Program Loop:
    //==================
    
    //All the code for the Main Windows, including Traceback, is contained here.
    //Regarding the traceback, a system is created where a Linked List contains all the windows defined as codes.
    
    //Regarding Return Codes, program is coded as:
    // - Closure of Window = Automatic Logout and also returns Close Code 0 (Terminates the above infinite Loops)
    // - LogOut from the Session = Logout and returns a Logout Code 1 (Continues the Login loop and sends back to the original loop)
    public static int mainwindowcode(String email, boolean newAccStatus) throws Exception {
        
        //Declarations:
        WindowManager windows = new WindowManager(); //Creates a Window Object
        MySQLInterface sqlmanagerwindows = new MySQLInterface(); //Creates a new MySQL Session with the MySQLInterface
        UserFactory usercreator = new UserFactory(); //Creating a new Person Generator
        int winreturnval = 0; //For returning window details
        boolean hasFriends = false; //To check if user has friends yet
        
        //Section to add more User Details (if new account)
        if (newAccStatus == true) {
            windows.ExtraDetailsMenu();
            String[] extraDetails = windows.getDataCarrierData();
            sqlmanagerwindows.addExtraDetailsData(extraDetails, email);
        }
        
        //Initalize Friends Network
        TheSocialNetwork network = new TheSocialNetwork();
        int[] idlist = sqlmanagerwindows.DatabaseIDList();
        
        //Adding everyone to the Network
        for (int i: idlist) {
            network.addPerson(usercreator.CreateUser(sqlmanagerwindows.ReturnUserRole(i), i));
        }
        
        //Adding friendships into the network
        int[][] friendlist = sqlmanagerwindows.InitiateNetworkFriends();
        for (int i=0; i<friendlist.length; i++) {
            network.addFriendship(friendlist[i][0], friendlist[i][1]);
        }
        
        //Getting current user:
        Person current_user = network.getPersonByID(sqlmanagerwindows.ReturnDatabaseID(email));
        
        //Checking if person has friends or not (For Homepage Recommendations) (Inverted for Correct Results)
        if (current_user.getFriends().isEmpty() == true) {
            hasFriends = true;
        }
        else {
            hasFriends = false;
        }
        
        //Creating Traceback Linked List
        LinkedList<tracebackwindowdetails> TracebackWindows = new LinkedList<>();
        //The codes for Each window are as follows:
        //- Code 0: HomePage (DONE)
        //- Code 1: View Acc Page (DONE)
        //- Code 2: Manage Account Page (DONE)
        //- Code 3: Friend Requests Page (DONE)
        //- Code 4: Admin Management Window (DONE)
        //- Code 5: Search Results Window (DONE)
        
        //Adding the homepage in traceback since its the first page after all
        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0)); //Added Homepage Code Number in LinkedList
        
        //Main window Code:
        windowloop:
        while (true) {
            
            //Peeking the topmost window
            int windowtoshow = 0;
            if (TracebackWindows.isEmpty() == true) {
                TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                windowtoshow = TracebackWindows.getLast().getWindowID();
            }
            else {
                windowtoshow = TracebackWindows.getLast().getWindowID();
            }
            
            switch (windowtoshow) {
                
                //The Main Window
                case 0:
                    List recommendations = network.getRecommendedFriends(current_user.getID(), hasFriends);
                    String homepagereturn = windows.HomePage(recommendations, current_user);
                    if (homepagereturn.equals("close") || homepagereturn.equals("logout")) {
                        if (homepagereturn.equals("logout")) {
                            winreturnval = 1;
                            break windowloop;
                        }
                        else {
                            winreturnval = 0;
                            break windowloop;
                        }
                    }
                    else if (homepagereturn.equals("back")) {
                        TracebackWindows.removeLast();
                        continue windowloop;
                    }
                    else if (homepagereturn.equals("search")) {
                        //Invoking Search Box window here:
                        String searchQuery = windows.SearchBox();
                        //Sending search box query info to the Tracebackwindow manager, which will make the Results show up
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(5, searchQuery));
                        continue windowloop;
                    }
                    else if (homepagereturn.equals("manage")) {
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(2));
                        continue windowloop;
                    }
                    else if (homepagereturn.equals("frenreqs")) {
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(3));
                        continue windowloop;
                    }
                    else if (homepagereturn.equals("admin")) {
                        if (current_user instanceof AdminUser) {
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(4));
                            continue windowloop;
                        }
                        else if (current_user instanceof RegularUser) {
                            windows.ErrorPopup("You do not have Admin Permissions to Access this Window");
                            continue windowloop;
                        }
                    }
                    else if (homepagereturn.substring(0,4).equals("view")) {
                        String acctoview = homepagereturn.replaceAll("[^0-9]", "");
                        Person Personretrieved = (Person) recommendations.get(Integer.parseInt(acctoview));
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(1, Personretrieved));
                        continue windowloop;
                    }
                    continue windowloop;
                
                //View Account Page
                case 1:
                    
                    //Retrieve the user being viewed:
                    Person user_to_view = TracebackWindows.getLast().getPersonBeingViewed();
                    
                    //Retrieve the Friends of the User being Viewed:
                    List<Person> friends_of_account = user_to_view.getFriends();
                    
                    //Retrieve User Details and making small data correction
                    String[] userdetails = user_to_view.RetrievePersonDetails();
                    if (userdetails[13].equals("0")) {
                        userdetails[13] = "No Partner";
                    }
                    else {
                        userdetails[13] = network.getPersonByID(Integer.parseInt(userdetails[13])).getName();
                    }
                    
                    //Check if the account being viewed is an account which isnt the same as the current_user
                    if (current_user.getID() == user_to_view.getID()) {
                        //Open Window
                        String viewstatus = windows.ViewAccount(user_to_view, friends_of_account, userdetails);
                        if (viewstatus.equals("home")) {
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                            continue windowloop;
                        }
                        else if (viewstatus.equals("back")) {
                            TracebackWindows.removeLast();
                            continue windowloop;
                        }
                        else if (viewstatus.equals("close")) {
                            winreturnval = 0;
                            break windowloop;
                        }
                        else if (viewstatus.equals("edit")) {
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(2));
                            continue windowloop;
                        }
                        else if (viewstatus.substring(0,6).equals("friend")) {
                            String acctoview = viewstatus.replaceAll("[^0-9]", "");
                            Person PersonRetrieved = friends_of_account.get(Integer.parseInt(acctoview));
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(1, PersonRetrieved));
                            continue windowloop;
                        }
                    }
                    else {
                        //Check if user being viewed is a Friend of current user
                        boolean isFriend = false;
                        if (friends_of_account.contains(current_user)) {
                            isFriend = true;
                        }
                        //Obtain Degree of Connection
                        Person user2 = network.findUser(current_user,user_to_view.getID());
                        int degree = network.getDegreeOfConnection(current_user, user2);
                        //Retrieve Mutual Friends
                        List<Person> mutualFriends = network.getMutualFriends(current_user.getID(), user_to_view.getID());
                        //Open Window
                        String viewstatus = windows.ViewAccount(user_to_view, friends_of_account, mutualFriends, userdetails, degree, isFriend);
                        if (viewstatus.equals("home")) {
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                            continue windowloop;
                        }
                        else if (viewstatus.equals("back")) {
                            TracebackWindows.removeLast();
                            continue windowloop;
                        }
                        else if (viewstatus.equals("close")) {
                            winreturnval = 0;
                            break windowloop;
                        }
                        else if (viewstatus.substring(0,6).equals("friend")) {
                            String acctoview = viewstatus.replaceAll("[^0-9]", "");
                            Person PersonRetrieved = friends_of_account.get(Integer.parseInt(acctoview));
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(1, PersonRetrieved));
                            continue windowloop;
                        }
                        else if (viewstatus.substring(0,6).equals("mutual")) {
                            String acctoview = viewstatus.replaceAll("[^0-9]", "");
                            Person PersonRetrieved = mutualFriends.get(Integer.parseInt(acctoview));
                            TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(1, PersonRetrieved));
                            continue windowloop;
                        }
                        else if (viewstatus.equals("addfriend")) {
                            sqlmanagerwindows.sendFriendRequest(current_user, user_to_view);
                            windows.ErrorPopup("Friend Request Sent!");
                            continue windowloop;
                        }
                        else if (viewstatus.equals("removefriend")) {
                            network.removeFriend(current_user, user_to_view);
                            continue windowloop;
                        }
                    }
                    continue windowloop;
                    
                //Manage Account Page Here
                case 2:
                    
                    //Calling First Option Menu:
                    String editstatus = windows.EditAccountChoice();
                    
                    //Considering all Cases of editstatus
                    if (editstatus.equals("home")) {
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                        continue windowloop;
                    }
                    else if (editstatus.equals("back")) {
                        TracebackWindows.removeLast();
                        continue windowloop;
                    }
                    else if (editstatus.equals("close")) {
                        winreturnval = 0;
                        break windowloop;
                    }
                    else if (editstatus.equals("accdetails")) {
                        
                        //Starting Account Modification Section
                        String accdetailsstatus = windows.EditAccount();
                        if (accdetailsstatus.equals("save")) {
                            //Retrieve Data Stored First
                            String[] updates = windows.getDataCarrierData();
                            //Update details in SQL
                            sqlmanagerwindows.UpdateSpecificDetails(updates, current_user);
                            //Update details for current user as well:
                            current_user.UpdateSpecificDetails(updates);
                            //Go back to Edit Account Window:
                            continue windowloop;
                        }
                        else if (accdetailsstatus.equals("cancel")) {
                            //Go back to Edit Account Window
                            continue windowloop;
                        }
                    }
                    //Add Section for Managing Relationship Status Here
                    else if (editstatus.equals("rstatus")) {
                        
                        //Retrieving Friends of user who are Single
                        List<Person> isFriendAndSingle = network.isSingleAndFriend(current_user);
                        //Starting Account Modification Window which sends all the above info
                        String rstatus = windows.EditRelationshipStatus(isFriendAndSingle, current_user);
                        
                        //Accounting for All Cases
                        if (rstatus.equals("cancel")) {
                            //Go back to Edit Account Window
                            continue windowloop;
                        }
                        //Case when Single is Chosen
                        else if (rstatus.equals("save1")) {
                            //Checking if Person already is in a Relationship
                            if (current_user.isSingle() == true || current_user.isComplicated() == true) {
                                current_user.UpdateRelationshipStatus(1);
                                windows.ErrorPopup("Updated Successfully");
                                continue windowloop;
                            }
                            else if (current_user.isSingle() == false && current_user.isComplicated() == false) {
                                //Getting Partner ID first:
                                int partnerid = current_user.getPartnerID();
                                Person partner = network.getPersonByID(partnerid);
                                //Removing Partners First, so that both are set to Single
                                network.removePartners(current_user, partner, 1);
                                windows.ErrorPopup("Updated Successfully");
                                continue windowloop;
                            }
                        }
                        //Case when It's Complicated is Chosen:
                        else if (rstatus.equals("save4")) {
                            //Checking if Person already is in a Relationship
                            if (current_user.isSingle() == true || current_user.isComplicated() == true) {
                                current_user.UpdateRelationshipStatus(4);
                                windows.ErrorPopup("Updated Successfully");
                                continue windowloop;
                            }
                            else if (current_user.isSingle() == false && current_user.isComplicated() == false) {
                                //Getting Partner ID first:
                                int partnerid = current_user.getPartnerID();
                                Person partner = network.getPersonByID(partnerid);
                                //Removing Partners First, so that both are set to Single
                                network.removePartners(current_user, partner, 4);
                                windows.ErrorPopup("Updated Successfully");
                                continue windowloop;
                            }
                        }
                        //Case when Committed Relationship is Chosen:
                        else if (rstatus.equals("save2")) {
                            //Checking if the user is Married or in a Committed Relationship first
                            if (current_user.isSingle() == false && current_user.isComplicated() == false) {
                                windows.ErrorPopup("Set your Status to Single before doing this");
                                continue windowloop;
                            }
                            else {
                                //Retrieve Partner Name First:
                                String partnername = windows.getDataCarrierData()[0];
                                //Retrieve the Person based on that name
                                Person partnertobe = network.getPersonByName(partnername.trim());
                                //Add them as partners:
                                network.addPartners(current_user, partnertobe, 2);
                                windows.ErrorPopup("Updated Successfully");
                                continue windowloop;
                            }
                        }
                        //Case when Married is Chosen:
                        else if (rstatus.equals("save3")) {
                            //Checking if the user is Married or in a Committed Relationship first
                            if (current_user.isSingle() == false && current_user.isComplicated() == false) {
                                windows.ErrorPopup("Set your Status to Single before making this change");
                                continue windowloop;
                            }
                            else {
                                //Retrieve Partner Name First:
                                String partnername = windows.getDataCarrierData()[0];
                                //Retrieve the Person based on that name
                                Person partnertobe = network.getPersonByName(partnername.trim());
                                //Add them as partners:
                                network.addPartners(current_user, partnertobe, 3);
                                windows.ErrorPopup("Updated Successfully");
                                continue windowloop;
                            }
                        }
                    }
                    continue windowloop;
                    
                //View Friend Requests Page Here
                case 3:
                    
                    //Retrieve Friend Requests Of User first:
                    ArrayList<Integer> frenreqs = sqlmanagerwindows.retrieveFriendRequests(current_user);
                    //Convert into a Person Arraylist
                    ArrayList<Person> frenreqspeople = new ArrayList<>();
                    for (int i: frenreqs) {
                        frenreqspeople.add(network.getPersonByID(i));
                    }
                    //Sending it to Window Manager to be Transferred via Data Carrier
                    String requeststatus = windows.ViewFriendRequests(current_user, frenreqspeople);
                    //Handling all cases of requeststatus
                    if (requeststatus.equals("home")) {
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                        continue windowloop;
                    }
                    else if (requeststatus.equals("back")) {
                        TracebackWindows.removeLast();
                        continue windowloop;
                    }
                    else if (requeststatus.equals("close")) {
                        winreturnval = 0;
                        break windowloop;
                    }
                    else if (requeststatus.substring(0,6).equals("accept")) {
                        String acctoadd = requeststatus.replaceAll("[^0-9]", "");
                        network.AcceptFriendRequest(frenreqspeople.get(Integer.parseInt(acctoadd)).getID(), current_user.getID());
                        continue windowloop;
                    }
                    else if (requeststatus.substring(0,6).equals("reject")) {
                        String acctoreject = requeststatus.replaceAll("[^0-9]", "");
                        sqlmanagerwindows.removeFriendRequest(frenreqspeople.get(Integer.parseInt(acctoreject)), current_user);
                        continue windowloop;
                    }
                    continue windowloop;
                
                //The Admin Management window is here
                case 4:
                    
                    //Retrieving People List without Person who called it
                    List<Person> peoplewithoutme = network.returnPeopleExceptMe(current_user);
                    
                    //Starting Window to Manage Admin Panel:
                    String adminstatus = windows.OpenAdminWindow(peoplewithoutme, current_user);
                    
                    //Handling all cases of the Program:
                    if (adminstatus.equals("home")) {
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                        continue windowloop;
                    }
                    else if (adminstatus.equals("back")) {
                        TracebackWindows.removeLast();
                        continue windowloop;
                    }
                    else if (adminstatus.equals("close")) {
                        winreturnval = 0;
                        break windowloop;
                    }
                    else if (adminstatus.substring(0,6).equals("delete")) {
                        String acctodelete = adminstatus.replaceAll("[^0-9]", "");
                        Person accgettingdeleted = peoplewithoutme.get(Integer.parseInt(acctodelete));
                        network.theNeuralyzer(accgettingdeleted);
                        windows.ErrorPopup("Account Deleted Succesfully");
                        continue windowloop;
                    }
                    continue windowloop;
                    
                //View Search Results Here:
                case 5:
                    
                    //First Obtain the Topmost element which has the SearchQuery info:
                    String searchquery = TracebackWindows.getLast().getSearchQuery();
                    //Perform Fuzzy Search on the given SearchQuery
                    List<Person> searchresult = network.fuzzySearch(searchquery);
                    //Send Results to Search Results Window:
                    String searchstatus = windows.SearchResultsBox(searchresult, searchquery);
                    //Handling all cases of SearchStatus
                    if (searchstatus.equals("home")) {
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(0));
                        continue windowloop;
                    }
                    else if (searchstatus.equals("back")) {
                        TracebackWindows.removeLast();
                        continue windowloop;
                    }
                    else if (searchstatus.equals("close")) {
                        winreturnval = 0;
                        break windowloop;
                    }
                    else if (searchstatus.equals("search")) {
                        //Returning Data Value
                        String[] Datavalues = windows.getDataCarrierData();
                        String searchQuery = Datavalues[0];
                        //Sending search box query info to the Tracebackwindow manager, which will make the Results show up
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(5, searchQuery));
                        continue windowloop;
                    }
                    else if (searchstatus.substring(0,4).equals("view")) {
                        String acctoview = searchstatus.replaceAll("[^0-9]", "");
                        Person Personretrieved = searchresult.get(Integer.parseInt(acctoview));
                        TracebackWindows.add(tracebackwindowdetails.TraceBackWindowGen(1, Personretrieved));
                        continue windowloop;
                    }
                    continue windowloop;
            }   
        }
        return winreturnval;
    }
}


//Sources Used:
// https://www.youtube.com/watch?v=AHFBPxWebFQ
// https://www.javatpoint.com/example-to-connect-to-the-mysql-database
// https://www.tutorialspoint.com/jdbc/jdbc-insert-records.htm
// https://stackoverflow.com/questions/4871051/how-to-get-the-current-working-directory-in-java
// https://stackoverflow.com/questions/6444812/executing-a-command-from-java-and-waiting-for-the-command-to-finish
// https://www.pysimplegui.org/en/latest/call%20reference/
// https://www.w3schools.com/python/python_file_write.asp
// https://www.freecodecamp.org/news/how-to-check-if-a-file-exists-in-python/
// https://www.javatpoint.com/how-to-delete-a-file-in-java
// https://www.geeksforgeeks.org/difference-between-write-and-writelines-function-in-python/
// https://www.w3schools.com/java/java_files_create.asp
// https://www.edureka.co/community/101325/how-do-i-check-if-a-file-exists-in-java
// https://www.w3schools.com/python/ref_string_isnumeric.asp
// https://www.tutorialspoint.com/pysimplegui/pysimplegui_input_element.htm
// https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/String-to-long-in-Java
// https://www.javatpoint.com/java-get-current-date
// https://www.geeksforgeeks.org/factory-method-design-pattern-in-java/
// https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
// https://www.mysqltutorial.org/mysql-count/
// https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html#getString-int-
// https://learnsql.com/cookbook/how-to-combine-the-results-of-two-queries-in-sql/
// https://www.geeksforgeeks.org/python-check-for-none-values-in-given-dictionary/
// Creation of Button Art: https://www.piskelapp.com/
// https://www.geeksforgeeks.org/string-slicing-in-python/
// https://www.blog.pythonlibrary.org/2022/01/25/pysimplegui-an-intro-to-laying-out-elements/
// https://stackoverflow.com/questions/67148741/pysimplegui-change-font-font-display-ubuntu-ugly-fonts
// https://interviewsansar.com/extract-numbers-from-string-java/
// https://github.com/PySimpleGUI/PySimpleGUI/issues/3025
// https://www.javacodegeeks.com/2019/03/check-two-lists-are-equal-java.html
// https://www.geeksforgeeks.org/how-to-update-multiple-columns-in-single-update-statement-in-sql/
// https://www.w3schools.com/python/python_ref_dictionary.asp

//Created By Group 1
