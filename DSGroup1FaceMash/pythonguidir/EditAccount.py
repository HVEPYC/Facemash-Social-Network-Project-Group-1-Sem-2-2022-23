#Necessary imports
import PySimpleGUI as sg
import os

#GUI Code
sg.theme("DarkAmber")

days = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31]
months = ["January","February", "March","April","May","June","July","August","September","October","November","December"]
years = [1900,1901,1902,1903,1904,1905,1906,1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,1919,1920,1921,1922,1923,1924,1925,1926,1927,1928,1929,1930,1931,1932,1933,1934,1935,1936,1937,1938,1939,1940,1941,1942,1943,1944,1945,1946,1947,1948,1949,1950,1951,1952,1953,1954,1955,1956,1957,1958,1959,1960,1961,1962,1963,1964,1965,1966,1967,1968,1969,1970,1971,1972,1973,1974,1975,1976,1977,1978,1979,1980,1981,1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035]

#Declarations
path = ".\\pythonguidir\\MessageCarrier.txt"
datapath = ".\\pythonguidir\\DataCarrier.txt"

#Layout of Window
layout = [[sg.Text("Edit Account", justification = "center", font=("Arial",15), size=(20,3), pad = ((228,25),(30,0)))],
          [sg.Text("Address: ", pad=((30,0),(0,0))), sg.Input(justification="left", size=(73,0), key="-address-", text_color = "white")],
          [sg.Text("Enter your Hobbies (Comma Seperated): ", pad=((30,0),(30,0)))],
          [sg.Input(justification="left", size=(85,0), key="-hobby-", text_color = "white", pad=((30,0),(0,30)))],
          [sg.Text("Enter your Job Status Here: ", pad=((30,0),(0,10)))],
          [sg.Text("Jobtitle: ", pad=((30,0),(0,0))), sg.Input(justification="left", size=(20,0), key="-jobtitle-", text_color = "white", pad=((0,0),(0,0))), sg.Text("Company: ", pad=((146,0),(0,0))), sg.Input(justification="left", size=(20,0), key="-company-", text_color = "white", pad=((0,0),(0,0)))],
          [sg.Text("Start Date: ", pad=((30,0),(10,10))), sg.Combo(values=days, pad=((5,0),(10,10)), key="-sday-"), sg.Combo(values=months, pad=((5,0),(10,10)), key="-smonth-"), sg.Combo(values=years, pad=((5,0),(10,10)), key="-syear-"), sg.Text("End Date: ", pad=((80,0),(10,10))), sg.Combo(values=days, pad=((5,0),(10,10)), key="-eday-"), sg.Combo(values=months, pad=((5,0),(10,10)), key="-emonth-"), sg.Combo(values=years, pad=((5,0),(10,10)), key="-eyear-")],
          [sg.Button("Save Changes", key="-save-", size=(15,2), pad=((210,0),(25,0))), sg.Button("Cancel Changes", size=(15,2), key="-cancel-", pad=((5,0),(25,0)))]]

#Define Window
windows = sg.Window("Edit Account",layout, size=(700, 450), no_titlebar = True)

#Function to check Null Values
def checkempty(A):
    res = not all(A.values())
    return res

#GUI Forever Loop:
while True:
    event, values = windows.read()
    if event == "-cancel-":
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
        if checkempty(values) == True:
            sg.popup("Fill in all the Required Information", no_titlebar = True)
            continue
        else:
            if os.path.isfile(datapath) == True:
                #Writing Data to file
                filetogo = open(datapath,"w")
                for i in values:
                    stringtoput = str(values[i])+"\n"
                    filetogo.write(stringtoput)
                filetogo.close()
                #Writing blank Message Status File
                if os.path.isfile(path) == True:
                    filetogo = open(path,"w")
                    filetogo.write("save")
                    filetogo.close()
                    exit()
                elif os.path.isfile(path) == False:
                    filetogo = open(path,"x")
                    filetogo.write("save")
                    filetogo.close()
                    exit()
            elif os.path.isfile(datapath) == False:
                #Writing Data to file
                filetogo = open(datapath,"x")
                for i in values:
                    stringtoput = str(values[i])+"\n"
                    filetogo.write(stringtoput)
                filetogo.close()
                #Writing blank Message Status File
                if os.path.isfile(path) == True:
                    filetogo = open(path,"w")
                    filetogo.write("save")
                    filetogo.close()
                    exit()
                elif os.path.isfile(path) == False:
                    filetogo = open(path,"x")
                    filetogo.write("save")
                    filetogo.close()
                    exit()

windows.close()