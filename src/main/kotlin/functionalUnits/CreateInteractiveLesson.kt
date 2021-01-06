package functionalUnits

import Google.GSheetsManag.SubModulesData
import Google.GSheetsManag.classroomDataAboutSubModulesPerDate
import Google.GSheetsManag.openQDat
import Google.GSheetsManag.sthWithNumb
import kotlinx.datetime.*
import mainFunction.Section
import mainFunction.SubSection
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets.forAlfa
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets.forBeta
import java.util.*

/**
 * creating interactive lesson composed of tasks that may be later graded, It will consist of tests with open and closed questions, videos with some short question and video comments
 * isAlfa is true when we are in for alfa classroom and false otherwise
 * */

val standardDurationOfTasks = 200


class CreateInteractiveLesson(
        val listOfClassroomData : List<classroomDataAboutSubModulesPerDate>, val isAlfa : Boolean) : Section(
        {
            //map enabling setting proper number to the task


            listOf<SubSection>(  *listOfClassroomData.flatMapIndexed { indexOfClassroom: Int, classroomDat ->
                val mapOfOrder = createMapOfOrder(classroomDat)

                val classroomName = if(isAlfa){classroomDat.classroomName.trim() +forAlfa} else{classroomDat.classroomName.trim() +forBeta}
                var indexOfMeeting = -1

                listOf<SubSection>(getIntoSubject(classroomName)) +
                classroomDat.mapOfDatesAndSubM.flatMap { (date, subModulesList)->
                    indexOfMeeting+=1
                    val taskDate = date.toJavaLocalDate().minusDays(3).toKotlinLocalDate()

                    //filtering only data that is ready
                    subModulesList
                            .filter { listOf(it.WhyImportantFormLinkBeta,it.WhyImportantQFormLinkAlfa,it.drawingAlfaLink, it.drawingBetaLink).all { it.str!="" }}

                    // adding tasks
                            .flatMapIndexed {indexOfSubModule : Int,subMDat: SubModulesData ->
                                val indexingStr = "(${indexOfMeeting})<${subMDat}>"
                        val subModuleName = subMDat.subModuleName
                        val amountOfOpen =  (subMDat.listOfOpenQAlfaData.size*3)
                       listOf(
                                // first we supply  tasks related to open questions
                       *(if(isAlfa) {
                          subMDat.listOfOpenQAlfaData.flatMapIndexed { indexOfOpen: Int, openQDat: openQDat -> getTasksFromOpenQDat(openQDat, taskDate, subModuleName, indexOfOpen,indexingStr,mapOfOrder) }.toTypedArray()
                      }else {
                          subMDat.listOfOpenBetaData.flatMapIndexed { indexOfOpen: Int, openQDat: openQDat -> getTasksFromOpenQDat(openQDat, taskDate, subModuleName, indexOfOpen, indexingStr, mapOfOrder) }.toTypedArray()
                      }),
                             //after those we add closed test
                                if(isAlfa) {    creteSingleTask(TaskInfo(
                                        taskTitle = "${mapOfOrder[subMDat.closedQFormLinkAlfa.hasshB]} closed test ${subModuleName}",
                                        taskDescription = "open the link remember to correctly write down your student id after completing click submit in the form and later submit this task (so you need to submit two times)",
                                        taskInstructions = subMDat.closedQFormLinkAlfa.str,
                                        taskStartDate = taskDate, // we give it 3 days earlier start
                                        durationDays = standardDurationOfTasks
                                ))}else{
                                    creteSingleTask(TaskInfo(
                                            taskTitle = "${mapOfOrder[subMDat.closedQFormLinkBeta.hasshB]} closed test ${subModuleName}",
                                            taskDescription = "open the link remember to correctly write down your student id after completing click submit in the form and later submit this task (so you need to submit two times)",
                                            taskInstructions = subMDat.closedQFormLinkBeta.str,
                                            taskStartDate = taskDate, // we give it 3 days earlier start
                                            durationDays = standardDurationOfTasks
                        ))},
                                //after those we add why important fom
                                if(isAlfa) {    creteSingleTask(TaskInfo(
                                        taskTitle = "${mapOfOrder[subMDat.WhyImportantQFormLinkAlfa.hasshB]}Why  ${subModuleName} is important",
                                        taskDescription = "open the link  and submit your answer in the form ",
                                        taskInstructions = subMDat.WhyImportantQFormLinkAlfa.str,
                                        taskStartDate = taskDate, // we give it 3 days earlier start
                                        durationDays = standardDurationOfTasks
                                ))}else{
                                    creteSingleTask(TaskInfo(
                                            taskTitle = "${mapOfOrder[subMDat.WhyImportantFormLinkBeta.hasshB]} Why  ${subModuleName} is important",
                                            taskDescription = "open the link  and submit your answer in the form ",
                                            taskInstructions = subMDat.WhyImportantFormLinkBeta.str,
                                            taskStartDate = taskDate, // we give it 3 days earlier start
                                            durationDays = standardDurationOfTasks
                                    ))},
                                //now we will ask student to say what he or she contributed to created mindmap
                                if(isAlfa) {    creteSingleTask(TaskInfo(
                                        taskTitle = "${mapOfOrder[subMDat.drawingAlfaLink.hasshB]} Contribution to  ${subModuleName} mindmap",
                                        taskDescription = "wwite down what had you contribued to the minmap which link is provided below, ig nothing type 0",
                                        taskInstructions = subMDat.drawingAlfaLink.str,
                                        taskStartDate = taskDate, // we give it 3 days earlier start
                                        durationDays = standardDurationOfTasks
                                ))}else{
                                    creteSingleTask(TaskInfo(
                                            taskTitle = "${mapOfOrder[subMDat.drawingBetaLink.hasshB]} Contribution to  ${subModuleName} mindmap",
                                            taskDescription = "wwite down what had you contribued to the minmap which link is provided below, ig nothing type 0",
                                            taskInstructions = subMDat.drawingBetaLink.str,
                                            taskStartDate = taskDate, // we give it 3 days earlier start
                                            durationDays = standardDurationOfTasks
                                    ))},

//                           addForumImidiatelyToClassroom(TaskInfo(
//                                taskTitle = "${mapOfOrder[subMDat.formLinkForFaq.hasshB]} ${subModuleName} FAQ",
//                                taskDescription = "in case of any questions about this subModule send them through ${subMDat.formLinkForFaq.str}",
//                                taskInstructions = "",
//                                taskStartDate = taskDate, // we give it 3 days earlier start
//                                durationDays = standardDurationOfTasks
//                        ))


                        )
                    }
                }

            }.toTypedArray())

        }.invoke())

