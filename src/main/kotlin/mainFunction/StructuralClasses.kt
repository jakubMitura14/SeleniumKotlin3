package mainFunction

import org.openqa.selenium.WebDriver

/**
 * data in order to complete the step - it will be generally related to single elementon the side
 * selectMet will select desired node
 * perform - will choose the action to perform
 * check - will choose way to check
 * */
sealed class Step( val selectMet : SelectorMethods , val perform : Perform, val checking: Checking,val timeToWait : Long = 0){

/** will click given node */
class click (selectingString: String,  timeToWait : Long = 0,
             selectMet : SelectorMethods = SelectorMethods.bySelector(selectingString),
             perform : Perform = Perform.click(timeToWait),  checking: Checking)  : Step(selectMet,perform,checking,timeToWait )
/**
 * sending keys
 * */
class sendKeys (selectingString: String, inputText : String,
                selectMet : SelectorMethods = SelectorMethods.bySelector(selectingString), timeToWait : Long = 0,
                perform : Perform = Perform.sendText(inputText),  checking: Checking = Checking.checkNodeText(inputText))  : Step(selectMet,perform,checking ,timeToWait)
/**
 * just opening given webpage
 * */
class openWebpage (val webPageUrl : String,timeToWait: Long=0)  :Step(
        selectMet = SelectorMethods.dummy(),
        perform= Perform.openWebpage(webPageUrl),
        checking= Checking.checkisCorrWebsite(webPageUrl),
        timeToWait =timeToWait)


/**
 * sending keys after some delay without specifing the node generally it will send given amount of tabs and later enter
 * */
class sendKeysToDummy (val  inputText : String,
                       val numberOfTabs : Int= 0,  timeToWait : Long =0 ,
                       selectMet : SelectorMethods = SelectorMethods.dummy(),
                       perform : Perform = Perform.sendTextToDummy(inputText,numberOfTabs),
                       checking: Checking = Checking.dummy())
                                : Step(selectMet,perform,checking )
    /**
     * sending keys after some delay without specifing the node generally it will send given amount of tabs and later enter
     * */
    class sendKeysToRobot (val  inputKeys : List<Int>,
                           val numberOfTabs : Int= 0,  timeToWait : Long =0 ,
                           selectMet : SelectorMethods = SelectorMethods.dummy(),
                           perform : Perform = Perform.sendTextToRobot(inputKeys,numberOfTabs),
                           checking: Checking = Checking.dummy())
        : Step(selectMet,perform,checking )
    /**
     * sending keys after some delay without specifing the node generally it will send given amount of tabs and later enter
     * */
    class sendKeysToRobotWithKeyboardUtil (val  inputKeys : List<List<Int>>,
                           val numberOfTabs : Int= 0,  timeToWait : Long =0 ,
                           selectMet : SelectorMethods = SelectorMethods.dummy(),
                           perform : Perform = Perform.sendTextToRobotFormKeyBoard(inputKeys,numberOfTabs),
                           checking: Checking = Checking.dummy())
        : Step(selectMet,perform,checking )


/**
 * invoking some specialized function on driver
 * */
class specialFunct (val specFunct :(dr : WebDriver) ->Unit,
                       selectMet : SelectorMethods = SelectorMethods.dummy(),
                       perform : Perform = Perform.specialFunct(specFunct),
                       checking: Checking = Checking.dummy(),timeToWait: Long=0)
    : Step(selectMet,perform,checking ,timeToWait)

 /**
  * looking into page source and on the basis of  the number of occurences of the text it will  press calculated number of tabs and  enter in the end
  * */
 class calculateTabsAndPress (val  textToSeek : String,
                        val formulaToCalculateTabs: (i : Int)->Int ,  timeToWait : Long =0 ,
                        selectMet : SelectorMethods = SelectorMethods.dummy(),
                        perform : Perform = Perform.findTextAndPressCalculatedTabs(textToSeek,formulaToCalculateTabs ),
                        checking: Checking = Checking.dummy())
     : Step(selectMet,perform,checking )

/**
 * will invoke some navigation functions like refresh or get back ...
 * */
class navigation ( perform : Perform ,selectMet : SelectorMethods = SelectorMethods.dummy(),

                    checking: Checking = Checking.dummy(),timeToWait: Long=0)
    : Step(selectMet,perform,checking ,timeToWait)

}
/**
 * sequences of steps that if any of them would not work we will get back to the specified site
 * listOfSteps - list of step objects indicating what needs to be done
 *  * repair - will choose way to repair a situation will be invoked if any check function of the steps will return false ()
 * */
class SubSection(val listOfSteps : List<Step>, val repair: Repair = Repair.refreshSiteAndStartOver(), var preCheck: PreCheck = PreCheck.dummy()){}


/**
 * combined subsections and handle errors
 * */
open class Section( val listOfSubSections: List<SubSection>){

}