package com.example.common

import appScript.AppsScriptConnection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.datetime.*
import java.math.BigDecimal


val beginningDatOfSemester by lazy { val currentMoment = Clock.System.now()
    val currDate = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
    var yearNumber =currDate.year
    val monthNumber = currDate.monthNumber
   var  isSummer = true
    if(monthNumber<2){yearNumber= yearNumber-1  ;isSummer=false }
    if (monthNumber>8 ){ isSummer=false}

    if(isSummer) {  LocalDate(yearNumber,3,1)}
    else{   LocalDate(yearNumber,10,1)}

}

class AppScriptConnectedFunctions ( val basicManger: AppScriptBasicManager) {


/** basic set up - creating appropriate spreasheets , sheets, managing metadata, adding titles row etc
 * this is part A becouse after this we need to manually set the classrooms - set their names subjects etc
 * */
    suspend fun basicSetUpFunctionsPartA() {
   basicManger.setUpFolders().apply {
       getmainFolders()
       setMainFoldersInSemesterFolderTOP()
   }
  basicManger.setUpDataSheets().apply {
      createBasicSheetsTop()
      prepareSemesterSheetTOP()
      setDataForSemSheetMetaDataTop()
      appendSubjectsAndSubsubjectsToMetaDAtOptionsTOP ()
      setUpNoSubjectColumnsInMetaDatOptionsTOP()
      preapareDropDownsForsemsheetMetaTabTOP()
  }


    }

/*
* after step 1 we need to manually set the basic data about classrooms and faculties
* */
  suspend fun setUpBasicPart2 (){
    basicManger.setUpDataSheets().apply {
    addBasicDataToSemsheetTOP()
    }
basicManger.coordinateCreatingAndModdingAssingmnets().apply {
    prepareFormListTop()
    prepareSubmissionIdsSprTop()
    prepareMarksSummarySpreadsheetTabsAndTitlesTop()
    executePrepareToExecuteBasicAssignments()
}
    basicManger.basicStudentDataManag().apply {
        prepareBasicStudentDataTab()
        addBasicFilesForEachStudentTOP()
        addAdditionalFunctionToBasicDatTabTOP()
        connectBasicDataFormStudentSpreadshettAndSemSheetTOP ()
                  }

 basicManger.coordinateCalendar().apply {
     prepareMeetingDatesSpreadsheetTabsAndTitlesTOP ()
 }
    basicManger.AssesmentTasks().apply {
        prepareSmallAssesmentTaskTablesTOP()
        prepareTabsAndTitlesRowFormMainAssesmentsTasksSprTOP()
        addCheckBoxesToSmallAssesmentTaskSprTOP()
        getDataFromSmallassTaskToBigSprTOP()
        getDataBackFromBigToSmallTOP()
    }

}

 /** we need now to accept invitations to classrooms and add data to classroom Calendar */
 suspend fun setUpBasicPart3 (){
     basicManger.coordinateCalendar().apply {
         setDatesOfFinalMeetingsToMetaDat()
         appendDatesOfMeetingsTOP()
         prepareMeetingDatesSpreadsheetTabsAndTitlesTOP()
         sortDatesInCorrectOrderTOP()
         settingWhichTestIsFirstLastETCTOP()
     }
     basicManger.createNewForms().apply {
         createFormForGivenTestMeetingTOP()
     }

 }





    /**
     * on the basis of meetings saved in google calendar it creates test in a form of google form  appends submission ids to new forms
     * and add docs to submission and e mail to appropriate place in submission ids spreadsheet
     * */
    suspend fun  prepareAllTests (){
//       var coordinateCalendar = basicManger.coordinateCalendar()
//        coordinateCalendar.apply {
//            appendDatesOfMeetingsTOP()
//            sortDatesInCorrectOrderTOP()
//            settingWhichTestIsFirstLastETCTOP()
//        }
//basicManger.createNewForms().apply {
//    createFormForGivenTestMeetingTOP()
//}
//
//        var coordinateCourseWork = basicManger.coordinateCreatingAndModdingAssingmnets()
//        coordinateCourseWork.apply {
//            getFormsIdsToSheetFromMeetingDatesTOP()
//            addDataAboutPositionAndCourseWorkInSummaryMarksTableTop()
//            appendSubmissionIdsTop()
//            //addMailAny7hgt48edwss?""""""""""""""""""dLinkToSubmissionIdsTOP() TODO(unhash)
//
//        }
        var coordinateFormsAndPaper = basicManger.coordinateFormsAndPaper()
        coordinateFormsAndPaper.apply {
        //   pushCourseIdsAndSubmissionsForBarcodesETCTOP() //TODO(unhash)
   //       delay(10000)// we need a delay in order to ensure that appended data will be visible from android side
         basicManger.createNeededCodes()// now we can get create codes TODO(unhash)
    //         createQuestionSheetsTOP()
        }
    }
    /**
     * refreshes prepareAllTests
     * */
    suspend fun  refreshprepareAllTests (){
        var coordinateCalendar = basicManger.coordinateCalendar()

        coordinateCalendar.apply {
            refrappendDatesOfMeetings()
            refrsortDatesInCorrectOrderTOP()
            refrsettingWhichTestIsFirstLastETCTOP()
        }
        var coordinateCourseWork = basicManger.coordinateCreatingAndModdingAssingmnets()
        coordinateCourseWork.apply {
            refrappendSubmissionIdsTop()
            refraddDataAboutPositionInSummaryMarksTableeTop()
            refrgetFormsIdsToSheetFromMeetingDates()

        }
        var coordinateFormsAndPaper = basicManger.coordinateFormsAndPaper()
        coordinateFormsAndPaper.apply {
              refrcreateQuestionSheetsTOP()
            refrpushCourseIdsAndSubmissionsForBarcodesETCTOP()
        }
        basicManger.createNewForms().apply {
            refrcreateFormForGivenTestMeetingTOP()
        }
    }

