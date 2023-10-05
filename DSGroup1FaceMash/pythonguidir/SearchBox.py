#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
datapath = ".\\pythonguidir\\DataCarrier.txt"

#Layout
layout = [[sg.Text("Search Users on Facemash!", font=("Arial",15), pad=((115,0),(20,0)), size=(30,2))],
          [sg.Text("Enter Person's Name:")],
          [sg.Input(key="-searchterm-", font=("Arial",15), text_color="white")],
          [sg.Button("Search",key="-search-", font=("Arial",15), pad=((200,0),(15,0)), bind_return_key = True)]]

#Initialize Window
windows = sg.Window("Facemash Search",layout,size=(500,200), no_titlebar = True)

#Function to check Null Values
def checkempty(A):
    res = not all(A.values())
    return res


#GUI Forever Loop:
while True:
    event, values = windows.read()
    if event == "-search-":
        if checkempty(values) == True:
            sg.Popup("Do not leave Search Box Blank")
        else:
            print(values["-searchterm-"])
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

windows.close()