#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
path = ".\pythonguidir\MessageCarrier.txt"
datapath = ".\\pythonguidir\\DataCarrier.txt" #change later
frienddatapath = ".\\pythonguidir\\frienddata.txt" #Change later
imgfiles = ".\\pythonguidir\\assets\\" #Change later

#Load Data Files Here:

#Loading User Info
datafile = open(datapath, "r")
name = datafile.readline()
datalist = datafile.readlines()
for i in range(len(datalist)-1):
    datalist[i] = datalist[i][:-1] #removes the \n
datafile.close()

dateofbirth = datalist[0] #done
address = datalist[1] #done
gender = datalist[2] #done
username = datalist[3] #done
phonenumber = datalist[4] #done
email = datalist[5] #done
acccreationdate = datalist[6] #done
hobbies = datalist[7] #done
jobtitle = datalist[8] #done
company = datalist[9] #done
startdate = datalist[10] #done
enddate = datalist[11] #done
partner = datalist[12] #done
relationstatus = datalist[13] #done

relationtext = ""
relationstatus = relationstatus[:-1]
if relationstatus == "1":
    relationtext = "Currently Single"
elif relationstatus == "2":
    relationtext = "In a Relationship with "+partner
elif relationstatus == "3":
    relationtext = "Married to "+partner
elif relationstatus == "4":
    relationtext = "It's Complicated"

os.remove(datapath)

#Loading Friend Data info
friendfile = open(frienddatapath, "r")
name = friendfile.readline()
friendlist = friendfile.readlines()
for i in range(len(friendlist)-1):
    friendlist[i] = friendlist[i][:-1] #removes the \n
friendfile.close()

os.remove(frienddatapath)

name = name[:-1]

#Main User Info Layout
userinfolayout = [[sg.Button(key = "-back-", pad = ((5,0),(5,0)), image_filename=imgfiles+"backbutton.png"), sg.Button("Home",key="-gohome-", font=("Arial", 15), pad=((500,0),(0,0)))],
                  [sg.Text(name, font=("Arial", 40), size=(18)), sg.Button("Edit Account",font = ("Arial",15), key="-edit-", pad = ((0,0),(0,0)))],
                  [sg.Text("Phone Number: "+phonenumber, font=("Arial",15), pad = ((5,0),(20,0)))],
                  [sg.Text("Username: "+username, font=("Arial",15)), sg.Text("Gender: "+gender, font=("Arial",15), pad = ((200,0),(0,0)))],
                  [sg.Text("Date of Birth: "+dateofbirth, font=("Arial",15))],
                  [sg.Text("Email Address: "+email, font=("Arial",15))],
                  [sg.Text("Address: "+address, font=("Arial",15))],
                  [sg.Text("Currently Working at: "+company+" as "+jobtitle, font=("Arial",15))],
                  [sg.Text("Working there from "+startdate+" to "+enddate, font=("Arial",15))],
                  [sg.Text(text = "Relationship Status: "+relationtext, font=("Arial",15))],
                  [sg.Text("My Hobbies are "+hobbies, font=("Arial",15))],
                  [sg.Text("Account Created On: "+acccreationdate, font=("Arial",15))]]

#Friends of Person being Viewed Here:
friendsofuserlayout = [[sg.Text("Friends:",size=(20), font=("Arial", 15))]]

#Adding Friends From List
for i in range(35):
    try:
        keytouse = "-friendview"+str(i)+"-"
        friendsofuserlayout.append([sg.Text(str(i+1)+". "+friendlist[i], font = ("Arial", 15), size=23, pad = ((10,0),(0,0))), sg.Button("View Profile", key = keytouse, font = ("Arial", 15))])
    except IndexError:
        break

finallayout = [[sg.Column(userinfolayout),sg.VerticalSeparator(), sg.Column(friendsofuserlayout, vertical_alignment="top", scrollable=True, vertical_scroll_only=True, expand_y = True)]]

window = sg.Window("View Your Profile: "+name, finallayout, size = (1150,500))

#GUI Forever Loop
while True:
    event, values = window.read()
    if event == sg.WINDOW_CLOSED:
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("close")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("close")
            filetogo.close()
            exit()
    elif event == "-back-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("back")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("back")
            filetogo.close()
            exit()
    elif event == "-gohome-":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("home")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("home")
            filetogo.close()
            exit()
    elif event == "-edit-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("edit")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("edit")
            filetogo.close()
            exit()
    elif event[:-2] == "-friendview":
        print(event[-2])
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("friend"+event[-2])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("friend"+event[-2])
            filetogo.close()
            exit()
    elif event[:-3] == "-friendview":
        print(event[-3:-1])
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("friend"+event[-3:-1])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("friend"+event[-3:-1])
            filetogo.close()
            exit()

windows.close()