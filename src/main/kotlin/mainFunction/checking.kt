package mainFunction

import org.openqa.selenium.WebDriver

/**
 * will hold information to perform check weather step was succesfull;
 * generally if not first thing should be just to wait a second and try one more time
 * */
sealed class Checking() {
    /** check will be performed by checking weater given node has some text in it*/
     class checkNodeText(val inputText: String) : Checking(){}
    /** checking are we on correct given website
     * websiteUrl url of website on which we should be */
     class checkisCorrWebsite (val websiteUrl : String) : Checking(){}

    class dummy() : Checking()
    /**
     * a way to get a custom checinkg of success
     * */
    class customChecking (val funct: (dr : WebDriver)->Boolean) : Checking()
    /**
     * checking weatjer on current screen we have what we wanted by checking if any node contains given text
     * */
    class isThereSuchTexxt (val textToCheck : String) : Checking()
}