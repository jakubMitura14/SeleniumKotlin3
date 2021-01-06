package functionalUnits

import Google.GDrive.DownloadPres
import functionalUnits.props.isAnyLessonAdded
import kotlinx.datetime.*
import mainFunction.*
import org.example.SeleniumJavaFx.MainFunction
import org.example.SeleniumJavaFx.isThereSuchText
import org.example.SeleniumJavaFx.sendKeysToRobot
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import utils.Keyboard
import utils.genereteStringWithNumberOfBackspaces
import utils.genereteStringWithNumberOfTabs
import java.awt.event.KeyEvent
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter





object props {
    var isAnyLessonAdded = false// marking weather any lesson was added in this session already
}
fun getIntoSubject(title: String) = SubSection(listOf(
        Step.openWebpage("https://wssp.eduportal.pl/Lms/SciezkaSzkoleniowa/SciezkiSzkoleniowe"),
        Step.specialFunct({ driver -> Thread.sleep(4000) }),
        Step.openWebpage("https://wssp.eduportal.pl/Lms/SciezkaSzkoleniowa/SciezkiSzkoleniowe"),
        Step.specialFunct({ driver -> Thread.sleep(5000) }),
        Step.sendKeys(selectingString = "#Search",
                genereteStringWithNumberOfBackspaces(40)),
        Step.specialFunct({ driver -> Thread.sleep(1000) }),

        Step.sendKeys("#Search",title),
        Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
        Step.specialFunct({ driver -> Thread.sleep(8000) }),
        Step.click(selectingString = title, selectMet = SelectorMethods.byText(title), checking = Checking.dummy()),
), Repair.refreshSiteAndStartOver())



//in order for task to be seen we need to calculate days from begining of the semester  so for example if begining would be 26.09.2020 and we would want to make it available for students on 28.09.2020 we will give days from set to 1  and duration 2 days
 fun creteSingleTask(taskInfo: TaskInfo) = SubSection(
        listOf(
                Step.click("div.btn-group:nth-child(2) > button:nth-child(1)", checking = Checking.dummy()),
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 12),
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER), 0),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(taskInfo.taskTitle)!!.toList(), 1),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(taskInfo.taskDescription)!!.toList(), 1),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(taskInfo.taskInstructions)!!.toList(), 1),

            Step.sendKeysToRobot(listOf(), 3),
                Step.sendKeysToRobot(listOf(), 1),

                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(getDaysFromTodayTillGivenDate(taskInfo.taskStartDate))!!.toList(), 8),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(taskInfo.durationDays.toString())!!.toList(), 1),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
            Step.specialFunct({ driver -> Thread.sleep(3000) }),
                Step.navigation(perform = Perform.refreshWebsite()),
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
//                Step.navigation(perform = Perform.specialFunct({}), timeToWait = 0, checking = Checking.isThereSuchTexxt(taskInfo.taskTitle))
        ), Repair.refreshSiteAndStartOver(), preCheck = PreCheck.isThereSuchText(taskInfo.taskTitle)
)


/**
 * adding a link
 * */
