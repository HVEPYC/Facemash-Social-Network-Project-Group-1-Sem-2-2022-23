#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
path = ".\pythonguidir\MessageCarrier.txt"
datapath = ".\\pythonguidir\\DataCarrier.txt"
imgfiles = ".\\pythonguidir\\assets\\"

#Python File Loads Data Here:
datafile = open(datapath, "r")
name = datafile.readline() #Loading the First Name
finalist = datafile.readlines() #List with all Names
for i in range(len(finalist)-1):
    finalist[i] = finalist[i][:-1] #Removes the \n newline thing
datafile.close()
#Removes datacarrier.txt here
os.remove(datapath)

#Left Column Layout
WindowManageButtons = [[sg.Button(key = "-back-", pad = ((5,0),(5,0)), image_filename=imgfiles+"backbutton.png")],
                       [sg.Button("Search Users", key = "-search-",size = (18,2) , pad = ((5,0),(30,0)), font = ("Arial",15))],
                       [sg.Button("Manage my Profile", key = "-manage-",size = (18,2) , pad = ((5,0),(10,0)), font = ("Arial",15))],
                       [sg.Button("View Friend Requests", key = "-frenreqs-",size = (18,2) , pad = ((5,0),(10,0)), font = ("Arial",15))],
                       [sg.Button("Admin Tools", key = "-admin-",size = (18,2) , pad = ((5,0),(10,0)), font = ("Arial",15))],
                       [sg.Text("Currently Logged In:", pad = ((5,0),(30,0)), font = ("Arial",15))],
                       [sg.Text(name, pad = ((5,0),(10,0)), font = ("Arial",15), size =(15,2))], #Insert Name Here
                       [sg.Button("Log Out", key = "-logout-",size = (18,2) , pad = ((5,0),(10,10)), font = ("Arial",15))]]

#Right Column Layout
RightColumnLayout = [[sg.Text("Welcome to Facemash!", font = ("Arial", 30), pad = ((10,0),(0,45)), size = (50,1))],
                     [sg.Text("Here's a list of People we think you would like to Follow:", font=("Arial", 15), pad = ((10,0),(0,30)))]]

#Adding elements from the Recommendations:
for i in range(15):
    try:
        keytouse = "-buttonview"+str(i)+"-"
        RightColumnLayout.append([sg.Text(str(i+1)+". "+finalist[i], font = ("Arial", 15), size=33, pad = ((10,0),(0,0))), sg.Button("View Profile", key = keytouse, font = ("Arial", 15))])
    except IndexError:
        break


#Main Window Layout
layout = [[sg.Column(WindowManageButtons, pad=((0,10),(0,0))), sg.VSeparator(),sg.Column(RightColumnLayout, vertical_alignment="top", scrollable=True, vertical_scroll_only=True, expand_y = True)]]

#Final Layout
windows = sg.Window("Facemash Home",layout,size=(830,600))

#GUI Forever Loop:
while True:
    event, values = windows.read()
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
    elif event == "-search-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("search")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("search")
            filetogo.close()
            exit()
    elif event == "-manage-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("manage")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("manage")
            filetogo.close()
            exit()
    elif event == "-frenreqs-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("frenreqs")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("frenreqs")
            filetogo.close()
            exit()
    elif event == "-admin-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("admin")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("admin")
            filetogo.close()
            exit()
    elif event == "-logout-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("logout")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("logout")
            filetogo.close()
            exit()
    elif event[:-2] == "-buttonview":
        print(event[-2])
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("view"+event[-2])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("view"+event[-2])
            filetogo.close()
            exit()
    elif event[:-3] == "-buttonview":
        print(event[-3:-1])
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("view"+event[-3:-1])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("view"+event[-3:-1])
            filetogo.close()
            exit()

windows.close()