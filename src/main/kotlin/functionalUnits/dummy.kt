package functionalUnits

import mainFunction.Section
import mainFunction.SubSection

///**
// * creating subject and adding appropriate students groups to it
// * */
//class createSubjectAndSetStudents : Section(
//        {
//            val subsection1 = SubSection
//
//            listOf<SubSection>()}.invoke() )

/**
 *
 * package functionalUnits

import Google.GDrive.DownloadPres
import mainFunction.*
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import utils.Keyboard
import utils.genereteStringWithNumberOfTabs
import java.awt.event.KeyEvent

/**
 * this part will start editing the subject to add appropriate modules
 * */
class startEditingSubject(val title: String, groupsList: List<String>, val subModulesToAdd: List<subModule>) : Section(
{
val subsection1 = SubSection(listOf(
Step.openWebpage("https://wssp.eduportal.pl/Lms/SciezkaSzkoleniowa/SciezkiSzkoleniowe"),
Step.click(selectingString = title, selectMet = SelectorMethods.byText(title), checking = Checking.dummy()),
), Repair.refreshSiteAndStartOver())

/**
 * generic way to add sth to szkolenie like article form ...
 * */
fun addToSzkolenie(title: String, numberOfNeededTabs : Int
, stepsAfterChoosing : List<Step>
) : SubSection{

return SubSection(listOf(

Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->  Thread.sleep(15000)    }),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),numberOfNeededTabs,1000), // getting to adding link
 *stepsAfterChoosing.toTypedArray(),
Step.specialFunct({ driver ->  Thread.sleep(15000)    }),
Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000,
checking = Checking.customChecking { driver ->
driver.findElements(By.xpath("//*"))
.any { it.text == title }

}),


), Repair.refreshSiteAndStartOver())


}

/**
 * adding link to szkolenie
 * */
fun addLink(linkTitle: String, linkURL : String): SubSection {
return addToSzkolenie(linkTitle, 16, listOf(
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(linkTitle)!!.toList()),
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(linkURL)!!.toList(),1,1000), // title for link
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,2000),
)) }

/**
 * adding article explaining sth
 * */
fun addArticle (title: String, articleContent : String): SubSection {


return addToSzkolenie(title,11,  listOf(
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("so"
//        " summarizing your answer may look for example like that (it may be longer or shorter and may be phrased diffrently there is no one good answer in open questions :)" TODO(unhash)
)!!.toList(),0,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("Podstawy turystyki")!!.toList(),1,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(articleContent)!!.toList(),6,1000), // title for link
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,1000),
Step.specialFunct({dr: WebDriver ->  dr.switchTo().activeElement().submit()}),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),1,1000),
Step.navigation(perform = Perform.navigateBack(), timeToWait = 5000)
)                )


}
/**
 * adding lekcja to szkolenie
 * */
fun addLekcja(lessonTitle: String, linkURL : String, subModuleTitle: String, presentationFileId: String) : SubSection{

return addToSzkolenie(lessonTitle, 5, listOf(
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(subModuleTitle)!!.toList(),0,5000),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),5,2000),

Step.navigation(Perform.refreshWebsite(),timeToWait = 2000),

Step.click("div.btn-group:nth-child(10) > a:nth-child(1)", checking = Checking.dummy(), timeToWait = 1000),
Step.click("button.btn:nth-child(4)", checking = Checking.dummy(), timeToWait = 1000),
Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE),0,1000),
// downloading the presentation to downloads folder
Step.specialFunct({ driver ->
DownloadPres.getPresToFile(presentationFileId)
}),
/////////////////////// file picker to add presentation
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),8,1000), // getting to downloads
Step.sendKeysToRobot(listOf(KeyEvent.VK_RIGHT,KeyEvent.VK_RIGHT,KeyEvent.VK_DOWN),0,10000),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,3000),
Step.specialFunct({ driver ->
Thread.sleep(15000)
}),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),1,2000),// accepting import of screens
//Step.sendKeysToDummy(Keys.ENTER.toString(),1,15000),// accepting import of screens
//   Step.specialFunct({dr: WebDriver ->  dr.switchTo().activeElement().submit()}),

Step.navigation(Perform.navigateBack(), timeToWait = 40000),
Step.navigation(Perform.navigateBack(), timeToWait = 1000)


)) }






/**
 * creating new szkolenie  - just starting position to enable adding elements to it
 * */
