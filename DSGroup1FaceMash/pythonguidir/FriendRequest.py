#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
path = ".\pythonguidir\MessageCarrier.txt"
datapath = ".\\pythonguidir\\DataCarrier.txt" 
imgfiles = ".\\pythonguidir\\assets\\" 

#Loading Data File Friend Request Info:
datafile = open(datapath, "r")
name = datafile.readline()
datalist = datafile.readlines()
for i in range(len(datalist)-1):
    datalist[i] = datalist[i][:-1] #removes the \n
datafile.close()

#Removes datacarrier.txt here
os.remove(datapath)

#Layout
layout = [[sg.Button(key = "-back-", pad = ((5,0),(5,0)), image_filename=imgfiles+"backbutton.png"), sg.Text("View Pending Friend Requests", font=("Arial",20), pad=((70,0),(20,0)), size=(25,2)), sg.Button("Home",key="-gohome-", font=("Arial", 15), pad=((30,0),(0,0)))],
          [sg.Text()]]

#Adding values from File:
for i in range(15):
    if len(datalist) == 0:
        layout.append([sg.Text("No Pending Friend Requests", font = ("Arial", 15), size=31, pad = ((5,0),(0,0)))])
        break
    else:
        try:
            keytouse1 = "-accept"+str(i)+"-"
            keytouse2 = "-reject"+str(i)+"-"
            layout.append([sg.Text(str(i+1)+". "+datalist[i], font = ("Arial", 15), size=27, pad = ((5,0),(0,0))), sg.Button("Accept Request", key = keytouse1, font = ("Arial", 15)), sg.Button("Remove Request", key = keytouse2, font = ("Arial", 15))])
        except IndexError:
            break

#Defining Final Layout
finallayout = [[sg.Column(layout, scrollable=True, expand_y=True, vertical_scroll_only=True)]]

#Initialize Window
windows = sg.Window("View Friend Requests",finallayout,size=(690,500))

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
    elif event[:-2] == "-accept":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("accept"+event[-2])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("accept"+event[-2])
            filetogo.close()
            exit()
    elif event[:-3] == "-accept":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("accept"+event[-3:-1])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("accept"+event[-3:-1])
            filetogo.close()
            exit()
    elif event[:-2] == "-reject":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("reject"+event[-2])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("reject"+event[-2])
            filetogo.close()
            exit()
    elif event[:-3] == "-reject":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("reject"+event[-3:-1])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("reject"+event[-3:-1])
            filetogo.close()
            exit()

window.close()