    /** print queston sheets for tests in nex days*/
suspend fun printQuestionSheets(){
    basicManger.createNewForms().apply {
        markIsFormReadyTOP()
    }
        basicManger.coordinateFormsAndPaper().apply {
            prepareFormsToPrintTOP()
        }

}
    /**refreshes function printQuestionSheets*/
    suspend fun refreshprintQuestionSheets(){
        basicManger.createNewForms().apply {
            refrmarkIsFormReadyTOP()
        }
        basicManger.coordinateFormsAndPaper().apply {
           refrprepareFormsToPrintTOP()
        }
    }


    /** managing all  functions needed for publishing the marks to appropriate courseworks apart from those that are invoked in ondataToBasicAssignmnets function*/
 suspend   fun onFullSetMarks () {

//GlobalScope.launch { onCreateAnswerSheets ()  }
       var coordinateCourseWork = basicManger.coordinateCreatingAndModdingAssingmnets()
     coordinateCourseWork.apply {
//    executeAddDataToOrganizational()
//       executeAddDataToNonOrganizational()
 //      executeaddFormsIfNeededToMaiIdsAssignment()
//       appendingDataFromBasicStudentDataForms()
//       addDataAboutPositionAndInSummaryMarksTableTop()
//       addMailAndLinkToSubmissionIdsTOP()
//      setMarksAndDocsTOP()
//         addAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryTOP()
   //      addBasicDataToSummaryMarksTOP()
         addDataAoutBasicAssesmentsScoresToSummaryTOP()
         setDataAboutTestMarksIntoSummaryMarksTOP()
         proessDataOfSmallTestsInSummaryMarksiTOP()
         setFlagsAndFinalMarksTOP()
         setScoresToBasicAssesmentLikeFlagTOP()
     }
        }
    /** refreshing all columns that needs to be refreshed in ordew to enable setting marks one more time*/
    suspend fun onFullRefreshMarks () {
        var coordinateCourseWork = basicManger.coordinateCreatingAndModdingAssingmnets()

//      coordinateCourseWork.refreshexecuteSetUpBasicAssignments()
//      coordinateCourseWork.refrseUpDataUfBesiAssignmentsToTableTOP()
//    coordinateCourseWork.refreshExecuteaddFormsIfNeededToMaiIdsAssignment()
//   coordinateCourseWork.refreshappendingDataFromBasicStudentDataForms()
//       coordinateCourseWork.refraddDataAboutPositionInSummaryMarksTableeTop()
//      coordinateCourseWork.refrappendingDataFromBasicStudentDataFormsTop()
//        coordinateCourseWork.refraddDataAboutPositionInSummaryMarksTableeTop()
//        coordinateCourseWork.refraddMailAndLinkToSubmissionIdsTOP()
//        coordinateCourseWork.refrsetMarksAndDocsTOP()
//        coordinateCourseWork.refrappendSubmissionIdsTop()
//        coordinateCourseWork.refrForaddAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryTOP()
  //      coordinateCourseWork.refrForaddBasicDataToSummaryMarksTOP()
        coordinateCourseWork.refraddDataAoutBasicAssesmentsScoresToSummaryTOP()
        coordinateCourseWork.refrsetDataAboutTestMarksIntoSummaryMarksTOP()
        coordinateCourseWork.refrproessDataOfSmallTestsInSummaryMarksiTOP()
        coordinateCourseWork.refrsetsetFlagsAndFinalMarksTOP()
        coordinateCourseWork.refrsetScoresToBasicAssesmentLikeFlagTOP()
    }

suspend fun onCreateAnswerSheets () {
    var coordinateFormsAndPaper = basicManger.coordinateFormsAndPaper()
 coordinateFormsAndPaper.apply {
//       refrpushCourseIdsAndSubmissionsForBarcodesETCTOP()
//       pushCourseIdsAndSubmissionsForBarcodesETCTOP()
 }
//delay(10000)// we need a delay in order to ensure that appended data will be visible from android side
  basicManger.createNeededCodes()// now we can get create codes
    coordinateFormsAndPaper.apply {
     //  refrcreateQuestionSheetsTOP()
    //    createQuestionSheetsTOP()
    }


}

 /** is responsible for uploading scanned files that are in form of pdf into  appropriate table in order to enable futher processing by app script */

    /** refreshing  ProcessScannedTests in order to enable reapating it from the beginingt */
    suspend  fun refrProcessScannedTests() {
    }

    suspend  fun manageTestBase() {
basicManger.manageTestBase().modifyQuestionBases()

    }


}