fun createSzkolenie(subModuleTitle : String) : SubSection {
return     SubSection(listOf(

Step.click(selectingString = "div.btn-group:nth-child(2) > button:nth-child(1)", checking = Checking.dummy()),
Step.sendKeysToDummy( Keys.ENTER.toString(),1),
Step.sendKeysToDummy( subModuleTitle,0),
Step.sendKeysToDummy( Keys.ARROW_UP.toString()+Keys.ARROW_UP.toString()+Keys.ARROW_UP.toString(),1),
Step.sendKeysToDummy( Keys.SPACE.toString(),6),
Step.sendKeysToDummy( "5",1),
Step.sendKeysToDummy( Keys.ENTER.toString(),0),
Step.sendKeysToDummy( Keys.ESCAPE.toString(),0),
Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000,
checking = Checking.customChecking { driver ->
driver.findElements(By.xpath("//*"))
.any { it.text == subModuleTitle }
}),


), Repair.refreshSiteAndStartOver())

}

/**
 * slect last szkolenie and open it for futher modifications
 * */
fun selectLastSzkolenie(subModuleTitle : String) : SubSection {
return     SubSection(listOf(

Step.click(".action-menu-group > div:nth-child(1) > a:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->
val page = driver.findElements(By.xpath("//*"))
val szkolenNum = page.filter { it.text == "Szkolenie" }.size// will get information how many Szkolenie Submodules were already added
driver.findElement(By.cssSelector("div.btn-group:nth-child(4) > button:nth-child(1)")).sendKeys(Keys.ESCAPE.toString() + genereteStringWithNumberOfTabs(szkolenNum * 4 + 1) + Keys.ENTER.toString())
}),
Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000,
checking = Checking.customChecking { driver ->
driver.findElements(By.xpath("//*"))
.any { it.text == subModuleTitle }
}),


), Repair.refreshSiteAndStartOver())

}

/**
 * create new Topic in szkolenie
 * */
fun createNewTopicInSzkolenie(subModuleTitle : String) : SubSection {
return     SubSection(listOf(
Step.sendKeys("#SzkolenieTematNazwa", " topic "+subModuleTitle, timeToWait = 2000),
Step.click("div.btn-group:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),

Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000,
checking = Checking.customChecking { driver ->
driver.findElements(By.xpath("//*"))
.any { it.text == subModuleTitle }
}),


), Repair.refreshSiteAndStartOver())

}

//"Click the link fill the form and get back to this website , remember answer should reflect what you remember and use simple terms do not copy it from internet "

listOf<SubSection>(subsection1, *subModulesToAdd.map { subModule ->

/******************************    here we are adding data depending at each submodule       **************************************************/

val openQAndTests =   subModule.listOfopenQAndVideo.map {openQAndVid->
listOf(

Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->
Thread.sleep(15000)
}),

Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),16,1000), // getting to adding link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("Click the link fill the form and get back to this website , remember answer should reflect what you remember and use simple terms do not copy it from internet ")!!.toList(),0,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(openQAndVid.openQFormLink)!!.toList(),1,1000), // title for link
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,2000),
Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000),


// adding youtube video

Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->
Thread.sleep(15000)
}),

Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),16,1000), // getting to adding link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("video" //TODO(add more text)
//     " the link fill the form and get back to this website , remember answer should reflect what you remember and use simple terms do not copy it from internet "
)!!.toList(),0,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(openQAndVid.video)!!.toList(),1,1000), // title for link
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,2000),
Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000),



// adding video comment
Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->
Thread.sleep(15000)
}),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),11,1000), // getting to adding link
Step.specialFunct({ driver ->
Thread.sleep(15000)
}),
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("so"
//        " summarizing your answer may look for example like that (it may be longer or shorter and may be phrased diffrently there is no one good answer in open questions :)" TODO(unhash)
)!!.toList(),0,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("Podstawy turystyki")!!.toList(),1,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(openQAndVid.videoDescription)!!.toList(),6,1000), // title for link
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,1000),
Step.specialFunct({dr: WebDriver ->  dr.switchTo().activeElement().submit()}),
Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),1,1000),
Step.navigation(perform = Perform.navigateBack(), timeToWait = 5000)

)
}.flatten()