/**
 * given open Q data it gives back objects that will be used for task creation
 * openQDat - data takes from appropriate spredsheet about open questions videos and video comments related to particular submodule
 * taskDate  - date of the begining of the task
 * subModuleName - name of submodule
 * indexOfOpen - which open question it is in this subModule
 * */
fun getTasksFromOpenQDat (openQDat: openQDat, taskDate: LocalDate, subModuleName: String, indexOfOpen: Int, indexingStr: String, mapOfOrder: Map<UUID, Int>): List<SubSection> {

            println("getTasksFromOpenQDat ${subModuleName }   ${openQDat}")
   return if(openQDat.openQFormLink.str!="" && openQDat.videoLinks.listOfStr.any { it!="" && it!="undefined" }){listOf(    creteSingleTask(TaskInfo(
                    //first we will create the task for the student to answer the open question 4  vid c
                    taskTitle = "${mapOfOrder[openQDat.openQFormLink.hasshB]} op Q ${subModuleName} ${indexOfOpen}",
                    taskDescription = "open the link remember to correctly write down your student id after completing click submit in the form and later submit this task (so you need to submit two times)",
                    taskInstructions = openQDat.openQFormLink.str,
                    taskStartDate = taskDate, // we give it 3 days earlier start
                    durationDays = standardDurationOfTasks
            )),
            creteSingleTask(TaskInfo(
                    //first we will create the task for the student to answer the open question
                    taskTitle = "${mapOfOrder[openQDat.videoLinks.hasshB]} video ${subModuleName} ${indexOfOpen}",
                    taskDescription = "open the video/ videos link and after watching it tell me 3 most important words from it (no more not less)",
                    taskInstructions = openQDat.videoLinks.listOfStr.reduce { acc, s ->"${acc}     AND      ${s}"  },
                    taskStartDate = taskDate, // we give it 3 days earlier start
                    durationDays = standardDurationOfTasks)),
            creteSingleTask(TaskInfo(
                    //first we will create the task for the student to answer the open question
                    taskTitle = "${mapOfOrder[openQDat.videoComment.hasshB]}  vid comm  ${subModuleName} ${indexOfOpen}",
                    taskDescription = "read the video comment written below and tell me 2 most important words from it (not more not less) that would be diffrent than thse used to describe video",
                    taskInstructions = openQDat.videoComment.str,
                    taskStartDate = taskDate, // we give it 3 days earlier start
                    durationDays = standardDurationOfTasks
            )),
    )} else{
       listOf()}
}

/**
 * given list of meetings dates data it creates map of hashcode vs integer number where integer number marks which questions it is
 * */
fun createMapOfOrder (classroomDat :classroomDataAboutSubModulesPerDate) =
        classroomDat.mapOfDatesAndSubM.flatMap { (date,liist)-> liist.flatMap {
        listOf<sthWithNumb>(*it.listOfOpenQAlfaData.flatMap { listOf(it.openQFormLink,it.videoLinks,it.videoComment) }.toTypedArray(),*it.listOfOpenBetaData.flatMap { listOf(it.openQFormLink,it.videoLinks,it.videoComment) }.toTypedArray()
                ,it.drawingBetaLink,it.drawingAlfaLink,it.closedQFormLinkBeta ,
                it.closedQFormLinkAlfa,it.closedQFormLinkBeta , it.WhyImportantQFormLinkAlfa,it.WhyImportantFormLinkBeta ,it.formLinkForFaq)
     } } .sortedBy {
      // println("iiiiiiiiiiiiii  ${it.integB }")
       it.integB }
           // we sorted it in correct order now we can create a map
           .mapIndexed {indexx,it -> it.hasshB to indexx }.toMap()



//        {
//            val subsection1 = SubSection
//
//            listOf<SubSection>()}.invoke() )
