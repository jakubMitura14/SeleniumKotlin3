package Google.GSheetsManag

import appScript.AppsScriptConnection
import com.example.common.librariesIds
import com.example.mituratest5.GSheetsManag.SheetsManager
import com.example.mituratest5.GSheetsManag.getAllData
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

/**
 * will import and export needed data  to meeting dates spreadsheet enabling for example adding  appropriate presentations to asseco edu portal etc
 * */
object manageMeetingDatesSpr {
//    private val tempDir = createTempDir().absolutePath // use any readable/writable directory of your choice
//
//    private val cache = CacheBuilder.config(tempDir, StringDataConvertible()).build().let(::ExpirableCache)
val meetIngDatesId="1WxcssYS_TuoW1LFvZfsw8uZNephpcMsWu7uQDkOw4yY"



    /**gets data from meetingDates from sheet of given classroomName*/
    suspend fun getTabDataFromMeetingDates(classroomName: String)  :classroomDataAboutSubModulesPerDate?{

    val allData =getAllData(meetIngDatesId, classroomName).drop(1)

    val subModulesDateMap : SortedMap<LocalDate, List<SubModulesData>>  =  allData.filter { it.size>2 }.map { row->row[3] }.distinct() // deting all  non distinck dates
       .filter { dateStr -> dateStr!= "#N" && dateStr.isNotEmpty() && dateStr.split("/").none { it.isBlank() }  &&  dateStr.split("/").none {it== "#N" }}
         .mapIndexed { indexOfWhichDate, dateStr->
               println("loc ${dateStr}" )
    val splitted =dateStr.split("/").map { it.toInt() }
    val  parsedDate = LocalDate(splitted[2],splitted[1],splitted[0])
          //java.time.LocalDate.parse(dateStr.split(" ").subList(0, 4).joinToString(" "), DateTimeFormatter.ofPattern("EEE MMM dd yyyy"))
              parsedDate to  getSubModulesRelatedToThisDate(getIndexesOfThissMeeting(dateStr, ArrayList(allData)).toList(), ArrayList(allData), indexOfWhichDate).toList()}
                                .toMap().toSortedMap()
val res = classroomDataAboutSubModulesPerDate(classroomName, subModulesDateMap)
       println("rrrrrrrrrrr " + res.mapOfDatesAndSubM.map { it.value.map { listOf(it.subModuleName,it.drawingBetaLink, it.drawingAlfaLink) } })

      return   res

    }