fun addForumImidiatelyToClassroom(taskInfo: TaskInfo) = SubSection(
        listOf(
                Step.click("div.btn-group:nth-child(2) > button:nth-child(1)", checking = Checking.dummy()),
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 5),
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(taskInfo.taskTitle)!!.toList(), 0),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(""
                //        taskInfo.taskDescription//TODO(unhash)
                )!!.toList(), 1),
                Step.specialFunct({ driver -> Thread.sleep(100) }),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 1),
                // now we need to add task description that may for example have the link to FAQ
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
                Step.navigation(perform = Perform.refreshWebsite()),
                Step.specialFunct({ driver -> Thread.sleep(5000) }),
                Step.sendKeys("div.btn-group:nth-child(4) > button:nth-child(1)", Keys.ESCAPE.toString(), checking = Checking.dummy()),// selecting ustawiernia


                Step.specialFunct({ driver ->

                    val page = driver.findElements(By.xpath("//*"))
                    val zadanieNum = page.filter { it.text == "Zadanie" }.size  // will get information how many zadanie we have
                    val ForumNum = page.filter { it.text == "Forum" }.size  // will get information how many Forum we have
                    sendKeysToRobot(Perform.sendTextToRobot(listOf(KeyEvent.VK_ENTER), (4 + (4*(zadanieNum) + (ForumNum-1)*3))))
                    // driver.findElement(By.cssSelector("div.btn-group:nth-child(4) > button:nth-child(1)")).sendKeys(Keys.ESCAPE.toString() + genereteStringWithNumberOfTabs(szkolenNum * 4 + 1) + Keys.ENTER.toString())
                }),
                //we are inside the forum
                //first adding new topic
                Step.specialFunct({ driver -> Thread.sleep(6000) }),
                Step.click(".ibox-control > button:nth-child(1)", checking = Checking.dummy()),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(taskInfo.taskDescription)!!.toList(), 0),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
                Step.specialFunct({ driver -> Thread.sleep(6000) }),


                Step.navigation(perform = Perform.navigateBack()),
                Step.navigation(perform = Perform.navigateBack()),

                Step.specialFunct({ driver -> Thread.sleep(8000) }),
                Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000, checking = Checking.isThereSuchTexxt(taskInfo.taskTitle))

        ), Repair.refreshSiteAndStartOver(), preCheck = PreCheck.isThereSuchText(taskInfo.taskTitle)
)


/**
 * with given local date it will calculate how many days it is from begining of the semester
 * */
 fun getDaysFromTodayTillGivenDate(enteredDate: LocalDate) : String {
    val counted = enteredDate.minus(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date).days
return if(counted>0){ counted.toString() } else{""}
}




/**
 * generic way to add sth to szkolenie like article form ...
 * numberOfTabsToPassATopic is important in case we want to organise material into topics so we need to calculate how many tabs we need to presess between dodaj of previous topic to dodaj of next topic for example in passive szkolenie it is 54
 * */
fun addToSzkolenie(title: String, numberOfNeededTabs: Int, numberOfTabsToPassATopic: Int, stepsAfterChoosing: List<Step>
) : SubSection {

    return SubSection(listOf(
            Step.specialFunct({ driver -> Thread.sleep(5000) }),

            // Step.click("div.m-l-xs:nth-child(7) > button:nth-child(1)", checking = Checking.dummy()),
            //Step.navigation(perform = Perform.refreshWebsite()),
            Step.sendKeys("div.btn-group:nth-child(4) > button:nth-child(1)", Keys.ESCAPE.toString(), checking = Checking.dummy()),// selecting zawartosc szkolenia
           Step.calculateTabsAndPress("txtEdycjaSzkolenieTematNazwa" ,{(4 + ((it - 1) * numberOfTabsToPassATopic))}),
//            Step.specialFunct({ driver ->
////                val text ="Dodaj/Edytuj opis"
////                val topicsNum =    MainFunction.driver.pageSource.filter{ it in text}
////                        .groupingBy { it }
////                        .eachCount().size
//
//                val page = driver.findElements(By.xpath("//*"))
//                val topicsNum = page.filter { it.text == "Dodaj/Edytuj opis" }.size  // will get information how many topics we have
//                sendKeysToRobot(Perform.sendTextToRobot(listOf(KeyEvent.VK_ENTER), (4 + ((topicsNum - 1) * numberOfTabsToPassATopic))))
//                // driver.findElement(By.cssSelector("div.btn-group:nth-child(4) > button:nth-child(1)")).sendKeys(Keys.ESCAPE.toString() + genereteStringWithNumberOfTabs(szkolenNum * 4 + 1) + Keys.ENTER.toString())
//            }),
            Step.specialFunct({ driver -> Thread.sleep(6000) }),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), numberOfNeededTabs), // getting to adding link
            Step.specialFunct({ driver -> Thread.sleep(6000) }),

            *stepsAfterChoosing.toTypedArray(),
            Step.specialFunct({ driver -> Thread.sleep(2000) }),
            Step.navigation(perform = Perform.refreshWebsite()),

            Step.specialFunct({ driver -> Thread.sleep(5000) }),

            Step.navigation(perform = Perform.refreshWebsite(),  checking = Checking.isThereSuchTexxt(title)),
                    Step.specialFunct({ driver -> Thread.sleep(5000) }),

            ), Repair.refreshSiteAndStartOver(), preCheck = PreCheck.isThereSuchText(title))

