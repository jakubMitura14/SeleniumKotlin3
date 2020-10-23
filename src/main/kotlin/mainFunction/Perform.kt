package mainFunction

import org.openqa.selenium.WebDriver


/** decribes what actions should be performed in a step */
sealed class Perform {

    /** click the node */
    class click(val timeToWait : Long) :Perform()
    /**send some keys*/
    class sendText(val textToSend : String) :Perform()
    /** opens given webpage*/
    class openWebpage (val webpageUrl : String) :Perform()
    /**send some keys but without earlier choosing nodes*/
    class sendTextToDummy (val textToSend : String, val numberOfTAbs: Int =0) :Perform()
    /**send some keys but without earlier choosing nodes*/
    class sendTextToRobot (val listOfKeysToPress : List<Int>, val numberOfTAbs: Int =0) :Perform()
    /**send some keys but without earlier choosing nodes*/
    class sendTextToRobotFormKeyBoard (val listOfListsKeysToPress : List<List<Int>>, val numberOfTAbs: Int =0) :Perform()
    /**in case we need to invoke some specialized function to perform the task*/
    class specialFunct (val specializedFunct : (dr : WebDriver) ->Unit) :Perform()
    /** refreshing website */
    class refreshWebsite () :Perform()
    /** navigating to previous site */
    class navigateBack () :Perform()
    /**calculating number of tabs and than performing o the basis of it */
    class findTextAndPressCalculatedTabs(val textToSeek: String, val formulaToCalculateTabs: (i: Int) -> Int) :Perform()
}