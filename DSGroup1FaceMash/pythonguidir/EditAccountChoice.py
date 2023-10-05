#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
path = ".\\pythonguidir\\MessageCarrier.txt"
imgfiles = ".\\pythonguidir\\assets\\"

#Layout of Window:

layout = [[sg.Button(key = "-back-", pad = ((5,0),(5,0)), image_filename=imgfiles+"backbutton.png"), sg.Text("Edit Account Options", font=("Arial",15), pad=((50,0),(20,0)), size=(21,2)), sg.Button("Home",key="-gohome-", font=("Arial", 15), pad=((0,0),(0,0)))],
          [sg.Button("Edit Account Details", key = "-accdetails-", size=(20,2), font=("Arial", 15), pad = ((85,0),(25,0)))],
          [sg.Button("Edit Relationship Status", key = "-rstatus-", size=(20,2), font=("Arial", 15), pad = ((85,0),(5,0)))]]

#Initialize Window
windows = sg.Window("Edit Account",layout,size=(420,300))

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
    elif event == "-accdetails-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("accdetails")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("accdetails")
            filetogo.close()
            exit()
    elif event == "-rstatus-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("rstatus")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("rstatus")
            filetogo.close()
            exit()

windows.close()