    /**
    gets data related to all submodules relatesd to indices of this date
    @return listOfObjects with data about submodules
    subModuleName {String}
    drawingAlfaLink {String}
    drawingBetaLink {String}

    closedQFormLinkAlfa {String}
    closedQSpreadshetLinkAlfa {String}
    closedQFormLinkBeta {String}
    closedQSpreadshetLinkBeta {String}

    listOfOpenQAlfaData {openQDat[]} list of objects with data about given open questions
    listOfOpenBetaData {openQDat[]} list of objects with data about given open questions

    rowIndexInThisDate {Number} analyzing all of the rows related to this date we will mark which one it is
    indexISplitted {Number} will mark which subModule it is in given cell which in array after splitting
     */
    fun getSubModulesRelatedToThisDate(indices: List<Int>, allData: List<List<String>>, indexOfThisDate: Int): List<SubModulesData> {
       // first we retrive what important per date
         return  indices.map{ ind-> allData[ind]}
                .filter{row->row.size>30}
                .map{ row-> print(" in getSubModulesRelatedToThisDate " + splitHashes(row[9]))
                    linkedMapOf("subMNames" to splitHashes(row[9]),
                            "splittedOpQFormLinksAlfa" to splitHashes(row[12]),
                            "splittedOpQFormLinksBeta" to splitHashes(row[13]),
                            "splittedOpQSprLinksAlfa" to splitHashes(row[14]),
                            "splittedOpQSprLinksBeta" to splitHashes(row[15]),

                            "splittedVideos" to splitHashes(row[16]),
                            "splittedVideoComments" to splitHashes(row[17]),
                            "splittedClQFormLinksAlfa" to splitHashes(row[18]),
                            "splittedClQFormLinksBeta" to splitHashes(row[19]),
                            "splittedClQSprLinksAlfa" to splitHashes(row[20]),
                            "splittedClQSprLinksBeta" to splitHashes(row[21]),

                            "splittedWhyImporantFormLinksAlfa" to splitHashes(row[28]),
                            "splittedWhyImporantFormLinksBeta" to splitHashes(row[29]),
                            "splittedWhyImporantSprLinksAlfa" to splitHashes(row[30]),
                            "splittedWhyImporantSprLinksBeta" to splitHashes(row[31]),


                            "drawingAlfaLinks" to splitHashes(row[25]),
                            "drawingBetaLinks" to splitHashes(row[26]),

                            "subPresentationIds" to splitHashes(row[10]),
                            "subPresentationLinks" to splitHashes(row[11]),

                            "startHour" to listOf(row[6]),
                            "spreadsheetSummarizing" to listOf(row[27]),
                            "formLinkForFaq" to listOf(row[23]),
                            "spreadsheetIdForFaq" to listOf(row[24])
                    )   }
// now we need to group all relevant data  into objects mapped on the basis of subModuleNames
                 .flatMapIndexed { rowIndexInThisDate: Int, linkedHashMap: LinkedHashMap<String, List<String>> ->

                     linkedHashMap["subMNames"]!!.mapIndexed { indexISplitted, subMName ->
                         SubModulesData(
                                 subModuleName = subMName,
                                 drawingAlfaLink = strWithNumb(linkedHashMap["drawingAlfaLinks"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: ""),
                                 drawingBetaLink = strWithNumb(linkedHashMap["drawingBetaLinks"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: ""),

                                 closedQFormLinkAlfa = strWithNumb(linkedHashMap["splittedClQFormLinksAlfa"]?.elementAtOrElse(indexISplitted, { "" })
                                         ?: ""),
                                 closedQSpreadshetLinkAlfa = linkedHashMap["splittedClQSprLinksAlfa"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: "",
                                 closedQFormLinkBeta = strWithNumb(linkedHashMap["splittedClQFormLinksBeta"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: ""),
                                 closedQSpreadshetLinkBeta = linkedHashMap["splittedClQSprLinksBeta"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: "",

                                 WhyImportantQFormLinkAlfa = strWithNumb(linkedHashMap["splittedWhyImporantFormLinksAlfa"]?.elementAtOrElse(indexISplitted, { "" })
                                         ?: ""),
                                 WhyImportantSpreadshetLinkAlfa = linkedHashMap["splittedWhyImporantSprLinksAlfa"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: "",
                                 WhyImportantFormLinkBeta = strWithNumb(linkedHashMap["splittedWhyImporantFormLinksBeta"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: ""),
                                 WhyImportantSpreadshetLinkBeta = linkedHashMap["splittedWhyImporantSprLinksBeta"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: "",

                                 subPresentationId = linkedHashMap["subPresentationIds"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: "",
                                 subPresentationLink = linkedHashMap["subPresentationLinks"]?.elementAtOrElse(indexISplitted) { "" }
                                         ?: "",

                                 listOfOpenQAlfaData = combineDataAboutOpenQ(subMName, linkedHashMap?.getOrElse("splittedOpQFormLinksAlfa") { listOf("") }
                                         ?: listOf(""), linkedHashMap?.getOrElse("splittedOpQSprLinksAlfa") { listOf("") }
                                         ?: listOf(""),
                                         linkedHashMap?.getOrElse("splittedVideos") { listOf("") }
                                                 ?: listOf(""), linkedHashMap["splittedVideoComments"] ?: listOf("")),
                                 listOfOpenBetaData = combineDataAboutOpenQ(subMName, linkedHashMap?.getOrElse("splittedOpQFormLinksBeta") { listOf("") }
                                         ?: listOf(""),
                                         linkedHashMap?.getOrElse("splittedOpQSprLinksBeta") { listOf("") }
                                                 ?: listOf(""), linkedHashMap?.getOrElse("splittedVideos") { listOf("") }
                                         ?: listOf(""),
                                         linkedHashMap.getOrElse("splittedVideoComments") { listOf("") } ?: listOf("")),

                                 startHour = linkedHashMap["startHour"]?.elementAtOrElse(0) { "" } ?: "",
                                 spreadsheetSummarizing = linkedHashMap["spreadsheetSummarizing"]?.elementAtOrElse(0) { "" }
                                         ?: "",
                                 formLinkForFaq = strWithNumb(linkedHashMap["formLinkForFaq"]?.elementAtOrElse(0) { "" }
                                         ?: ""),
                                 spreadsheetIdForFaq = linkedHashMap["spreadsheetIdForFaq"]?.elementAtOrElse(0) { "" }
                                         ?: "",

                                 rowIndexInThisDate = rowIndexInThisDate,
                                 indexISplitted = indexISplitted,
                         ) } }
    //now we need to add appropriate indexes to the data related to interactive lessons so later we will be able to give correct numbers to tasks
                  .mapIndexed{ index, subModulesData ->

                     var innerIndex =indexOfThisDate*1000000+ index*1000
                      val newOpenListAlfa = ArrayList<openQDat>()
                      val newOpenListBeta = ArrayList<openQDat>()

                      subModulesData.listOfOpenQAlfaData.forEachIndexed { indexOfOpen, openDat->

                          newOpenListAlfa.add(openQDat(
                                  openQFormLink = strWithNumb(openDat.openQFormLink.str, innerIndex),
                                  openQSpreadsheetLink = strWithNumb(openDat.openQSpreadsheetLink.str, innerIndex + 1),
                                  videoLinks = listOfStrWithNumb(listOf(openDat.videoLinks.listOfStr).flatten(), innerIndex + 2),
                                  videoComment = strWithNumb(openDat.videoComment.str, innerIndex + 3)
                          ))
                          newOpenListBeta.add(openQDat(
                                  openQFormLink = strWithNumb(subModulesData.listOfOpenBetaData[indexOfOpen].openQFormLink.str, innerIndex),
                                  openQSpreadsheetLink = strWithNumb(subModulesData.listOfOpenBetaData[indexOfOpen].openQSpreadsheetLink.str, innerIndex + 1),
                                  videoLinks = listOfStrWithNumb(listOf(subModulesData.listOfOpenBetaData[indexOfOpen].videoLinks.listOfStr).flatten(), innerIndex + 2),
                                  videoComment = strWithNumb(subModulesData.listOfOpenBetaData[indexOfOpen].videoComment.str, innerIndex + 3)
                          ))
                          innerIndex +=30
                      }
                      subModulesData.copy(listOfOpenBetaData = newOpenListBeta.toList(), listOfOpenQAlfaData = newOpenListAlfa.toList(),
                              closedQFormLinkAlfa = strWithNumb(subModulesData.closedQFormLinkAlfa.str, innerIndex - 10),
                              closedQFormLinkBeta = strWithNumb(subModulesData.closedQFormLinkBeta.str, innerIndex - 9),
                              WhyImportantQFormLinkAlfa = strWithNumb(subModulesData.WhyImportantQFormLinkAlfa.str, innerIndex - 8),
                              WhyImportantFormLinkBeta = strWithNumb(subModulesData.WhyImportantFormLinkBeta.str, innerIndex - 7),
                              drawingAlfaLink = strWithNumb(subModulesData.drawingAlfaLink.str, innerIndex - 6),
                              drawingBetaLink = strWithNumb(subModulesData.drawingBetaLink.str, innerIndex - 5),
                              formLinkForFaq = strWithNumb(subModulesData.formLinkForFaq.str, innerIndex - 4)
                      )


                  }



    }




    /**
    simple splitting with null check
     */
    fun splitHashes(str: String) : List<String>{
        if(str!=null && str!= "") {
            if(!str.contains("##")) { return  listOf(str)}
            return str.split("##")}
        else {return listOf<String>()}
    }

    /**
    filtering and mapping given list of data about open questions files  and returning all related to the
    given submodule also we are removing marking string
     */
    fun getOpenQFilesRelatedToSubmodule(listOfFilesLinks: List<String>, subModuleName: String): List<String> {
        return listOfFilesLinks
                .map{ it->it.split("%%")}
                .filter { it.size>1 }
        .filter{ it->it[1] ==subModuleName }
        .map{ it->it[0]}
    }

    /**combine data about open question related video and videocomment ... and all the files
    @returns {Object[]}
    openQFormLink {String}form link to given open question
    openQSpreadsheetLink {String[]} form link to spreadsheet with answers to given open question
    videoLinks {String} link to related video in most cases it will be just one but in some thare will be more
    videoComment {String} text of video comment
     */
    fun combineDataAboutOpenQ(subModuleName: String, listOfFormLinks: List<String>, listOfSpreadsheetLinks: List<String>, listOfVideos: List<String>, listOfVideoCommeents: List<String>) : List<openQDat> {
        val formLinks = getOpenQFilesRelatedToSubmodule(listOfFormLinks, subModuleName)
        val sprLinks =getOpenQFilesRelatedToSubmodule(listOfSpreadsheetLinks, subModuleName)
        val videos =getOpenQFilesRelatedToSubmodule(listOfVideos, subModuleName)
        val videoComments =getOpenQFilesRelatedToSubmodule(listOfVideoCommeents, subModuleName)
        val res =  formLinks.mapIndexed{ index, link->
            openQDat(openQFormLink = strWithNumb(link),
                    openQSpreadsheetLink = strWithNumb(sprLinks.getOrElse(index, { "" })),
                    videoLinks = listOfStrWithNumb(videos.getOrElse(index, { "" }).split("!@!")),
                    videoComment = strWithNumb(videoComments.getOrElse(index, { "" })))
            }
        return res

    }

    /**
    we are getting the  indexes of the current date only if index of datarow is the same as found index we will outup the
    @param dateStr {String} String with date in format DAY/MONTH/YEAR
    @param allDat {String[][]} all data from relevant sheet
    @return {Number[]} indexes of occurences of dateStr
     */
    fun  getIndexesOfThissMeeting(dateStr: String, allDat: List<List<String>>) : List<Int>{

        val res =  allDat.filter {  it.size>2}.map{ row->row[3]}.filter{ it-> it!=null && it!=""}
            .mapIndexed { index, innerDateStr -> if(innerDateStr== dateStr){  index } else{-5}
                           }.filter { it-> it>-1}// as we marked that in case it is irrelevant it is -5

        return res
    }


}
/**
 * combining all relevant data from one sheet Tab - which is representing data about single classroom
 * */
data class classroomDataAboutSubModulesPerDate(
        val classroomName: String,
        val mapOfDatesAndSubM: SortedMap<LocalDate, List<SubModulesData>> // map where  a key is a date of meeting and values are all submodules related to this meeting

)

/**
 * summarizing all data about submodule
 * */
data class SubModulesData(
        val subModuleName: String,
        val drawingAlfaLink: strWithNumb,
        val drawingBetaLink: strWithNumb,

        val closedQFormLinkAlfa: strWithNumb,
        val closedQSpreadshetLinkAlfa: String,
        val closedQFormLinkBeta: strWithNumb,
        val closedQSpreadshetLinkBeta: String,

        val listOfOpenQAlfaData: List<openQDat>,// list of objects with data about given open questions
        val listOfOpenBetaData: List<openQDat>,//list of objects with data about given open questions

        val WhyImportantQFormLinkAlfa: strWithNumb,
        val WhyImportantSpreadshetLinkAlfa: String,
        val WhyImportantFormLinkBeta: strWithNumb,
        val WhyImportantSpreadshetLinkBeta: String,

        val rowIndexInThisDate: Number,//analyzing all of the rows related to this date we will mark which one it is
        val indexISplitted: Number,
        val subPresentationLink: String,
        val subPresentationId: String,
        val startHour: String,
        val spreadsheetSummarizing: String,
        val formLinkForFaq: strWithNumb,
        val spreadsheetIdForFaq: String,// will mark which subModule it is in given cell which in array after splitting


)

data class openQDat(
        val openQFormLink: strWithNumb,//form link to given open question
        val openQSpreadsheetLink: strWithNumb,// form link to spreadsheet with answers to given open question
        val videoLinks: listOfStrWithNumb,// link to related video in most cases it will be just one but in some thare will be more
        val videoComment: strWithNumb //text of video comment
)

open class sthWithNumb(var integB: Int = 0, var hasshB: UUID = UUID.randomUUID())

/**
 * just pair of string and integer
 * */
data class strWithNumb(val str: String, var integ: Int = 0) :sthWithNumb(integ) {


}

/**
 * list of Strings with number
 * */
data class listOfStrWithNumb(val listOfStr: List<String>, var integ: Int = 0) :sthWithNumb(integ) {

}


