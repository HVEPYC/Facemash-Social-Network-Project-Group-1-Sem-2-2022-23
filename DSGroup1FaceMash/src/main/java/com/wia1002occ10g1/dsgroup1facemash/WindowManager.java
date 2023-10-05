package com.wia1002occ10g1.dsgroup1facemash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Harishankar Vinod
 */
public class WindowManager {
    
    // This Manages all Window Processes which could take place in this App
    
    private String cwd;
    private String PythonGUIDir;
    
    public WindowManager() {
        this.cwd = System.getProperty("user.dir");
        this.PythonGUIDir = this.cwd+"\\pythonguidir";
    }
    
    //Window to show the initial Start Menu
    public String StartMenu() throws Exception {
        WindowCommandLauncher("StartMenu.py");
        return this.getMessageCarrierData();
    }
    
    //Window to Log into an account
    public String LoginMenu() throws Exception {
        WindowCommandLauncher("LoginMenu.py");
        return this.getMessageCarrierData();
    }
    
    //Window to Sign Up a new user
    public String SignUpMenu() throws Exception {
        WindowCommandLauncher("SignUpMenu.py");
        return this.getMessageCarrierData();
    }
    
    //Window to add extra Details
    public void ExtraDetailsMenu() throws Exception {
        WindowCommandLauncher("MoreDetails.py");
    }
    
    //Window definition to Open Homepage
    public String HomePage(List<Person> Recommendations, Person currentUser) throws Exception {
        //Sending Recommendations list to Python File
        this.WritePersonListDataToDataCarrier(Recommendations, currentUser);
        //Launching the Python File to read all the Values
        WindowCommandLauncher("HomePage.py");
        //Returning Window Status
        return this.getMessageCarrierData();
    }
    
    //Window definition to Open Search Box
    public String SearchBox() throws Exception {
        WindowCommandLauncher("SearchBox.py");
        return this.getDataCarrierData()[0];
    }
    
    public String SearchResultsBox(List<Person> recommendations, String query) throws Exception {
        //Sending Search Results to Python File first:
        this.WritePersonListDataToDataCarrier(recommendations, query);
        //Launching the python file which will read all values
        WindowCommandLauncher("SearchResults.py");
        //Returning Window Status
        return this.getMessageCarrierData();   
    }
    
    //Window definition to view Account
    public String ViewAccount(Person User, List<Person> Friends, String[] accdetails) throws Exception {
        //Sending Person data to Datacarrier
        this.WriteToDataCarrier(accdetails);
        //Sending Friend Data to FriendDataCarrier
        this.WriteToFriendData(Friends, User);
        //Invoking Window to Open
        this.WindowCommandLauncher("ViewAccount.py");
        return this.getMessageCarrierData();
    }
    
    //Window definition to view Account of other Users
    public String ViewAccount(Person User, List<Person> Friends, List<Person> mutuals, String[] accdetails, int degreeOfConnection, boolean FriendStatus) throws Exception {
        //Replacing Phone Number with the Degree of Connection Amount (because we do not wish to display others phone numbers when viewing acc)
        accdetails[5] = Integer.toString(degreeOfConnection);
        //Sending Person Data to DataCarrier
        this.WriteToDataCarrier(accdetails);
        //Sending Friend Data to FriendDataCarrier
        this.WriteToFriendData(Friends, User);
        //Sending Mutual Friend Data to MutualDataCarrier
        this.WriteToMutualData(mutuals, User);
        //Sending isFriend Status Data
        this.WriteToisFriendCarrier(FriendStatus);
        //Invoking Window to open
        this.WindowCommandLauncher("ViewAccountOthers.py");
        return this.getMessageCarrierData();
    }
    
    //Window definition to Open Friend Requests
    public String ViewFriendRequests(Person User, ArrayList<Person> FriendRequests) throws Exception {
        //Writing Info to Data Carrier First
        this.WritePersonListDataToDataCarrier(FriendRequests, User);
        //Starting Window
        this.WindowCommandLauncher("FriendRequest.py");
        return this.getMessageCarrierData();
    }
    
