package org.example.SeleniumJavaFx

import Google.GSheetsManag.classroomDataAboutSubModulesPerDate
import Google.GSheetsManag.manageMeetingDatesSpr.getTabDataFromMeetingDates
import Google.GSheetsManag.manageSemsheet.getAllNonOrganizationalClassroomName
import Google.GSheetsManag.manageSemsheet.listOfRelevantClassroomData
import functionalUnits.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import mainFunction.Section
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * here we are combining data from google spreadsheets with formulas developed to get data into asseco edu portal
 * */
object combinedFunctionsWithSpreadsheets {

    //strings added for diffrent types of przedmioty
    val forAll = " for All"
    val forAlfa = " for alfa"
    val forBeta = "for beta"

    val listOfMeetingDatesDat : Deferred<List<classroomDataAboutSubModulesPerDate>> = GlobalScope.async {
  //listOf("Radiologic Anatomy sem1 Exercises plus Lect. Phy s2 Winter 2020 ")
  //listOf("Biophysics sem1 Exercises plus Lect. Phy s1 Winter 2020 ")
 // listOf("Anatomy sem1 Exercises plus Lect. Phy s2 Winter 2020 ")
//  listOf(
//          "Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020 "
//  )
   //       , "Biomechanics sem1 Exercises plus Lect. Phy s1 Winter 2020 ", "Biophysics sem1 Exercises plus Lect. Phy s1 Winter 2020 ",
//"Physiology sem1 Lectures Nur A Nur B NurC Winter 2020 ",
//listOf(       "Physiology of pain sem2 Exercises plus Lect. Phy s2 Winter 2020 ")
//  )
// listOf("Biomechanics sem1 Exercises plus Lect. Phy s1 Winter 2020 ")
//  listOf("Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020 ", "Biophysics sem1 Exercises plus Lect. Phy s1 Winter 2020 ")
 //listOf("Physiology sem1 Lectures Nur A Nur B NurC Winter 2020 ")
  //listOf("Genetics sem1 Lectures Nur A Nur B NurC Winter 2020 ")
 // listOf("Physiology sem1 Exercises NurC Winter 2020 ")
 // listOf("Physiology sem1 Exercises NurC Winter 2020 ")
// listOf("Physiology sem1 Exercises Nur B Winter 2020 ","Physiology sem1 Exercises Nur A Winter 2020 ")
// listOf("Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020 ")
// listOf("Anatomy sem2 Exercises plus Lect. SpS s2 Winter 2020 ")
//  listOf("Biophysics sem1 Exercises plus Lect. Phy s1 Winter 2020 ")                                                                                                                                                                                                                                                                                                                                                             listOf("Physiology of pain sem2 Exercises plus Lect. Phy s2 Winter 2020 ")
     getAllNonOrganizationalClassroomName().filterIndexed { index, s -> s!=null
             //&& index>5
             && s.trim()!="Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020" }
                 .filter { it!!.trim()!="course Name" }
                 .map { getTabDataFromMeetingDates(it) }
                 .filter { it!=null }
                 .map { it!! }
        // data from meeting dates from this classrooms  }
    }

    /**
     * adding all przedmioty according to datya from semsheet
     * */
    suspend fun addClassrooms(): List<Section> {
return        listOfRelevantClassroomData.await().filter {
            it?.classroomName!!.length > 6
            //     && it.classroomName.trim()=="Physiology sem1 Exercises Nur B Winter 2020"
                  // it.classroomName.trim()=="Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020"
                  // it.classroomName.trim()=="Genetics sem1 Lectures Nur A Nur B NurC Winter 2020"
                  // it.classroomName.trim()=="Anatomy sem2 Exercises plus Lect. SpS s2 Winter 2020"
//                    && it.classroomName.trim()!="Biophysics sem1 Exercises plus Lect. Phy s1 Winter 2020"
//                    && it.classroomName.trim()!="Biophysics sem1 Exercises plus Lect. Phy s1 Winter 2020"
//                    && it.classroomName.trim()!="Biomechanics sem1 Exercises plus Lect. Phy s1 Winter 2020"
//                    && it.classroomName.trim()!="Anatomy sem1 Exercises plus Lect. Phy s2 Winter 2020"
//                    && it.classroomName.trim()!="Physiology of pain sem2 Exercises plus Lect. Phy s2 Winter 2020"
//                    && it.classroomName.trim()!="Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020"
//                    && it.classroomName.trim()!="Biomechanics and Ergonomics sem1 Exercises plus Lect. Phy s2 Phy s3 Winter 2020"
//                    && it.classroomName.trim()!="Genetics sem1 Lectures Nur A Nur B NurC Winter 2020"
//                    && it.classroomName.trim()!="Physiology sem1 Lectures Nur A Nur B NurC Winter 2020"
//                    && it.classroomName.trim()!="Physiology sem1 Lectures Nur A Nur B NurC Winter 2020"
//                    && it.classroomName.trim()!="Physiology sem1 Exercises Nur A Winter 2020"
//                    && it.classroomName.trim()!="Physiology sem1 Exercises Nur B Winter 2020"
        }.flatMap { classroomDat ->
            classroomDat!!
           val res=  listOf(
                  //  dataToCreateClassrooms(classroomDat.classroomName.trim() + forAll, listOf(*classroomDat.assecoGroupAlfa.toTypedArray(), *classroomDat.assecoGroupBeta.toTypedArray())),
                   dataToCreateClassrooms(classroomDat.classroomName.trim() + forAlfa, classroomDat.assecoGroupAlfa), dataToCreateClassrooms(classroomDat.classroomName.trim() + forBeta, classroomDat.assecoGroupBeta)
            )

    res
        }.flatMap {
            listOf(
                   createSubject(it.classroomName, Date.from(Instant.now()), Date.from(Instant.now().plus(200, ChronoUnit.DAYS))),
                    createSubjectAndSetStudents(it.classroomName, it.listOfGroups)
            )

        }
    }

//
//             classroomDat ->
//            classroomDat!!
//            listOf(
//                    createSubject(classroomDat.classroomName.trim() + forAll, Date.from(Instant.now()), Date.from(Instant.now().plus(200, ChronoUnit.DAYS))),
//                    createSubject(classroomDat.classroomName.trim() + forAlfa, Date.from(Instant.now()), Date.from(Instant.now().plus(200, ChronoUnit.DAYS))),
//                    createSubject(classroomDat.classroomName.trim() + forBeta, Date.from(Instant.now()), Date.from(Instant.now().plus(200, ChronoUnit.DAYS))),
//                    createSubjectAndSetStudents(classroomDat.classroomName.trim() + forAll, listOf(*classroomDat.assecoGroupAlfa.toTypedArray(), *classroomDat.assecoGroupBeta.toTypedArray()))
//                    , createSubjectAndSetStudents(classroomDat.classroomName.trim() + forAlfa, classroomDat.assecoGroupAlfa), createSubjectAndSetStudents(classroomDat.classroomName.trim() + forBeta, classroomDat.assecoGroupBeta)
//            )
//        }
   // }
    /***
     * info to creating new classrooms
     * */
data class dataToCreateClassrooms (val classroomName: String,val  listOfGroups : List<String>)


