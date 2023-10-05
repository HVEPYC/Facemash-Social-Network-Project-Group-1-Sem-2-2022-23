#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
path = ".\\pythonguidir\\MessageCarrier.txt"
datapath = ".\\pythonguidir\\DataCarrier.txt"
imgfiles = ".\\pythonguidir\\assets\\"

#Python File Loads Data Here:
datafile = open(datapath, "r")
searchquery = datafile.readline() #Loading the First Line with the Search Query
finalist = datafile.readlines() #List with all Search Results
for i in range(len(finalist)-1):
    finalist[i] = finalist[i][:-1] #Removes the \n newline thing
datafile.close()
#Removes datacarrier.txt here
os.remove(datapath)

#Layout
layout = [[sg.Button(key = "-back-", pad = ((5,0),(5,0)), image_filename=imgfiles+"backbutton.png"), sg.Text("Search Users on Facemash!", font=("Arial",15), pad=((50,0),(20,0)), size=(25,2)), sg.Button("Home",key="-gohome-", font=("Arial", 15), pad=((25,0),(0,0)))],
          [sg.Text("Enter Person's Name:")],
          [sg.Input(key="-searchterm-", font=("Arial",15), size=(42), text_color="white")],
          [sg.Button("Search Again",key="-search-", font=("Arial",15), pad=((170,0),(15,20)))],
          [sg.HorizontalSeparator()],
          [sg.Text("Search Results for: "+searchquery, font=("Arial",15))]]

#Make section which adds all responses here
for i in range(15):
    if len(finalist) == 0:
        layout.append([sg.Text("No Results Found", font = ("Arial", 15), size=31, pad = ((5,0),(0,0)))])
        break
    else:
        try:
            keytouse = "-buttonview"+str(i)+"-"
            layout.append([sg.Text(str(i+1)+". "+finalist[i], font = ("Arial", 15), size=31, pad = ((5,0),(0,0))), sg.Button("View Profile", key = keytouse, font = ("Arial", 15))])
        except IndexError:
            break

finallayout = [[sg.Column(layout, scrollable=True, expand_y=True, vertical_scroll_only=True)]]

#Initialize Window
windows = sg.Window("Facemash Search",finallayout,size=(530,500))

#Function to check Null Values
def checkempty(A):
    res = not all(A.values())
    return res

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
        if checkempty(values) == True:
            sg.Popup("Do not leave Search Box Blank")
        else:
            print(values["-searchterm-"])
            #Writing search for Accepting Another search term
            if os.path.isfile(path) == True:
                filetogo = open(path,"w")
                filetogo.write("search")
                filetogo.close()
            elif os.path.isfile(path) == False:
                filetogo = open(path,"x")
                filetogo.write("search")
                filetogo.close()
            if os.path.isfile(datapath) == True:
                #Writing Data to file
                filetogo = open(datapath,"w")
                filetogo.write(values["-searchterm-"])
                filetogo.close()
                exit()
            elif os.path.isfile(datapath) == False:
                #Writing Data to file
                filetogo = open(datapath,"x")
                filetogo.write(values["-searchterm-"])
                filetogo.close()
                exit()
    elif event[:-2] == "-buttonview":
        #Writing Button Press Event
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
        #Writing Button Press Event
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

windows.close()