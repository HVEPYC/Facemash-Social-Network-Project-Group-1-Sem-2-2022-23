#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

#Declarations
path = ".\\pythonguidir\\MessageCarrier.txt"
datapath = ".\\pythonguidir\\DataCarrier.txt"

#Python File Loads Data Here:
datafile = open(datapath, "r")
searchquery = datafile.readline() #Loading the First Line with the Search Query
finalist = datafile.readlines() #List with all Search Results
for i in range(len(finalist)-1):
    finalist[i] = finalist[i][:-1] #Removes the \n newline thing
datafile.close()
#Removes datacarrier.txt here
os.remove(datapath)

#Defining Lists for Values
statusvalues = {"Single":1, "Committed Relationship":2, "Married":3, "It's Complicated":4}
statusvalue2 = list(statusvalues.keys())

#GUI Layout
layout = [[sg.Text("Edit Relationship Status", font=("Arial",25), justification="center", size = (34), pad=((10,0),(20,0)))],
          [sg.Text("\nNote: To add a Person: ", font=("Arial",15))],
          [sg.Text("- The Person Needs to be in your Friends List", font=("Arial",15))],
          [sg.Text("- The Person also has to be Single as per their Profile Setup", font=("Arial",15))],
          [sg.Text("\nChoose From the Options Below: ", font=("Arial",15))],
          [sg.Text("Relationship Status: ", size=40, font=("Arial",15)),sg.Combo(values = statusvalue2, key = "-rstatus-", font=("Arial",15))],
          [sg.Text("Choose Person you want to be Related To: ", size=40, font=("Arial",15)), sg.Combo(values = finalist, key = "-person-", font=("Arial",15))],
          [sg.Button("Save Changes", key = "-save-", size=(20,2), font=("Arial",15), pad=((105,0),(35,0))), sg.Button("Cancel", key = "-cancel-", size=(20,2), font=("Arial",15), pad=((5,0),(35,0)))]]

#Define Window
windows = sg.Window("Edit Relationship Status",layout, size=(700, 425), no_titlebar = True)

#Function to check Null Values
def checkempty(A):
    res = not all(A.values())
    return res

#GUI Forever Loop:
while True:
    event, values = windows.read()
    if event == "-cancel-":
        #Writing to MessageCarrier
        if os.path.isfile(path) == True:
            filetogo = open(path,"w")
            filetogo.write("cancel")
            filetogo.close()
            exit()
        elif os.path.isfile(path) == False:
            filetogo = open(path,"x")
            filetogo.write("cancel")
            filetogo.close()
            exit()
    elif event == "-save-":
        if values["-rstatus-"] == "":
            sg.Popup("Select a Relationship Status")
            continue
        else:
            if statusvalues[values["-rstatus-"]] == 1 or statusvalues[values["-rstatus-"]] == 4:
                #Writing to MessageCarrier
                result = statusvalues[values["-rstatus-"]]
                print("save"+str(result))
                if os.path.isfile(path) == True:
                    filetogo = open(path,"w")
                    filetogo.write("save"+str(result))
                    filetogo.close()
                    exit()
                elif os.path.isfile(path) == False:
                    filetogo = open(path,"x")
                    filetogo.write("save"+str(result))
                    filetogo.close()
                    exit()
            elif statusvalues[values["-rstatus-"]] == 2 or statusvalues[values["-rstatus-"]] == 3:
                if checkempty(values) == True:
                    sg.Popup("Select a Person")
                    continue
                else:
                    result = statusvalues[values["-rstatus-"]]
                    print("save"+str(result))
                    print(values["-person-"])
                    #Writing to DataCarrier
                    if os.path.isfile(datapath) == True:
                        #Writing Data to file
                        filetogo = open(datapath,"w")
                        filetogo.write(values["-person-"])
                        filetogo.close()
                    elif os.path.isfile(datapath) == False:
                        #Writing Data to file
                        filetogo = open(datapath,"x")
                        filetogo.write(values["-person-"])
                        filetogo.close()
                    #Writing to MessageCarrier
                    if os.path.isfile(path) == True:
                        filetogo = open(path,"w")
                        filetogo.write("save"+str(result))
                        filetogo.close()
                        exit()
                    elif os.path.isfile(path) == False:
                        filetogo = open(path,"x")
                        filetogo.write("save"+str(result))
                        filetogo.close()
                        exit()

windows.close()