    /**
     *  iterating over data from meeting Dates and  getting all needed data  to create passive szkolenie
     * */
    suspend fun createPassiveSzkolenies () =

                // creating section object that can be executed by selenium on the basis on the data from tables above
            listOfMeetingDatesDat.await().filter { it!=null }.flatMap {classroomData->
                var indexOfDate = 0
                    classroomData.mapOfDatesAndSubM

                            .map { (Date, subModuleListDat)->
                        indexOfDate++
                        CreatePassiveClassromForAll(classroomTitle = classroomData.classroomName.trim()+ forAll,
                                meetingDateTime = Date,
                                subModulesToAdd = subModuleListDat.map{subModulesData ->
                                         subMDataFoPassive(subModulesData.subModuleName,subModulesData.subPresentationId,subModulesData.drawingAlfaLink.str,subModulesData.drawingBetaLink.str) },
                                groupsAlfa = listOfRelevantClassroomData.await().find {
                                   // println("it!!.classroomName.trim()  ${it!!.classroomName.trim()}  classroomData.classroomName.trim()  ${classroomData.classroomName.trim()}")
                                    it!!.classroomName.trim()== classroomData.classroomName.trim()}?.assecoGroupAlfa?:listOf(""),
                                groupsBeta =  listOfRelevantClassroomData.await().find { it!!.classroomName.trim()== classroomData.classroomName.trim()}?.assecoGroupAlfa?:listOf("")
                        ,indexOfDate= indexOfDate)

                    }

                }

  /**
   * adds to for All classrooms virtual meetings
   * */
  suspend fun createInteractiveMeetings () =
          listOfMeetingDatesDat.await().map {classroomData->
              CreateVirtualMeetingsInForAllClassrooms(classroomTitle =  classroomData.classroomName.trim()+ forAll,
              meeteingsDataList = classroomData
                      .mapOfDatesAndSubM
                      .filter {(Date, subModuleListDat)->  Date > Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date  }
                      .map { (date, submDataList)->
                val localDateTimes = submDataList.map {

                               val splittedHour = it.startHour.split(":").map { it.trim().toInt() }
                            kotlinx.datetime.LocalDateTime(date.year,date.monthNumber,date.dayOfMonth, splittedHour[0],splittedHour[1]) }.distinct().sorted()
              if(localDateTimes.isNotEmpty()){
                 infoFrorInterActiveMeetingCreation(
                            subModulesTitles = submDataList.map { it.subModuleName },
                            startTime = localDateTimes.first(),
                          endTime = localDateTimes.last().toJavaLocalDateTime().plusMinutes(45).toKotlinLocalDateTime(),
                          linkOfSummarizingSpreadsheet = submDataList.map { it.spreadsheetSummarizing }.filter { it!="" }.getOrElse(0,{""})
                  )} else{ null}

              }.filter { it!=null }.map{it!!})
         }
/**
 * creates interactive part of lessons where students are required to perform some tasks
 *
 * */
    suspend fun createInteractiveTasksLessons () =
    listOfMeetingDatesDat.await().flatMap {classroomData->
        listOf(CreateInteractiveLesson(listOfMeetingDatesDat.await(),false  )
        ,CreateInteractiveLesson(listOfMeetingDatesDat.await(),true))
    }

//Physiology sem1 Exercises plus Lect. Phy s1 SpS s2 Winter 2020 for alfa

}