//"EdytujTematOpis"
//<i class="fa fa-edit"></i> Dodaj/Edytuj opis

}

/**
 * adding forum to szkolenie
 * */
fun addForum(forumTitle: String, forumInstructions: String, numberOfTabsToPassATopic: Int): SubSection {
    return addToSzkolenie(forumTitle, 4, numberOfTabsToPassATopic, listOf(
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(forumTitle)!!.toList()),
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(forumInstructions)!!.toList(), 1, 1000), // title for link
            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 1, 2000),
    )) }



/**
 * adding link to szkolenie
 * */
fun addLink(linkTitle: String, linkURL: String, numberOfTabsToPassATopic: Int): SubSection {
    return addToSzkolenie(linkTitle, 16, numberOfTabsToPassATopic, listOf(
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(linkTitle)!!.toList()),
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(linkURL)!!.toList(), 1), // title for link
            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
    )) }


/**
 * adding article explaining sth to szkolenie
 * */
fun addArticle(title: String, articleContent: String, numberOfTabsToPassATopic: Int): SubSection {


    return addToSzkolenie(title, 11, numberOfTabsToPassATopic, listOf(
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(title)!!.toList(), 0, 1000), // title for link
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("Podstawy turystyki")!!.toList(), 1, 1000), // title for link
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(articleContent)!!.toList(), 6, 1000), // title for link
            //Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER),0,1000),
            Step.specialFunct({ dr: WebDriver -> dr.switchTo().activeElement().submit() }),
            Step.navigation(perform = Perform.navigateBack())
    ))


}
/**
 * adding lekcja to szkolenie
 * */

fun addLekcja(lessonTitle: String, subModuleTitle: String, presentationFileId: String, numberOfTabsToPassATopic: Int) : SubSection {
    var site = ""
    val res =
        addToSzkolenie(lessonTitle, 5, numberOfTabsToPassATopic, listOf(

            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(lessonTitle)            !!.toList(), 0),

            Step.specialFunct({ driver ->       Thread.sleep(2000) }),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 5),
            Step.specialFunct({ driver ->       Thread.sleep(4000) }),




//            Step.specialFunct({ driver ->
//                site = driver.currentUrl
//                driver.manage().deleteAllCookies()
//                driver.quit()
//                MainFunction.driver = MainFunction.prepareDriver()
//                MainFunction.driver.get("https://wssp.eduportal.pl/Zaloguj")
//                Thread.sleep(3000)
//                LoginSection().listOfSubSections.flatMap { it.listOfSteps }.forEach { MainFunction.processStep(it) }
//                Thread.sleep(3000)
//                MainFunction.driver.get(site)
//                Thread.sleep(3000)
//            }),
//
//            // Step.click("div.btn-group:nth-child(10) > a:nth-child(1)", checking = Checking.dummy(), timeToWait = 1000),
//            Step.sendKeys("div.btn-group:nth-child(4) > button:nth-child(1)", Keys.ESCAPE.toString(), checking = Checking.dummy()),// selecting zawartosc szkolenia
//            Step.calculateTabsAndPress("txtEdycjaSzkolenieTematNazwa" ,{((4 + ((it - 1) * numberOfTabsToPassATopic))+7)}),
////            Step.specialFunct({ driver ->
////
////
//////                val text ="Dodaj/Edytuj opis"
//////                val topicsNum =    MainFunction.driver.pageSource.filter{ it in text}
//////                        .groupingBy { it }
//////                        .eachCount().size
////
////                val page = driver.findElements(By.xpath("//*"))
////                val topicsNum = page.filter { it.text == "Dodaj/Edytuj opis" }.size  // will get information how many topics we have
////
////
////
////                sendKeysToRobot(Perform.sendTextToRobot(listOf(KeyEvent.VK_ENTER), (11 + ((topicsNum - 1) * numberOfTabsToPassATopic))))
////            }),
//
//            Step.specialFunct({ dr: WebDriver -> Thread.sleep(5000) }),
//            // clicking importuj ekrany
//            //button.btn:nth-child(4)
//            Step.click("button.btn:nth-child(4)", checking = Checking.dummy(), timeToWait = 1000),
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE), 0, 1000),
//            // downloading the presentation to downloads folder
//            Step.specialFunct({ driver ->
//                DownloadPres.getPresToFile(presentationFileId)
//            }),
//            /////////////////////// file picker to add presentation
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 8), // getting to downloads
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_RIGHT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN), 0, 8000),
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
//            Step.specialFunct({ driver ->
//                Thread.sleep(8000)
//            }),
//
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 1),// accepting import of screens
//            //Step.sendKeysToDummy(Keys.ENTER.toString(),1,15000),// accepting import of screens
//            //   Step.specialFunct({dr: WebDriver ->  dr.switchTo().activeElement().submit()}),
//
//            Step.navigation(Perform.navigateBack(), timeToWait = 20000),
//            Step.navigation(Perform.navigateBack()),




            Step.navigation(Perform.refreshWebsite()),
