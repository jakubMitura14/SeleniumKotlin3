package functionalUnits

import mainFunction.*


/**
 * storing actions needed to login
 * */
class LoginSection  : Section({
    listOf<SubSection>(SubSection(
         listOfSteps =    listOf(
                    Step.sendKeys(selectingString = "#UserName",inputText = "e-jmitura"),
                    Step.sendKeys(selectingString = "#Password",inputText = "JJJOjjjo1*"),
                    Step.click(selectingString = "#BtnLogowanie",checking = Checking.dummy()),
                            Step.specialFunct({ driver -> Thread.sleep(300) }),

                 ),
    repair= Repair.refreshSiteAndStartOver()))


}.invoke())

