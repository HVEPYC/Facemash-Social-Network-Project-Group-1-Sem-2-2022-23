#Necessary Imports:
import PySimpleGUI as sg
import os

#GUI Code:
sg.theme("DarkAmber")

pathwithmsg = ".\pythonguidir\ErrorMessage.txt"

messagefile = open(pathwithmsg, "r")
message = messagefile.readline()
messagefile.close()
os.remove(pathwithmsg)

layout = [[sg.Text(message, justification = "center", font=("Arial",15), size=(20,3), pad = ((25,0),(10,0)))],
          [sg.Button(button_text = "OK", size=(7,2), key="-popupack-", pad = ((105,0),(0,0)))]]

window = sg.Window("Error", layout, size = (300,150), no_titlebar = True)

#GUI Forever Loop:
while True:
    event, values = window.read()
    if event == "-popupack-":
        exit()

window.close()