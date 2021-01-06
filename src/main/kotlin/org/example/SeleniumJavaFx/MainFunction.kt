package org.example.SeleniumJavaFx

import com.sun.org.apache.xpath.internal.operations.Bool
import functionalUnits.LoginSection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mainFunction.*
import org.openqa.selenium.By
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import utils.Util
import utils.genereteStringWithNumberOfTabs
import java.awt.Robot
import java.awt.event.KeyEvent
import java.lang.Exception
import java.util.concurrent.TimeUnit

object MainFunction{

    var driver: WebDriver = prepareDriver ()
    var isExcBlocked = false// marks weather we want to block the execution oof steps


    fun prepareDriver ()  : WebDriver {
        System.setProperty("webdriver.gecko.driver", "/home/jagiellonczyk/IdeaProjects/SeleniumKotlin33/src/main/resources/geckodriver-v0.27.0-linux64/geckodriver")
//val blank = when(Util.oS){
//    Util.OS.WINDOWS ->  System.setProperty("webdriver.gecko.driver", "C:\\geckodriver-v0.27.0-win64\\geckodriver")
//    Util.OS.LINUX -> System.setProperty("webdriver.gecko.driver", "//home/jakub/firefox/geckodriver")
//    Util.OS.MAC ->  System.setProperty("webdriver.gecko.driver", "//home/jakub/firefox/geckodriver")
//    Util.OS.SOLARIS ->  System.setProperty("webdriver.gecko.driver", "//home/jakub/firefox/geckodriver")
//    null ->  System.setProperty("webdriver.gecko.driver", "//home/jakub/firefox/geckodriver")
//}

        return prepareDriverHelper1 ()


    }


    fun prepareDriverHelper1 (): WebDriver {
val driv =  prepareDriverHelper2 ()
    if(driv!=null){
      return  driv!!}
   return    prepareDriverHelper1 ()

    }

    fun prepareDriverHelper2 (): WebDriver? {
try {

    val capabilities = DesiredCapabilities()
    capabilities.platform = Platform.LINUX
    val opt = FirefoxOptions(capabilities)
    val driver: WebDriver = FirefoxDriver(opt)
    driver.manage().timeouts().implicitlyWait(9000, TimeUnit.SECONDS);
    driver.manage().deleteAllCookies()
    try {
        driver.get("https://wssp.eduportal.pl/Zaloguj");
        driver.manage().window().maximize();
    } catch (ex : Exception){
        driver.close()
return null
    }
        return driver
} catch (ex : Exception) {

    Thread.sleep(40000)

}
        return null

    }



    /**
     * we will process here data in order to complete the step - it will be generally related to single elementon the side
     * return true if execution was succesfull
     * */
    fun processStep(step: Step) : Boolean {
//try {
    // selecting node
 if(step.selectMet !is SelectorMethods.dummy) {
     val node: WebElement = selectNode(step.selectMet)!!


     ////////////////////// performing action
     performAction(step.perform, node, step.timeToWait)


     //////////////// checking weather all wen well
     var  isCompleted = CheckNode(step.checking, node)
     if(!isCompleted){ runBlocking { delay(5000) }
         isCompleted = CheckNode(step.checking, node)
     }
     if(!isCompleted){ runBlocking { delay(5000) }
         isCompleted = CheckNode(step.checking, node)
     }


     return isCompleted
 } else{
     runBlocking { delay(step.timeToWait) }
     val perform = step.perform
     val blank = when(perform){
         is Perform.click -> {

         }
         is Perform.sendText -> {
         }
         is Perform.openWebpage -> {driver.get(perform.webpageUrl)
         }
         is Perform.sendTextToDummy -> {sendTextTodummyy(perform)
         }
         is Perform.specialFunct -> {perform.specializedFunct(driver)
         }
         is Perform.refreshWebsite -> {
             driver.navigate().refresh()
         }
         is Perform.navigateBack -> {
             driver.navigate().back()

         }
         is Perform.sendTextToRobot -> {
    sendKeysToRobot(perform)



         } // in case it is more complicated
         is Perform.sendTextToRobotFormKeyBoard -> {
             val rb = Robot()
             rb.autoDelay = 1
             for(i in 1..perform.numberOfTAbs){
                 rb.keyPress(KeyEvent.VK_TAB)
                 rb.keyRelease(KeyEvent.VK_TAB)}

             perform.listOfListsKeysToPress.forEach {innerList->
                 innerList.forEach {  rb.keyPress(it)  }
                 innerList.forEach {  rb.keyRelease(it)  }
             }
         }
         is Perform.findTextAndPressCalculatedTabs -> {
             calculateTabsAndPres(perform.textToSeek,perform.formulaToCalculateTabs)


         }

     }
val checkk = step.checking
         return   when (checkk){

             is Checking.checkNodeText -> {

                 // node.text.contains(check.inputText)
true             }
             is Checking.checkisCorrWebsite -> {
                 driver.currentUrl.contains(checkk.websiteUrl)}
             is Checking.dummy -> {
                 true     }
             is Checking.customChecking -> checkk.funct.invoke(driver)
             is Checking.isThereSuchTexxt -> {
                 println("in chckk is textt  ${checkk.textToCheck}")

             val     res = isThereSuchText(checkk.textToCheck)
//                 val res = driver.findElements(By.xpath("//*"))
//                         .any {it.
//                             it.text == checkk.textToCheck }
                 println("in chckk is textt  ${checkk.textToCheck} ${res}")

                 res


             }
         }
 }

//}catch (ex : Exception){
//    return false
//}
}



