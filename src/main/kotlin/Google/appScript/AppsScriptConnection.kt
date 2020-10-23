package appScript


import com.example.common.AppScriptBasicManager

import com.google.api.services.classroom.model.StudentSubmission
import com.google.api.services.script.Script
import com.google.api.services.script.model.ExecutionRequest
import com.google.api.services.script.model.Operation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

/**some of intended functions can be invoked only through apps script*/
object AppsScriptConnection {
    var isScriptInitialized = false
    lateinit var scriptContinuation : Continuation<Script>
    val script = GlobalScope.async { suspendCoroutine<Script> {scriptContinuation = it  } }
val basicManger = object: AppScriptBasicManager() {
    override var functionOnTextChange: (str: String) -> Unit = {}


    override suspend fun executeASfunAndGetString(
        libraryCode: String,
        functName: String
    ): String {

        var res : Any ?=""
        MyNet.err(functName) {
            println("run app script functName $functName ")
            val request = ExecutionRequest()
            request.devMode = true
            request.setFunction(functName)
           val params = ArrayList<Any>()
//            params.addAll(mapOfParameters.values)
            request.parameters = params
            val op = script.await().scripts().run(libraryCode, request).execute()

            try {
                res = (op.response["result"])
            }
            catch(e: Exception) {
      println("error in ${functName}"+  e.message)
            }
        }
        return  if(res is String) {res as String}  else{""}
}

    override suspend fun createNeededCodes() {

    }


}

    /**createing the request to run functions in app script and returning its response*/
    suspend fun runAppScripFunction (functName: String, mapOfParameters : LinkedHashMap<String, Any>,scriptId:String) : Any?{
        var res : Any ?=""
        MyNet.err(functName) {
            println("run app script functName $functName mapOfParameters $mapOfParameters")
            val request = ExecutionRequest()
            request.devMode = true
            request.setFunction(functName)
            val params = ArrayList<Any>()
            params.addAll(mapOfParameters.values)
            request.parameters = params
            val op = script.await().scripts().run(scriptId, request).execute()

            try {
                res = (op.response["result"])
            }
            catch(e: Exception) {
                e.stackTrace.forEach {
                    println(it)
                }
            }
        }
        return  res
    }