//            Step.specialFunct({ driver ->
//                Thread.sleep(10000)
//            }),

            ))
    isAnyLessonAdded=true
return res

}


/**
 * add folder for file uploading with getting some task done
 * */
fun addFolder(folderTitle: String, numberOfTabsToPassATopic: Int): SubSection {
    return addToSzkolenie(folderTitle, 10, numberOfTabsToPassATopic, listOf(
            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(folderTitle)!!.toList(), 0), Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
            Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000)
    ))

}


/**
 * creating new szkolenie  - just starting position to enable adding elements to it
 * */
fun createSzkolenie(subModuleTitle: String, dates: startAndEndDateTime? = null) : SubSection {
    // dates only will be added if provided
    var listOfDatesSteps = listOf<Step>()
    if(dates!= null){
        dates.endDate= dates.startDate.toInstant(TimeZone.UTC).plus(6, DateTimeUnit.MONTH, TimeZone.currentSystemDefault()).toLocalDateTime(TimeZone.currentSystemDefault())
        listOfDatesSteps= listOf(
                Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE), 5),
            //    Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatDateToAsseco(dates.startDate.date))!!.toList(), 1), Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("${dates.startDate.hour}:${dates.startDate.minute}")!!.toList(), 1), Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatDateToAsseco(dates.endDate.date))!!.toList(), 1), Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("${dates.endDate.hour}:${dates.endDate.minute}")!!.toList(), 1),
                Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatDateToAsseco(dates.startDate.date))!!.toList(), 1), Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("${dates.startDate.hour}:${dates.startDate.minute}")!!.toList(), 1), Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatDateToAsseco(dates.endDate.date))!!.toList(), 1), Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts("${dates.endDate.hour}:${dates.endDate.minute}")!!.toList(), 1),
                Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE), 2)

        )

    }

    return     SubSection(listOf(

            Step.click(selectingString = "div.btn-group:nth-child(2) > button:nth-child(1)", checking = Checking.dummy()),
            Step.specialFunct({ driver -> Thread.sleep(8000) }),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 2),
            Step.specialFunct({ driver -> Thread.sleep(8000) }),
            Step.sendKeysToDummy(subModuleTitle, 0),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_UP), 1),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE), 6),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_5), 1),
            *listOfDatesSteps.toTypedArray(),
            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
            Step.sendKeysToDummy(Keys.ESCAPE.toString(), 0),
            Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 2000,
                    checking = Checking.isThereSuchTexxt(subModuleTitle))
    ), Repair.refreshSiteAndStartOver())

}

/**
 * slect last szkolenie and open it for futher modifications
 * */
