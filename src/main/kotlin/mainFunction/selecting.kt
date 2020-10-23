package mainFunction

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * will hold information how to select specified node
 * */
sealed class SelectorMethods ( ){

    class bySelector(val selectStr : String) : SelectorMethods() {}
    class byText(val selectStr : String) : SelectorMethods() {}
    class dummy () : SelectorMethods()

}