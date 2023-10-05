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

layout = [[sg.Button(key = "-back-", pad = ((5,0),(5,0)), image_filename=imgfiles+"backbutton.png"), sg.Text("Manage Users on Facemash!", font=("Arial",20), pad=((30,0),(20,0)), size=(25,2), justification="center"), sg.Button("Home",key="-gohome-", font=("Arial", 15), pad=((25,0),(0,0)))],
          [sg.Text("\nSelect a user to Delete (THIS ACTION IS IRREVERSIBLE)\n", font=("Arial",15))]]

#Make section which adds all responses here
for i in range(500):
    try:
        keytouse = "-delete"+str(i)+"-"
        layout.append([sg.Text(str(i+1)+". "+finalist[i], font = ("Arial", 15), size=43, pad = ((5,0),(0,0))), sg.Button("DELETE", key = keytouse, font = ("Arial", 15), button_color="red")])
    except IndexError:
        break

finallayout = [[sg.Column(layout, scrollable=True, expand_y=True, vertical_scroll_only=True)]]

#Initialize Window
windows = sg.Window("Admin Management Window",finallayout,size=(635,500))

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
    elif event[:-2] == "-delete":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("delete"+event[-2])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("delete"+event[-2])
            filetogo.close()
            exit()
    elif event[:-3] == "-delete":
        #Writing Button Press Event
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("delete"+event[-3:-1])
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("delete"+event[-3:-1])
            filetogo.close()
            exit()

window.close()