fun selectLastSzkolenie(subModuleTitle: String) : SubSection {
    return     SubSection(listOf(

            Step.click(".action-menu-group > div:nth-child(1) > a:nth-child(1)", checking = Checking.dummy()),
     //       Step.calculateTabsAndPress("Szkolenie",{it * 4 + 1}),
//
            Step.specialFunct({ driver ->
//                val text ="Szkolenie"
//                val szkolenNum =    MainFunction.driver.pageSource.filter{ it in text}
//                        .groupingBy { it }
//                        .eachCount().size
//
                val page = driver.findElements(By.xpath("//*"))
                val szkolenNum = page.filter { it.text == "Szkolenie" }.size// will get information how many Szkolenie Submodules were already added
                driver.findElement(By.cssSelector("div.btn-group:nth-child(4) > button:nth-child(1)"))
                        .sendKeys(Keys.ESCAPE.toString() + genereteStringWithNumberOfTabs(szkolenNum * 4 + 1) + Keys.ENTER.toString())
            }),
            Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000,
                    checking = Checking.isThereSuchTexxt(subModuleTitle))


    ), Repair.refreshSiteAndStartOver())

}


/**
 * slect given  szkolenie and open it for futher modifications
 * */
fun selectNthSzkolenie(subModuleTitle: String, whichSzkolenie : Int) : SubSection {
    return     SubSection(listOf(
            Step.specialFunct({ driver -> Thread.sleep(3000) }),

            Step.click(".action-menu-group > div:nth-child(1) > a:nth-child(1)", checking = Checking.dummy()),
            Step.specialFunct({ driver -> Thread.sleep(3000) }),

           Step.navigation(perform = Perform.refreshWebsite()),
       //     Step.calculateTabsAndPress("Szkolenie",{if(it==0) {it * 4 + 1 } else{(whichSzkolenie-1) * 4 + 5 }}),
//
            Step.specialFunct({ driver ->
                val page = driver.findElements(By.xpath("//*"))

                val szkolenNum = page.filter { it.text == "Szkolenie" }.size// will get information how many Szkolenie Submodules were already added

         val tabsNumToSend =  if(szkolenNum==0) {szkolenNum * 4 + 1 } else{(whichSzkolenie-1) * 4 + 5 }
                driver.findElement(By.cssSelector("div.btn-group:nth-child(4) > button:nth-child(1)"))
                        .sendKeys(Keys.ESCAPE.toString() + genereteStringWithNumberOfTabs(tabsNumToSend) + Keys.ENTER.toString())



            }),
            Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 5000,
                    checking = Checking.isThereSuchTexxt(subModuleTitle))

    ), Repair.refreshSiteAndStartOver())

}


/**
 * create new Topic in szkolenie
 * numberOfNeededTabsToGetToNewTopic means how many tabs we need to go through to pass to next topic - and in this case remove it
 * */
fun createNewTopicInSzkolenie(tematTitle: String, numberOfTabsToPassATopic: Int, date: LocalDate) : SubSection {
    val titlee = "$tematTitle topic"
    return     SubSection(listOf(
            Step.specialFunct({ driver ->
                println("in special funct of createNewTopicInSzkolenie $tematTitle topic    ${MainFunction.driver.pageSource.replace(titlee, "XX ${titlee} XX").split("$tematTitle topic") .size }")
                var isCreatedNow = false
               if(!isThereSuchText(titlee)){
                   println("!MainFunction.driver.pageSource.contains(\"$tematTitle topic\") ${!MainFunction.driver.pageSource.contains("$tematTitle topic")}" )
                   isCreatedNow = true
                   listOf(Step.sendKeys("#SzkolenieTematNazwa", "$tematTitle topic", timeToWait = 2000),
                    Step.specialFunct({ driver -> Thread.sleep(3000) }),
               //     Step.sendKeysToDummy(formatDateToAsseco(date), 2),
                    Step.sendKeysToDummy("", 2),
                    Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 1)).forEach { MainFunction.processStep(it) }
                                   }
                if(isCreatedNow || (MainFunction.driver.pageSource.filter {it.isLetterOrDigit()  }.split(titlee.filter { it.isLetterOrDigit()}) .size)>=3){


                    listOf(
                            Step.specialFunct({ driver -> Thread.sleep(8000) }),
                            Step.sendKeys("div.btn-group:nth-child(4) > button:nth-child(1)", Keys.ESCAPE.toString(), checking = Checking.dummy()),// selecting zawartosc szkolenia
                            Step.calculateTabsAndPress("txtEdycjaSzkolenieTematNazwa" ,{((3 + ((it - 2) * numberOfTabsToPassATopic)))}),
                            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),

                            Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 1000,
                                    checking = Checking.isThereSuchTexxt(tematTitle)),
                            Step.specialFunct({ driver -> Thread.sleep(3000) })).forEach { MainFunction.processStep(it) }


                }

            }),

