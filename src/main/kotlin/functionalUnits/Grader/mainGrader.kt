package functionalUnits.Grader

import Google.GSheetsManag.classroomDataAboutSubModulesPerDate
import functionalUnits.getIntoSubject
import mainFunction.Section
import mainFunction.SubSection
import org.example.SeleniumJavaFx.MainFunction
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets
import org.example.SeleniumJavaFx.isThereSuchText

/**
 * it will get through interactive tasks  check the list of students weather all had completed those tasks
 * in case that some conditions will be met it will put appropriate grade into the task and to separate google spreadsheet
 * in case it will not be completed and should it will send information to student to complete it
 * generally first we need to get through the results of the tasks
 * later we need to download detailed results from results of the course and upload it to google drive
 *
 * */

class createSubjectAndSetStudents ( classroomName : String,val isAlfa: Boolean, val listOfStudentsData : List<StudentDat>) : Section(
        {
     val fullClassroomName = if(isAlfa){classroomName.trim() + combinedFunctionsWithSpreadsheets.forAlfa        } else{classroomName.trim() + combinedFunctionsWithSpreadsheets.forBeta }


            listOf<SubSection>(getIntoSubject(fullClassroomName),



            )}.invoke() )


/**
 * when we are on the webpage we  know that each task has a button wh a link
 * in this function we will extract all such links ready to be used so we will enter each link one by one and check
 * */
fun getListOfTaskLinks () =
 MainFunction.driver.pageSource.split("href=\"")
         .map { it.split("\"><i class=").getOrElse(0){""} }
         .filter { it!=""   }
         .filter { it.contains("ZadanieWyniki") }
         .map { "https://wssp.eduportal.pl${it}" }


/**
 * we need to identify in which task we are in  we will do it by getting the full data about this classroom
 * we generally care about links - links to the forms to fill mainly link to open questions  and closed tests in case of the online classes
 * and in case of the words given to videos and video comments , presence of them is enough generally to give 5
 * in case of closed test we will give mark on the basis of what was written in closed test in case of open question having answer is leading to obtaining 5
 * 
 * */
fun identifyInWhatTaskWeAreIn(classroomDat : classroomDataAboutSubModulesPerDate){

  }


/** gets links for open questons  * */
fun getAllBetaOpenTestsLinks (classroomDat : classroomDataAboutSubModulesPerDate, isAlfa: Boolean) = classroomDat.mapOfDatesAndSubM.flatMap {  it.value.flatMap {
    if(isAlfa) {it.listOfOpenQAlfaData.map { it.openQFormLink }}  else { it.listOfOpenBetaData.map { it.openQFormLink }}} }

/** gets links for open questons in  */
fun getAllAlfaClosedTestsLinks (classroomDat : classroomDataAboutSubModulesPerDate, isAlfa: Boolean) =    classroomDat.mapOfDatesAndSubM.flatMap {
    it.value.map { if(isAlfa){it.closedQFormLinkAlfa } else{ it.closedQFormLinkBeta } }}

