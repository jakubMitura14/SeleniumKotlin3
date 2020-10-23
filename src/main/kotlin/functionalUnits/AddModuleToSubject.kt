package functionalUnits

import Google.GDrive.DownloadPres
import kotlinx.datetime.*
import mainFunction.*
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import utils.Keyboard
import utils.genereteStringWithNumberOfTabs
import java.awt.event.KeyEvent

///**
// * this part will start editing the subject to add appropriate interactive modules like tests ... also in separete szkolenie there will be lesson
// * the interactive part will have
// * */
//class CreateInteractiveLesson(val title: String, val subModulesToAdd: List<subModule>) : Section(
//        {
//
//            ///////////////////////// some strings that will be displayed
//            val introductionDecription= "intro desc"
//            val openQText ="Click"
//            // You should be already familiar with topic that we will go through today as the presentation was already posted you will answer open question in google form which link will be provided
//            //remember to enter correct student code  - it is on your studen id card(in case you do not have one think about any long number (it needs to be unique among all students so think of somethhing more robust that 123 ... :)
//            // ) but you will need to remember this number and use it  consistently till the end of semester) than there will be some video and some written explanations to your question,
//            // it is iportant that you will first go through the questions and only then look at video, it is also very important that you will phrase it in simple terms in your own words
//            // obviously do not copy it from internet, later there will be close test also remember to get the student code number consistent ! at the end of the interactive module you have a folder
//            // I want Your group that is comming to this subject to divide yourself into 3 subgroups and each one should upload to the folder some schematic that will graphically
//            // summarise material for this presentation - also upload the spreadsheet telling who had contributed to the graphics, graphics should be in powerpoint format, it should be symbolic  so on first slide schematic
//            // on second slide description to this schematic and on the last one who had contributed what of course those that would not contribute may score negative points
//
//            // " the link fill the form and get back to this website , remember answer should reflect what you remember and use simple terms do not copy it from internet " TODO(unhash)
//            val videoLinkDesc = "video" //TODO(add more text)
//            //     " the link fill the form and get back to this website , remember answer should reflect what you remember and use simple terms do not copy it from internet "
//            val videoDescArticleDesc = "so"
//            //        " summarizing your answer may look for example like that (it may be longer or shorter and may be phrased diffrently there is no one good answer in open questions :)" TODO(unhash)
//            val closedTestDescr = "clo"
//            //        "sed questions this will be simmilar to final test"// TODO(unhash)
//
//            listOf<SubSection>(getIntoSubject(title), *subModulesToAdd.map { subModule ->
//                /******************************    here we are adding data depending at each submodule       **************************************************/
//
//                // data needed to instantiate szkolenie and prepare to add content there
//                val introToSzkolenie= listOf(
//                        getIntoSubject(title),
//                        SubSection(listOf(),preCheck = PreCheck.isThereSuchText(textToSeek = subModule.subModuleTitle )),
//                        createSzkolenie(subModule.subModuleTitle),selectLastSzkolenie(subModule.subModuleTitle), createNewTopicInSzkolenie(subModule.subModuleTitle)
//                )
//
//                //adding needed content not related to open questions
//                val mainContent = listOf(
//                        addLekcja(" lesson "+subModule.subModuleTitle,subModule.subModuleTitle, subModule.presentationFileId)
//
//                )
//                val introToInterActiveSzkolenie = listOf(
//                        getIntoSubject(title),
//                        SubSection(listOf(),preCheck = PreCheck.isThereSuchText(textToSeek = subModule.subModuleTitle+"InterActive" )),
//
//                        createSzkolenie(subModule.subModuleTitle+"InterActive", subModule.startAndEndDateTime.copy())
//                        ,selectLastSzkolenie(subModule.subModuleTitle+"InterActive"),
//                        createNewTopicInSzkolenie(subModule.subModuleTitle+"InterActive")
//                       ,addArticle(subModule.subModuleTitle+ "  introduction ", introductionDecription)
//                )
//
//
//
//                // data related to open questions and descriptions to them
//                val openQAndTestsSubModules =subModule.listOfopenQAndVideo.flatMap {openQAndVid-> listOf(
//                        addLink(          openQText       , openQAndVid.openQFormLink),
//                        addLink(       videoLinkDesc           , openQAndVid.video),
//                        addArticle(         videoDescArticleDesc                 ,openQAndVid.videoDescription )
//                ) }
//
//                //adding final content
//                val finalContent = listOf(
//                        addLink(   closedTestDescr
//                                      , subModule.linkToClosedTest)
//                ,addFolder (subModule.subModuleTitle+" folder for image task " )
//                                        )
//                // adding video conference
//                val addVideoConference = listOf(
//                        getIntoSubject(title),
//                        SubSection(listOf(),preCheck = PreCheck.isThereSuchText(textToSeek = subModule.subModuleTitle+" meeting" )),
//
//                        createVideoConference(subModule.subModuleTitle, subModule.startAndEndDateTime.copy()),
//                                SubSection(listOf(),preCheck = PreCheck.resetPreCheck())
//                )
//
//
//     listOf(introToSzkolenie,mainContent,
//             introToInterActiveSzkolenie,openQAndTestsSubModules,finalContent,
//             addVideoConference).flatten()
//
//
//
//            }.flatten().toTypedArray())
//        }.invoke())

/**
 * summarizing all data needed for creatingSubmodule
 * */
data class subModule(val subModuleTitle: String, val presentationFileId: String, val listOfopenQAndVideo: List<openQAndVideo>, var linkToClosedTest : String, val startAndEndDateTime : startAndEndDateTime){
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

/**
 * start and end date
 * */
data class startAndEndDateTime (var startDate : LocalDateTime, var endDate : LocalDateTime)

///**
// * information needed to conduct check weather lekcja or forum or artykul to szkolenie
// * */
//data class szkolenieCheckInfo(val typeOfAddedSection : String, )