//            Step.specialFunct({ driver -> Thread.sleep(3000) }),
//            Step.navigation(perform=Perform.refreshWebsite()),
//            Step.specialFunct({ driver -> Thread.sleep(3000) }),
//
//            Step.sendKeys("div.btn-group:nth-child(4) > button:nth-child(1)", Keys.ESCAPE.toString(), checking = Checking.dummy()),// selecting zawartosc szkolenia
//
//    Step.calculateTabsAndPress("txtEdycjaSzkolenieTematNazwa" ,{((3 + ((it - 2) * numberOfTabsToPassATopic)))}),
//
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 0),
//
//            Step.navigation(perform = Perform.refreshWebsite(), timeToWait = 1000,
//                    checking = Checking.isThereSuchTexxt(tematTitle)),
//                    Step.specialFunct({ driver -> Thread.sleep(3000) }),

            ), Repair.refreshSiteAndStartOver(), preCheck = PreCheck.resetPreCheck())

}

/**
 * creates interactive  video meeting in subject specyfing also how long it will be active
 * */
fun createVideoConference(subModuleTitle: String, dates: startAndEndDateTime) : SubSection {
    // publishing it 20 minutes after the main lesson
    //dates.startDate= dates.startDate.toInstant(TimeZone.UTC).plus(20, DateTimeUnit.MINUTE, TimeZone.currentSystemDefault()).toLocalDateTime(TimeZone.currentSystemDefault())
val titlee = subModuleTitle + " meeting"
    println(titlee)
    println (formatDateToAsseco(dates.startDate.date))
    println (formatHourString(dates.startDate.hour,dates.startDate.minute))
    println (formatDateToAsseco(dates.endDate.date))
    println (formatHourString(dates.endDate.hour,dates.endDate.minute))

    return     SubSection(listOf(
//            Step.click(selectingString = "div.btn-group:nth-child(2) > button:nth-child(1)", checking = Checking.dummy()),
//            Step.specialFunct({ driver -> Thread.sleep(3000) }),
//            Step.sendKeysToRobot(listOf(), 15),
//            Step.specialFunct({ driver -> Thread.sleep(10000) }),
//
//            Step.specialFunct({ driver -> Thread.sleep(3000) }),
//
//            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(titlee)!!.toList(), 0),
//
//            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatDateToAsseco(dates.startDate.date))!!.toList(), 2),
//            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatHourString(dates.startDate.hour,dates.startDate.minute))!!.toList(), 1)
//            , Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatDateToAsseco(dates.endDate.date))!!.toList(), 1),
//            Step.sendKeysToRobotWithKeyboardUtil(Keyboard().strToInts(formatHourString(dates.endDate.hour,dates.endDate.minute))!!.toList(), 1),
//
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE), 3),
//
//            Step.sendKeysToRobot(listOf(KeyEvent.VK_SPACE,KeyEvent.VK_DOWN,KeyEvent.VK_DOWN), 1),
//            Step.specialFunct({ driver -> Thread.sleep(90000) }),
//
//            //Step.sendKeysToRobot(listOf(KeyEvent.VK_ENTER), 4),
//
//            Step.specialFunct({ driver -> Thread.sleep(3000) }),
//            Step.navigation(perform = Perform.refreshWebsite()),
//            Step.specialFunct({ driver -> Thread.sleep(5000) }),


            ), Repair.refreshSiteAndStartOver(), preCheck= PreCheck.isThereSuchText(titlee))
}

fun formatHourString (hour : Int, minute :Int): String {
  return   (if(hour<10){ "0${hour}"}else{hour.toString() }) +
            ":${(if(minute<10){ "0${minute}"}else{minute.toString() })}"
}



fun formatDateToAsseco(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

   return  formatter.format(date.toJavaLocalDate())
}



data class TaskInfo(val taskTitle: String, val taskDescription: String, val taskInstructions: String, val taskStartDate: LocalDate, val durationDays: Int)