    //Window Definition to Open Edit Account Initial Choice:
    public String EditAccountChoice() throws Exception {
        //Starting Window
        this.WindowCommandLauncher("EditAccountChoice.py");
        return this.getMessageCarrierData();
    }
    
    //Window definition to Open Account Edit Menu:
    public String EditAccount() throws Exception {
        //Starting Window
        this.WindowCommandLauncher("EditAccount.py");
        return this.getMessageCarrierData();
    }
    
    //Window definition to Open Relationship Status Account Edit Menu:
    public String EditRelationshipStatus(List<Person> isSingleAndFriend, Person user) throws Exception {
        //Sending the isSingleAndFriend info to the DataCarrier
        this.WritePersonListDataToDataCarrier(isSingleAndFriend, user);
        //Starting Window
        this.WindowCommandLauncher("EditRStatus.py");
        return this.getMessageCarrierData();
    }
    
    //Window definition to open Admin Window (Which Deletes Users)
    public String OpenAdminWindow(List<Person> people, Person user) throws Exception {
        //Sending the List of all people in the network to the Window
        this.WritePersonListDataToDataCarrier(people, user);
        //Opening the New Window:
        this.WindowCommandLauncher("AdminWindow.py");
        return this.getMessageCarrierData();
    }
    
    //Window which shows various Error Popups
    public void ErrorPopup(String textinput) throws Exception {
        File ErrorMessageToShow = new File(this.PythonGUIDir+"\\ErrorMessage.txt");
        if (ErrorMessageToShow.exists()) {
            PrintWriter file = new PrintWriter(new FileOutputStream(this.PythonGUIDir+"\\ErrorMessage.txt"));
            file.println(textinput);
            file.close();
        }
        else if (ErrorMessageToShow.exists() == false) {
            ErrorMessageToShow.createNewFile();
            PrintWriter file = new PrintWriter(new FileOutputStream(this.PythonGUIDir+"\\ErrorMessage.txt"));
            file.println(textinput);
            file.close();
        }
        WindowCommandLauncher("PopUp.py");
    } 
    
    //Main Window Command Launcher which launches windows.
    public void WindowCommandLauncher(String Window) throws Exception{
        String command  = "cmd /C \"python \""+PythonGUIDir+"\\"+Window+"\"\"";
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
    }
    
    //This method obtains the data from the MessageCarrier file generated by Python, and deletes it as well.
    public String getMessageCarrierData() throws Exception {
        Scanner input = new Scanner(new FileInputStream(this.PythonGUIDir+"\\MessageCarrier.txt"));
        String output = input.nextLine().trim();
        input.close();
        File toDelete = new File(this.PythonGUIDir+"\\MessageCarrier.txt");
        toDelete.delete();
        return output;
    }
    
    //This method obtains multiline data from the DataCarrier file generated by Python, and deletes it immediately.
    public String[] getDataCarrierData() throws Exception {
        Scanner inputtemp = new Scanner(new FileInputStream(this.PythonGUIDir+"\\DataCarrier.txt"));
        int filelength = 0;
        while (inputtemp.hasNextLine()) {
            inputtemp.nextLine();
            filelength++;
        }
        inputtemp.close();
        Scanner input = new Scanner(new FileInputStream(this.PythonGUIDir+"\\DataCarrier.txt"));
        String[] datacarriertext = new String[filelength];
        int i = 0;
        while (input.hasNextLine()) {
            datacarriertext[i] = input.nextLine();
            i++;
        }
        input.close();
        File toDelete = new File(this.PythonGUIDir+"\\DataCarrier.txt");
        toDelete.delete();
        return datacarriertext;
    }