    /**
     * performs action
     * */
    fun performAction(perform: Perform, node: WebElement, timeToWait: Long) {
        GlobalScope.launch { Checker.act.send(kotlinx.datetime.Clock.System.now())}
      //  runBlocking { delay(500) }
        runBlocking { delay(timeToWait) }
        val action = when (perform) {
            is Perform.click -> {
                runBlocking { delay(perform.timeToWait) }
                node.click()
            }
            is Perform.sendText -> {
                node.sendKeys(perform.textToSend)
            }
            is Perform.openWebpage -> {driver.get(perform.webpageUrl)
            }
            is Perform.sendTextToDummy -> {sendTextTodummyy(perform)
            }
            is Perform.specialFunct -> {perform.specializedFunct(driver)
            }
            is Perform.refreshWebsite -> {
            driver.navigate().refresh()
            }
            is Perform.navigateBack -> {
                driver.navigate().back()

            }
            is Perform.sendTextToRobot -> {}
            is Perform.sendTextToRobotFormKeyBoard -> {

            }
            is Perform.findTextAndPressCalculatedTabs -> {
                calculateTabsAndPres(perform.textToSeek,perform.formulaToCalculateTabs)

            }
        }

    }
    fun sendTextTodummyy(perform: Perform.sendTextToDummy) {
   //     driver.findElement(By.tagName("body"))
        driver.switchTo().activeElement()
                .sendKeys(genereteStringWithNumberOfTabs(perform.numberOfTAbs)+ perform.textToSend)

    }


    /**
     * select Node if it will be null it wats - so maybe onode will appear
     * */
    fun selectNode(selectMet: SelectorMethods):WebElement?{

      val node=  selectNodeHelper(selectMet)
         if(node==null || !node.isDisplayed){
runBlocking { delay(2000) }
        return  selectNodeHelper(selectMet)
        }

        if(node==null || !node.isDisplayed){
            runBlocking { delay(6000) }
            return  selectNodeHelper(selectMet)
        }


        return node
    }

    /**
     * selectNodeHelper
     * */
     fun selectNodeHelper (selectMet: SelectorMethods) :WebElement?{
       return when (selectMet){
            is SelectorMethods.bySelector -> {driver.findElement(By.cssSelector(selectMet.selectStr))}
           is SelectorMethods.dummy -> {null        }
           is SelectorMethods.byText -> {   driver.findElement(By.linkText(selectMet.selectStr.trim()))        }
       }}


/**
 * perform check is all good
 * */
fun CheckNode(check: Checking, node: WebElement) : Boolean{
    return when (check){

        is Checking.checkNodeText -> {
        return node.isDisplayed
        }
        is Checking.checkisCorrWebsite -> { driver.currentUrl.contains(check.websiteUrl)}
        is Checking.dummy -> {
            true     }
        is Checking.customChecking -> check.funct.invoke(driver)
        is Checking.isThereSuchTexxt -> {
            val res = driver.findElements(By.xpath("//*"))
                    .any{ it.text == check.textToCheck }

            res
        }
    }


}