SubSection(listOf(
//////////////////////////////////////////////////creating SZKOLENIE
//                        Step.click(selectingString = "div.btn-group:nth-child(2) > button:nth-child(1)", checking = Checking.dummy()),
//                        Step.sendKeysToDummy( Keys.ENTER.toString(),1),
//                        Step.sendKeysToDummy( subModule.subModuleTitle,0),
//                        Step.sendKeysToDummy( Keys.ARROW_UP.toString()+Keys.ARROW_UP.toString()+Keys.ARROW_UP.toString(),1),
//                        Step.sendKeysToDummy( Keys.SPACE.toString(),6),
//                        Step.sendKeysToDummy( "5",1),
//                        Step.sendKeysToDummy( Keys.ENTER.toString(),0),
//                        Step.sendKeysToDummy( Keys.ESCAPE.toString(),0),

//////////////////////////////////////////////// Selecting last SZKOLENIE from list

Step.click(".action-menu-group > div:nth-child(1) > a:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->
val page = driver.findElements(By.xpath("//*"))
val szkolenNum = page.filter { it.text == "Szkolenie" }.size// will get information how many Szkolenie Submodules were already added
driver.findElement(By.cssSelector("div.btn-group:nth-child(4) > button:nth-child(1)")).sendKeys(Keys.ESCAPE.toString() + genereteStringWithNumberOfTabs(szkolenNum * 4 + 1) + Keys.ENTER.toString())
}),
//                        //////////////////////////////////////////////// adding new topic
//                        Step.sendKeys("#SzkolenieTematNazwa", subModule.subModuleTitle, timeToWait = 2000),
//                        Step.click("div.btn-group:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
//                        Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
//
//                        //////////////////////////////////////////////// creating lesson
//                        Step.specialFunct({ driver ->                          Thread.sleep(5000)                        }),
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),5,15000),
//                        Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(subModule.subModuleTitle)!!.toList(),0,5000),
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),5,2000),
//
//                        Step.navigation(Perform.refreshWebsite(),timeToWait = 2000),
//
//                        Step.click("div.btn-group:nth-child(10) > a:nth-child(1)", checking = Checking.dummy(), timeToWait = 1000),
//                          Step.click("button.btn:nth-child(4)", checking = Checking.dummy(), timeToWait = 1000),
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE),0,1000),
//                       // downloading the presentation to downloads folder
//                        Step.specialFunct({ driver ->
//                          DownloadPres.getPresToFile(subModule.presentationFileId)
//                                }),
//                        /////////////////////// file picker to add presentation
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),8,1000), // getting to downloads
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_RIGHT,KeyEvent.VK_RIGHT,KeyEvent.VK_DOWN),0,10000),
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,3000),
//                        Step.specialFunct({ driver ->
//                            Thread.sleep(15000)
//                        }),
//                        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),1,2000),// accepting import of screens
//                        //Step.sendKeysToDummy(Keys.ENTER.toString(),1,15000),// accepting import of screens
//                     //   Step.specialFunct({dr: WebDriver ->  dr.switchTo().activeElement().submit()}),
//
//                        Step.navigation(Perform.navigateBack(), timeToWait = 40000),
//                        Step.navigation(Perform.navigateBack(), timeToWait = 1000),
/////////////////////// adding open questions
 *openQAndTests.toTypedArray(),
// adding closed qestion as a link
Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
Step.specialFunct({ driver ->
Thread.sleep(15000)
}),                        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),16,1000), // getting to adding link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("" +
"clo"
//        "sed questions this will be simmilar to final test"// TODO(unhash)
)!!.toList(),0,1000), // title for link
Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(subModule.linkToClosedTest)!!.toList(),1,1000), // title for link
Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000)
// adding appropriate forum ... TODO()

)
, Repair.refreshSiteAndStartOver())


}.toTypedArray())
}.invoke())

/**
 * summarizing all data needed for creatingSubmodule
 * */
data class subModule(val subModuleTitle: String, val presentationFileId: String, val listOfopenQAndVideo: List<openQAndVideo>, var linkToClosedTest : String){
init {
linkToClosedTest=  linkToClosedTest.removeSuffix("?usp=sf_link")
}
}

/**data about single or multiple of open questions and related videos and video comments
 * open questions will be asked in a form of google presentation and only link will be provided
 * here we will also post a link to anonymized students answers so students will be able to analyze them
 * */
data class openQAndVideo(val video: String, var openQFormLink: String, val videoDescription: String){
init {
openQFormLink=  openQFormLink.removeSuffix("?usp=sf_link")
}

}
/**
 * question data so question and answers data
 * */
data class closedQuestionToAdd(val qestion: String, val answerList: List<answerData>)

/**
 * AnswerData
 * */
data class answerData(val answerStr: String, val isCorrect: Boolean)

///**
// * information needed to conduct check weather lekcja or forum or artykul to szkolenie
// * */
//data class szkolenieCheckInfo(val typeOfAddedSection : String, )

//gora
//#\32 627 > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)
//srodek
//#\32 628 > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)
//dol
//#\32 629 > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)
//
//
//#\32 630 > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)
//#\32 631 > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)
//
//

 * */