    //To be used to transfer whether account being viewed is a friend or not
    private void WriteToisFriendCarrier(boolean isFriendStatus) throws Exception{
        String datacarrierpath = this.PythonGUIDir+"\\isFriend.txt";
        File DataCarrier = new File(datacarrierpath);
        if (DataCarrier.exists()) {
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            if (isFriendStatus == true) {
                File.println("true");
            }
            else {
                File.println("false");
            }
            File.close();
        }
        else if (DataCarrier.exists() == false) {
            DataCarrier.createNewFile();
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            if (isFriendStatus == true) {
                File.println("true");
            }
            else {
                File.println("false");
            }
            File.close();
        }
    }
    
    //To be used for the View Account Feature to transfer account info  
    private void WriteToDataCarrier(String[] accdetails) throws Exception{
        String datacarrierpath = this.PythonGUIDir+"\\DataCarrier.txt";
        File DataCarrier = new File(datacarrierpath);
        if (DataCarrier.exists()) {
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            for (String i: accdetails) {
                File.println(i);
            }
            File.close();
        }
        else if (DataCarrier.exists() == false) {
            DataCarrier.createNewFile();
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            for (String i: accdetails) {
                File.println(i);
            }
            File.close();
        }
    }
    
    //To be used for the View Account Feature to transfer friend info
    private void WriteToFriendData(List<Person> friends, Person currentuser) throws Exception{
        String datacarrierpath = this.PythonGUIDir+"\\frienddata.txt";
        File DataCarrier = new File(datacarrierpath);
        if (DataCarrier.exists()) {
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(currentuser.getName());
            for (Person i: friends) {
                File.println(i.getName());
            }
            File.close();
        }
        else if (DataCarrier.exists() == false) {
            DataCarrier.createNewFile();
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(currentuser.getName());
            for (Person i: friends) {
                File.println(i.getName());
            }
            File.close();
        }
    }
    
    //To be used for the View Account Feature to transfer mutual friend info
    private void WriteToMutualData(List<Person> mutuals, Person currentuser) throws Exception{
        String datacarrierpath = this.PythonGUIDir+"\\mutualdata.txt";
        File DataCarrier = new File(datacarrierpath);
        if (DataCarrier.exists()) {
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(currentuser.getName());
            for (Person i: mutuals) {
                File.println(i.getName());
            }
            File.close();
        }
        else if (DataCarrier.exists() == false) {
            DataCarrier.createNewFile();
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(currentuser.getName());
            for (Person i: mutuals) {
                File.println(i.getName());
            }
            File.close();
        }
    }
    
    //This method creates a file, sends the People names and also sends the name of the Person who is current User in the first line
    private void WritePersonListDataToDataCarrier(List<Person> items, Person currentUser) throws Exception{
        String datacarrierpath = this.PythonGUIDir+"\\DataCarrier.txt";
        File DataCarrier = new File(datacarrierpath);
        if (DataCarrier.exists()) {
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(currentUser.getName());
            for (Person i: items) {
                File.println(i.getName());
            }
            File.close();
        }
        else if (DataCarrier.exists() == false) {
            DataCarrier.createNewFile();
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(currentUser.getName());
            for (Person i: items) {
                File.println(i.getName());
            }
            File.close();
        }
    }
    
    //Same as above, except it sends data for Search Query Results
    private void WritePersonListDataToDataCarrier(List<Person> items, String searchquery) throws Exception{
        String datacarrierpath = this.PythonGUIDir+"\\DataCarrier.txt";
        File DataCarrier = new File(datacarrierpath);
        if (DataCarrier.exists()) {
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(searchquery);
            for (Person i: items) {
                File.println(i.getName());
            }
            File.close();
        }
        else if (DataCarrier.exists() == false) {
            DataCarrier.createNewFile();
            PrintWriter File = new PrintWriter(new FileOutputStream(datacarrierpath));
            //First Line being Person Name
            File.println(searchquery);
            for (Person i: items) {
                File.println(i.getName());
            }
            File.close();
        }
    }
}