    /**
     * processing the sequences of actions that if any of them would not work we will get back to the specified site
     *returns true if succesfull
     * */
    fun processSubSection(subSect : SubSection) : Boolean {
        val preCh = subSect.preCheck
        val blank = when(preCh){
            is PreCheck.dummy -> {true}
            is PreCheck.isThereSuchText -> {

               val res = isThereSuchText(preCh.textToSeek)
                      // filter { it!='\n' }

               isExcBlocked = res
                res
            }
            is PreCheck.resetPreCheck -> {
                isExcBlocked = false
            false
            }
            is PreCheck.isThereSuchTextTimes -> {
//                val res =  driver.pageSource.contains(preCh.textToSeek)
//                println("is there such text ${preCh.textToSeek}  res ${res}")
//
//                isExcBlocked = res
//                res
                (MainFunction.driver.pageSource.split("${preCh.textToSeek} topic").size - 1) >= preCh.times
            }
        }


if(!isExcBlocked) {
    iterateSteps(subSect.listOfSteps, subSect.repair,preCh )
}
        return true


    }
    /**
     * iterate recurrently over steps uintil it completes whole section in case of failure it performs repair action
     * */
    fun iterateSteps (listOfSteps: List<Step>, repair: Repair, preCh: PreCheck){
       val currUrl =  driver.currentUrl
        try {
            listOfSteps.forEach { step ->
                if (!processStep(step)) {
                    println("false in some step")
                    repairing(repair, currUrl)
                    if(preCh is PreCheck.isThereSuchText &&isThereSuchText(preCh.textToSeek)){
                        iterateSteps(listOfSteps, repair, preCh)}
                }
            }
        } catch (exc : Exception){
            repairing(repair, currUrl)
            if(preCh is PreCheck.isThereSuchText && isThereSuchText(preCh.textToSeek)){
            iterateSteps(listOfSteps, repair, preCh)
        }}
    }

/**
 * invoked when sth happens wrong in any step
 * */
fun repairing(repair: Repair, currUrl: String){
    val blank = when(repair){
        is Repair.refreshSiteAndStartOver -> {
           try {
           driver.manage().deleteAllCookies()
           driver.quit()
           }catch (exc : Exception) {}
            driver = prepareDriver ()
            driver.get("https://wssp.eduportal.pl/Zaloguj")
            Thread.sleep(3000)
            LoginSection().listOfSubSections.flatMap { it.listOfSteps }.forEach {  MainFunction.processStep(it)}
            Thread.sleep(3000)
            driver.get(currUrl)
            Thread.sleep(3000)

           }
        is Repair.getToSomeParticularSide -> {driver.get(repair.siteUrl)}
    }
}


    /**
     * we will process combined subsections and handle errors
     * */
    fun processSection(sect:Section) {

        sect.listOfSubSections.forEach {

            processSubSection(it)
        }


    }



fun calculateTabsAndPres (textToSeek : String, functionToClalculateTabs : (i: Int)->Int){

    val textNum = driver.pageSource.split(textToSeek) .size-1 // will get information how many topics we have
   println("textNum  "+textNum)

    sendKeysToRobot(Perform.sendTextToRobot(listOf(KeyEvent.VK_ENTER),functionToClalculateTabs(textNum)))
    Thread.sleep(5000)

}



}

fun isThereSuchText (text : String) : Boolean{
    println("isThereSuchText " + text)
    if(text.contains("Phenotype & genotype correlation")) { return true} // TODO(remove)
    if(text.contains("Nuclic acids:DNA & RNA || 1A. Intro The Cellular and Molecular Basis of Inheri")) { return true} // TODO(remove)
    if(text.contains("Duchenne & Sickle cell || elG6t  fHk9b  5. Cancer Genetics, Clinical Genetics and Genetic Counseling")) { return true} // TODO(remove)
    if(text.contains("Hemophilia & Huntington dieases || elG6t  fHk9b  5. Cancer Genetics, Clinical Genetics and Genetic Counseling")) { return true} // TODO(remove)
    if(text.contains("CF& Fragile X || elG6t  fHk9b  5. Cancer Genetics, Clinical Genetics and Genetic Counseling")) { return true} // TODO(remove)
    if(text.contains("Thalassimia & Tay Sachs  || elG6t  fHk9b  5. Cancer Genetics, Clinical Genetics and Genetic Counseling  topic")) { return true} // TODO(remove)
    return MainFunction.driver.pageSource.filter { it.isLetterOrDigit() }.contains(text.filter { it.isLetterOrDigit() })

}



fun sendKeysToRobot(perform: Perform.sendTextToRobot) {

    val rb = Robot()
    rb.autoDelay = 1
    for(i in 1..perform.numberOfTAbs){
        rb.keyPress(KeyEvent.VK_TAB)
        rb.keyRelease(KeyEvent.VK_TAB)}
    perform.listOfKeysToPress.forEach {

        rb.keyPress(it)
        rb.keyRelease(it)
    }

}