    /**pushing the results of a specified form into a sheet that is localised under the folder (which is a subflder of for teacher)
     * with name the same as classroom*/
//suspend fun pushFormToTheSheet(formId: String, sheetId: String, formName: String, modifTestFormData: testFormData){
//        println("arguments for pushFormToTheSheet formId ${formId} sheetId ${sheetId} formName ${formName}")
//      MyNet.err("pushFormToTheSheet in AppsScriptConnection" ) {
//          val request = ExecutionRequest()
//          request.devMode = true
//          request.setFunction("pushFormToTheSheet")
//
//          val params = ArrayList<Any>()
//          params.add("${formId}")
//          params.add("${sheetId}")
//          params.add("${formName}")
//          request.parameters = params
//          val op = script!!.await().scripts().run(scriptId, request).execute()
//
//          check((op.response["result"] as String).equals("${formId}"))
//
//
//         nameTheSheetCorrectly(formId,sheetId,formName,modifTestFormData)
//          modifTestFormData.isWithSheetSynced = true
//      }
//    }

//    // gets all ids of forms that has more than two reponses in given class folder
//    suspend fun getFormsWithMoreThanTwoResponses(folderId : String) : ArrayList<String>{
//            return runAppScripFunction("getFormsWithMoreThanTwoResponses", linkedMapOf("folderId" to folderId)) as ArrayList<String>
//    }
//
////    /**after creating the sheet in appropriate spreadsheet we need to name it correctly in order to be later usable*/
////    suspend fun nameTheSheetCorrectly(formId: String, sheetId: String, formName: String, modifTestFormData: testFormData){
////            MyNet.err("pushFormToTheSheet in AppsScriptConnection" ) {
////                val request = ExecutionRequest()
////                request.devMode = true
////                request.setFunction("nameTheSheetCorrectly")
////
////                val params = ArrayList<Any>()
////                params.add("${formId}")
////                params.add("${sheetId}")
////                params.add("${formName}")
////                request.parameters = params
////                val op = script!!.await().scripts().run(scriptId, request).execute()
////
////                check((op.response["result"] as String).equals("${formId}"))
////
////                modifTestFormData.isWithSheetSynced = true
////            }
////        }
//
//
////adding a link to the submission in order to enable student see details like their answers, what answers are correct etc id is submission id
//   suspend fun addLinkToSubmission (link : String, courseId : String, courseWorkId: String, id : String){
// //   MyNet.err("pushFormToTheSheet in AppsScriptConnection" ) {
//        println("link  $link courseId $courseId courseWorkId $courseWorkId id $id")
//        val request = ExecutionRequest()
//        request.devMode = true
//        request.setFunction("addLinkToSubmission")
//
//        val params = ArrayList<Any>()
//        params.addAll(arrayListOf(link,courseId,courseWorkId,id))
//
//        request.parameters = params
//        val op = script!!.await().scripts().run(scriptId, request).execute()
////    }
//}
//
///**gives ArrayList of answers with specified (at the end) are they correct answers or not based on the form id and question number it uses apps script to achieve this
// * */
//suspend fun  getAnswersAndAreTheyCorrect(formId : String) : ArrayList<String> {
//    val res = ArrayList<String>()
//    MyNet.err("getAnswersAndAreTheyCorrect" ) {
//        val request = ExecutionRequest()
//        request.devMode = true
//        request.setFunction("getAnswersAndAreTheyCorrect")
//        val params = ArrayList<Any>()
//        params.add(formId)
//     //   params.add(questionNumb)
//        request.parameters = params
//        val op = script!!.await().scripts().run(scriptId, request).execute()
//
//        //check((op.response["result"] as String).equals("${formId}"))
//        (op.response["result"] as ArrayList<String>).forEach {
//res.add(it)
//        }
//    }
//    return res
//}
//
//  suspend  fun add (a: Int, b: Int) : Int {
//      return   runAppScripFunction("add", linkedMapOf<String,Any>("a" to a, "b" to b)) as Int
//    }
/////**coordinates pushing the marks*/
////  suspend  fun coordinator (
////    courseId: String,
////    formId: String,
////    mapOfMailsBasedOnId: HashMap<String, String>
////){
////    var courseWorkId= createAssignmentsIfDoNotExist(courseId,formId)
////    var ListOfStudSubm= mutableListOf<StudentSubmission>()
////  MyNet.err("ListOfStudSubm") {
////      ListOfStudSubm = ClassroomControllObject.classroomService.await().courses().courseWork()
////          .studentSubmissions().list(courseId, courseWorkId).execute()
////          .studentSubmissions// list of student submissionns
////  }
////    var ListOfStudSubmId = ListOfStudSubm.map { it.id }
////    var ListOfStudSubmMail=  ListOfStudSubm.map {mapOfMailsBasedOnId[it.userId]  }
////    var ListOfStudSubmUserId=  ListOfStudSubm.map {it.userId }
////
////  var countingIndex = 0
////    val reducedListOfStudSubmIds = ArrayList<String>()
////    val reducedListOfStudSubmMail = ArrayList<String>()
////    val reducedListOfListOfStudSubmUserId = ArrayList<String>()
////    for(indeex in ListOfStudSubmId.indices) {
////        countingIndex++
////        if(ListOfStudSubmMail.indices.contains(indeex)){
////        var mail = ListOfStudSubmMail[indeex]?:getUserMailBasedOnSubmissionAndFilledFormByStudent(ListOfStudSubmUserId[indeex] )
////        reducedListOfStudSubmIds.add(ListOfStudSubmId[indeex])
////        reducedListOfStudSubmMail.add(mail)
////            reducedListOfListOfStudSubmUserId.add(ListOfStudSubmUserId[indeex])
////        if(countingIndex>5){countingIndex=0
////            properCoordinator(courseId,formId,courseWorkId,reducedListOfStudSubmIds,  reducedListOfStudSubmMail,reducedListOfListOfStudSubmUserId)
////            reducedListOfStudSubmIds.clear()
////            reducedListOfStudSubmMail.clear()
////            reducedListOfListOfStudSubmUserId.clear()
////        }}
////  }
////if(reducedListOfStudSubmIds.isNotEmpty()) {
////    properCoordinator(
////        courseId,
////        formId,
////        courseWorkId,
////        reducedListOfStudSubmIds,
////        reducedListOfStudSubmMail,
////        reducedListOfListOfStudSubmUserId
////    )
////}
////}
//    /**coordinates the response*/
//   suspend fun properCoordinator(
//        courseId: String,
//        formId: String,
//        courseWorkId: String,
//        listOfStudSubmId: List<String>,
//        listOfStudSubmMail: List<String?>,
//        listOfListOfStudSubmUserId: ArrayList<String>
//    ) {
//        MyNet.err("coordinator" ) {
////            println("courseId $courseId  formId $formId courseWorkId $courseWorkId"  )
////            println( "ListOfStudSubmId    "+listOfStudSubmId.map { "'$it'" } )
////            println( "ListOfStudSubmMail    "+listOfStudSubmMail.map { "'$it'" } )
////            println( "reducedListOfListOfStudSubmUserId    "+listOfListOfStudSubmUserId.map { "'$it.'" } )
//
//            val request = ExecutionRequest()
//            request.devMode = true
//            request.setFunction("coordinator")
//            val params = ArrayList<Any>()
//            params.addAll(arrayListOf(courseId,formId,courseWorkId ,listOfStudSubmId,listOfStudSubmMail, listOfListOfStudSubmUserId))
//            request.parameters = params
//            val op = script!!.await().scripts().run(scriptId, request).execute()
//            //  (op.response["result"] as String)
////    }
//        }
//    }
//
//
//    /**creates assignment if does not exist and either way returns id of assignment with specified form id in specified classroom/
// */
//   suspend fun createAssignmentsIfDoNotExist(classroomId : String, formId : String) : String {
//    //println("link  $link courseId $courseId courseWorkId $courseWorkId id $id")
//     var res = ""
//        MyNet.err("getAnswersAndAreTheyCorrect" ) {
//
//        val request = ExecutionRequest()
//    request.devMode = true
//    request.setFunction("createAssignmentsIfDoNotExist")
//
//    val params = ArrayList<Any>()
//    params.addAll(arrayListOf(classroomId,formId))
//    request.parameters = params
//    val op = script!!.await().scripts().run(scriptId, request).execute()
//  res=  (op.response["result"] as String)
////    }
//    }
//        return res
//    }
//
//    /**marking is form ready to publish */
//suspend fun checkIsFormReady (formId: String) : Boolean{
//var res = false
//    MyNet.err("checkIsFormReady" ) {
//        val request = ExecutionRequest()
//        request.devMode = true
//        request.setFunction("checkIsFormReady")
//
//        val params = ArrayList<Any>()
//        params.add("${formId}")
//        request.parameters = params
//        val op = script!!.await().scripts().run(scriptId, request).execute()
//
////        println(op.response["result"])
//        //check((op.response["result"] as String).equals("${formId}"))
//        res = (op.response["result"] as Boolean)
//
//    }
//    return res
//}
//
//    /**get user mail based on the submission id and data from form manually filled by student*/
//  suspend fun  getUserMailBasedOnSubmissionAndFilledFormByStudent ( userId: String)  : String{
//       return runAppScripFunction("getUserMailBasedOnSubmissionAndFilledFormByStudent", linkedMapOf( "userId" to userId  )) as String
//    }
//    /** creates assignment where for each student there will be created a form where He\ she will need to add his or her studen id , e mail from what class is etc*/
//    suspend fun createIdsAssignment (classroomId : String) : String {
//       return runAppScripFunction("createIdsAssignment", linkedMapOf("classroomId" to classroomId)) as String
//    }
//
//    /** coordinates submissions of IdsAssignments in order to add appropriate forms to them etc */
//   suspend fun  coordinateSubmissions(courseId : String, courseWorkId: String,commentsFolder: String, templateFormId: String){
//        runAppScripFunction("coordinateSubmissions", linkedMapOf("courseId" to courseId,
//           "courseWorkId" to courseWorkId, "commentsFolder" to commentsFolder, "templateFormId" to templateFormId))
//    }
//    // just for testing connection
//    suspend fun getFormsListTab () : String {
//      return  runAppScripFunction("getFormsListTab", linkedMapOf()) as String
//    }
//
//



}
/*




        @Throws(Exception::class)
    suspend fun add(v1: Int, v2: Int): Int {
        val request = ExecutionRequest()
        request.devMode = true
            request.setFunction("add")

        val params = ArrayList<Any>()
        params.add(v1)
        params.add(v2)
        request.parameters = params

        try {

            val op = script!!.await().scripts().run(scriptId, request).execute()
            if (op.error == null) {
                return (op.response["result"] as BigDecimal)
                    .intValueExact()
            }

            // Error
            println(getScriptError(op))

        } catch (e: Exception) {
            e.printStackTrace()
            System.exit(-1)
        }

        throw Exception("Unexpected error")

    }

    private fun getScriptError(op: Operation): String? {

        if (op.error == null) {
            return null
        }

        val detail = op.error.details[0]
        val stacktrace = detail["scriptStackTraceElements"] as List<Map<String, Any>>

        val sb = StringBuilder("\nScript error message: ")
        sb.append(detail["errorMessage"])
        sb.append("\nScript error type: ")
        sb.append(detail["errorType"])

        if (stacktrace != null) {
            sb.append("\nScript error stacktrace:")
            for (elem in stacktrace) {
                sb.append("\n  ")
                sb.append(elem["function"])
                sb.append(":")
                sb.append(elem["lineNumber"])
            }
        }
        sb.append("\n")
        return sb.toString()

    }*/