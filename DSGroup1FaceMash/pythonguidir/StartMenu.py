#Necessary Imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme('DarkAmber')

layout = [[sg.Text("Welcome to FaceMash!", justification="center", pad = ((125,0),(80,0)), font=("Arial",15), size=(20,3))],
          [sg.Button('Login', pad=((125,90),(0,0)), size=(7,2), key="-Login-"), sg.Button("Create User", size=(7,2), key="-NewUser-")]]      

window = sg.Window('FaceMash', layout, size=(500, 300))      

path = ".\pythonguidir\MessageCarrier.txt"

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
    if event == "-Login-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("login")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("login")
            filetogo.close()
            exit()
    elif event == "-NewUser-":
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("newuser")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("newuser")
            filetogo.close()
            exit()

window.close()