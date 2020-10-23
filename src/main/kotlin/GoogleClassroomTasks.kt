import Google.GSheetsManag.manageMeetingDatesSpr
import Google.GSheetsManag.manageSemsheet.getAllNonOrganizationalClassroomName
import Google.GSheetsManag.manageSemsheet.listOfRelevantClassroomData
import com.google.api.services.classroom.Classroom
import com.google.api.services.classroom.model.CourseWork
import com.google.api.services.classroom.model.Link
import com.google.api.services.classroom.model.Material
import functionalUnits.createMapOfOrder


object GoogleClassroomTasks {
    lateinit var classroomServ : Classroom

    /**
     * taking identical data that was used to add tasks to asseco eduportal  to create tasks in the google classroom in case asseco is slow or not functioning
     * */
    suspend fun createTasksOnTheBasisOfData (){
   val listOfMeetingDatesDat=      getAllNonOrganizationalClassroomName().filter { it!=null }
                .filter { it!!.trim()!="course Name" }
                .map { manageMeetingDatesSpr.getTabDataFromMeetingDates(it) }
                .filter { it!=null }
                .map { it!! }

        listOfMeetingDatesDat.map {
            val mapOfOrder = createMapOfOrder(it)
            val classroomName  = it.classroomName
            val classroomId =getCourseId(classroomName)
            val listOfCourseWorkTitles = kotlin.runCatching {
                classroomServ.Courses().CourseWork().list(classroomId).execute().courseWork.map { it.title }
            }.getOrElse { listOf() }
                println("ccccccccccccc"+classroomName)
it.mapOfDatesAndSubM.map {
    it.value.filter { listOf(it.WhyImportantFormLinkBeta,it.WhyImportantQFormLinkAlfa,it.drawingAlfaLink, it.drawingBetaLink).all { it.str!="" }}
    .map {subMDat->

        val subModuleName = subMDat.subModuleName
        subMDat.listOfOpenQAlfaData.mapIndexed { indexOfOpen, openQDat ->
            //open Q
            createAssignment (classroomId,  "${mapOfOrder[openQDat.openQFormLink.hasshB]} op Q ${subModuleName} ${indexOfOpen}"
                    , "open the link remember to correctly write down your student id after completing click submit in the form and later submit this task (so you need to submit two times)",
                     listOf(openQDat.openQFormLink.str) ,listOfCourseWorkTitles)
            //video
            createAssignment (classroomId, "${mapOfOrder[openQDat.videoLinks.hasshB]} video ${subModuleName} ${indexOfOpen}"
                    , "open the video/ videos link and after watching it tell me 3 most important words from it (no more not less)",
                    openQDat.videoLinks.listOfStr,
                    listOfCourseWorkTitles)

            //video comment
            createAssignment (classroomId, "${mapOfOrder[openQDat.videoComment.hasshB]}  vid comm  ${subModuleName} ${indexOfOpen}"
                    , "read the video comment written below and tell me 2 most important words from it (not more not less) that would be diffrent than thse used to describe video"
            +openQDat.videoComment.str, listOfCourseWorkTitles = listOfCourseWorkTitles
            )


        }
        //closed q
createAssignment(classroomId, "${mapOfOrder[subMDat.closedQFormLinkAlfa.hasshB]} closed test ${subModuleName}",
        "open the link remember to correctly write down your student id after completing click submit in the form and later submit this task (so you need to submit two times)",
        listOf(subMDat.closedQFormLinkAlfa.str),
        listOfCourseWorkTitles
)
//why important
        createAssignment(classroomId, "${mapOfOrder[subMDat.WhyImportantQFormLinkAlfa.hasshB]}Why  ${subModuleName} is important",
                "open the link  and submit your answer in the form ",
                listOf(subMDat.WhyImportantQFormLinkAlfa.str),
                listOfCourseWorkTitles)
        // contribution
          createAssignment(classroomId, "${mapOfOrder[subMDat.drawingAlfaLink.hasshB]} Contribution to  ${subModuleName} mindmap",

                  "write down what had you contribued to the minmap which link is provided below, ig nothing type 0",
                  listOf(subMDat.drawingAlfaLink.str, subMDat.drawingBetaLink.str),
                  listOfCourseWorkTitles
          )


    }
}
        }


    }

    suspend fun getCourseId (classroomName : String) =  listOfRelevantClassroomData.await().find { it!!.classroomName.trim()==classroomName.trim() }!!.classroomId


    /** creating assignment with given title aned link
     * to a classroom of given id
     * */
    fun createAssignment (courseId: String, title: String, description: String, links: List<String> = listOf(""), listOfCourseWorkTitles: List<String>) {
        println("ttttttttttitle " + title)
        if (!listOfCourseWorkTitles.contains(title)) {

            println("assignment added")
            try {
                classroomServ.Courses().CourseWork().create(courseId, CourseWork().apply {
                    setTitle(title)
                    setWorkType("ASSIGNMENT")
                    setState("PUBLISHED")
                    //      setDueDate(Date().apply { year = dueDate.year; month = dueDate.monthNumber;day = dueDate.dayOfMonth })
                    setDescription(description)
                    if (links.isNotEmpty() && links.first() != "") {
                        setMaterials(links.map {
                            Material()
                                    .apply { setLink(Link().apply { setUrl(it) }) }
                        })
                    }
                }).execute()
            } catch (ex: Exception) {
                classroomServ.Courses().CourseWork().create(courseId, CourseWork().apply {
                    setTitle(title)
                    setWorkType("ASSIGNMENT")
                    setState("PUBLISHED")
                    //      setDueDate(Date().apply { year = dueDate.year; month = dueDate.monthNumber;day = dueDate.dayOfMonth })
                    setDescription(description + "  ${links.reduce { acc, s -> "${acc} ${s}" }}")

                }).execute()

            }

        }
    }



}

