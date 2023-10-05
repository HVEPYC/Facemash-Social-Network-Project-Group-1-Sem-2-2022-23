#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

layout = [[sg.Text("Login To Your FaceMash Account", justification="center", pad = ((125,0),(50,0)), font=("Arial",15), size=(20,3))],
          [sg.Text("E-Mail: "),sg.Input(justification="left", size=(20,0), key="-user-", text_color = "white"), sg.Text("Password: "), sg.Input(justification = "left",size = (20,0), password_char = "*", key="-pass-", text_color = "white")],
          [sg.Button(button_text = "Login!", pad=((205,0),(25,0)), size=(7,2), key="-Login-", bind_return_key=True)]]

window  = sg.Window("Login to FaceMash", layout, size = (500,300))

path = ".\pythonguidir\MessageCarrier.txt"
datapath = ".\pythonguidir\DataCarrier.txt"

#Function to check Null Values
def checkempty(A):
    res = not all(A.values())
    return res

#GUI forever loop
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
        if checkempty(values) == True:
            sg.popup("Fill in all the Required Information", no_titlebar = True)
            continue
        else:
            if os.path.isfile(datapath) == True:
                #Writing Data to file
                filetogo = open(datapath,"w")
                string1 = values["-user-"]+"\n"
                filetogo.write(string1)
                filetogo.write(values["-pass-"])
                filetogo.close()
                #Writing blank Message Status File
                filetogo = open(path,"w")
                filetogo.write("blankdata")
                filetogo.close()
                exit()
            elif os.path.isfile(datapath) == False:
                #Writing Data to file
                filetogo = open(datapath,"x")
                string1 = values["-user-"]+"\n"
                filetogo.write(string1)
                filetogo.write(values["-pass-"])
                filetogo.close()
                #Writing blank Message Status File
                if os.path.isfile(path) == True:
                    filetogo = open(path,"w")
                    filetogo.write("blankdata")
                    filetogo.close()
                    exit()
                elif os.path.isfile(path) == False:
                    filetogo = open(path,"x")
                    filetogo.write("blankdata")
                    filetogo.close()
                    exit()

window.close()