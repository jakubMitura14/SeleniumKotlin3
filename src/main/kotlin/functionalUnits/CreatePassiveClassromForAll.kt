package functionalUnits

import kotlinx.datetime.*
import mainFunction.*


/**
 * adding to for all classroom all the data needed  for students to learn - all of the presentations
 * plus links  to google drawing so they will be able to complete them in time of choosing
 * also to improve cooperation there will be added some forums for each mindmap to complete
 * generally we wil do seperate szkolenie for each meeting day, so here we will have all submodules that will be done in a given day
 * @param groupsAlfa - list of groups that are represented in alfa
 * @param groupsBeta - list of groups that are represented in beta
 * indexOfDate - which szkolenie in a series it is
 * **/


class CreatePassiveClassromForAll(
        val classroomTitle: String,
        val meetingDateTime: LocalDate,
        val subModulesToAdd: List<subMDataFoPassive>
        , val groupsAlfa: List<String>
        , val groupsBeta: List<String>,val  indexOfDate: Int) : Section(
        {
 val joinedgroupsAlfa = groupsAlfa.reduce { acc, s -> "${acc},${s}" }
val joinedgroupsBeta = groupsBeta.reduce { acc, s -> "${acc},${s}" }
            // this szkolenie generally is about the single date  and a tit need to reflect thi
val szkolenieTitle = "Before ${meetingDateTime.year}:${meetingDateTime.monthNumber}:${meetingDateTime.dayOfMonth} learn and do mindmaps of topics inside"
val forumInstructions = "it is a place for collaboration to complete mindmaps there needs to be single mindmap per group some additional examples are in https://www.youtube.com/watch?v=jfyZdSfCKSs and in https://youtu.be/LTvAaKdq7e0"
//"Click the link fill the form and get back to this website , remember answer should reflect what you remember and use simple terms do not copy it from internet "
val numberOfTabsToPassATopic = 54 // number of tabs needed to start adding new elements to new topic
            val introToSzkolenie= listOf(
                    getIntoSubject(classroomTitle),
                   SubSection(listOf(),preCheck = PreCheck.isThereSuchText(textToSeek = szkolenieTitle)),
                    createSzkolenie(szkolenieTitle)
                , SubSection(listOf(),preCheck = PreCheck.resetPreCheck())
                    ,selectNthSzkolenie(szkolenieTitle,indexOfDate)
            )

            listOf<SubSection>(*introToSzkolenie.toTypedArray(), *subModulesToAdd.map { subModule ->
                val topicTitle = "${subModule.subModuleTitle} topic"
                listOf(
                /******************************    here we are adding data depending at each submodule       **************************************************/
                //creating new topic
                createNewTopicInSzkolenie(subModule.subModuleTitle,numberOfTabsToPassATopic,meetingDateTime+DatePeriod(0,0,1)),
                 SubSection(listOf(),preCheck = PreCheck.resetPreCheck()),
                // data needed to instantiate szkolenie and prepare to add content there

               addLekcja(" lesson "+subModule.subModuleTitle,subModule.subModuleTitle, subModule.subModulePresentationId,numberOfTabsToPassATopic),
                addLink("link to pres ${subModule.subModuleTitle} in case what above will not work", "https://docs.google.com/presentation/d/${subModule.subModulePresentationId}",numberOfTabsToPassATopic ),
                // links and forums to complete mindmaps in ALFA
                addLink(" link to drawing for ALFA- groups : "+ joinedgroupsAlfa+ " "+ subModule.subModuleTitle, subModule.linkForDrawingAlfa,numberOfTabsToPassATopic),
                addForum("for ALFA mindmap in ${subModule.subModuleTitle}",forumInstructions,numberOfTabsToPassATopic)
                // links and forums to complete mindmaps in BETA
                ,addLink(" link to drawing for BETA- groups : "+ joinedgroupsBeta+ " "+ subModule.subModuleTitle, subModule.linkForDrawingBeta,numberOfTabsToPassATopic),
                addForum("for BETA mindmap in ${subModule.subModuleTitle}",forumInstructions,numberOfTabsToPassATopic)
            )
            }.flatten().toTypedArray(), SubSection(listOf(Step.navigation(perform = Perform.navigateBack())), preCheck = PreCheck.resetPreCheck()))

        }.invoke())

/**
 * describing data about submodule needed for passive classroom
 * */
data class subMDataFoPassive(val subModuleTitle : String, val  subModulePresentationId : String,var linkForDrawingAlfa : String, var linkForDrawingBeta : String   ){
    init {
        linkForDrawingAlfa=  linkForDrawingAlfa.removeSuffix("/edit?usp=drivesdk")
        linkForDrawingBeta=  linkForDrawingBeta.removeSuffix("/edit?usp=drivesdk")
    }

}