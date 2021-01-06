package Google.GSheetsManag

import com.example.mituratest5.GSheetsManag.getAllData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * manages data from main Semester spreadsheet
 * */
object manageSemsheet {

val listOfRelevantClassroomData = GlobalScope.async {  getListOfRelevantClassroomData().filter { it!!.classroomName.trim()!="course Name" }}
/**
 * will retrieve relevant classroom data from semsheet - classroom name; presentation folder, infomration is classroom online and related groups
 * */
private suspend fun getListOfRelevantClassroomData() : List<classroomData?>{
     return kotlin.runCatching {
         //(AppsScriptConnection.runAppScripFunction("getSemSheetMetaDat", linkedMapOf(), librariesIds.AssecoEduPortalIOntegrations) as List<List<String>>)
           getAllData("1nFelJ94B1cz6q_M3jUymP8kyagpzA9aoKhraqy3RY3Y","semSheet meta dat" ).drop(1)
            .map {row ->
                if(row.size>7)  println(row[7])
                 if(row.size>20){
                    classroomData(classroomName= row[7], classroomCode = row[15],presentationFolderLink = row[16], isOrganizational= row[5]!="normal",
                    isOnline = row[18].toString()=="1", assecoGroupAlfa = row[19].split(";").filter { it.length>3 }
                            , assecoGroupBeta = row[20].split(";").filter { it.length>3 }
                    ,classroomId = row[8]
                    )
                } else{null}
            }.filter { it!=null }}.getOrThrow()
}
    /**
     * getting all classroom names of non organizational classrooms
     * */
    suspend fun getAllNonOrganizationalClassroomName () =  listOfRelevantClassroomData.await().filter { !it!!.isOrganizational }.map { it!!.classroomName }







}

data class classroomData(val classroomName: String, val classroomCode: String,
                         val presentationFolderLink: String, val isOnline: Boolean, val assecoGroupAlfa: List<String>, val assecoGroupBeta: List<String>, val isOrganizational: Boolean, val classroomId: String)