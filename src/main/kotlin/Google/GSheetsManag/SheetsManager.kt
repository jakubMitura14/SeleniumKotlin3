package com.example.mituratest5.GSheetsManag

import appScript.MyNet
import com.google.api.client.http.FileContent
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.*
import java.io.File
import java.util.regex.Pattern
import kotlin.coroutines.Continuation


/**managing main sheet that controls the application*/
object SheetsManager {
    lateinit var  continueSheets : Continuation<Sheets>
    var isServiceInitialized = false
    lateinit var sheetsService:Sheets    // map of test codes key is a title of a classroom and value is list of test codes that it stores
//val semSheetManag = SemSheetManag()// controlls main semester sheet


    /** got from https://developers.google.com/sheets/api/reference/rest/v4/spreadsheets.values/batchUpdate
     * executes batch update on the basis of the supplied list of value ranges*/
    suspend fun executeBatchUpdate(listOfValueRanges: ArrayList<ValueRange>, sheetId: String): BatchUpdateValuesResponse? {
        var res: BatchUpdateValuesResponse? = null
        MyNet.err("executeBatchUpdate "){ val requestBody = BatchUpdateValuesRequest()
            requestBody.valueInputOption = "USER_ENTERED"
            requestBody.setData(listOfValueRanges)
            val request = SheetsManager.sheetsService. spreadsheets().values().batchUpdate(sheetId, requestBody)
            res = request.execute()
        }
        return res
    }
///////////////////////////////////////////////////////////////////////////controlling tabs///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**gets names of  spreadsheet tabs*/
    suspend fun getSpreadSheetTabsNames(sheetId: String) : ArrayList<String> {

        val res =  ArrayList(getSheetTabs(sheetId)?.map { it.properties.title }!!)

    return res
    }
    /**returns sheetsOf  spreadsheet*/
    suspend fun getSheetTabs(sheetId: String): MutableList<Sheet>? {
        var res : MutableList<Sheet>?  = null
      MyNet.err("getSheetTabs"){  val entry = SheetsManager.sheetsService.spreadsheets().get(sheetId).execute()
        res = entry.getSheets()}
        return res
    }
        /**adds appropriate tab to the given sheet*/
    suspend fun addAppropriateTabs(sheetId: String, listOfNeededTabNames: ArrayList<String>) {
        MyNet.err("addAppropriateTabs") {
        val addSheetsequests = ArrayList<Request>()
        val spreadSheetTabsNames = getSpreadSheetTabsNames(sheetId)

        for (neededTab in listOfNeededTabNames) {
            if(!spreadSheetTabsNames.contains(neededTab)) {
                println("not containing $neededTab")
                // adding appropriate shhets one for each class
                val addSheet = AddSheetRequest()
                val props = SheetProperties()
                props.setTitle(neededTab)
                addSheet.setProperties(props)
                addSheetsequests.add(Request().setAddSheet(addSheet))
            }}
        val bodyAdding = BatchUpdateSpreadsheetRequest().setRequests(addSheetsequests.toMutableList())

        if(addSheetsequests.isNotEmpty()) {
            sheetsService.spreadsheets()
                .batchUpdate(sheetId, bodyAdding).execute()
        }
    }}

//    /**executes batch append*/
//    suspend fun executeListOfRequests(listOfAppendRequests : ArrayList<Request>, spreadsheetId : String) {
//        var res: BatchUpdateValuesResponse? = null
//
//        val bodyAdding = BatchUpdateSpreadsheetRequest().setRequests(listOfAppendRequests.toMutableList())
//
//        if(listOfAppendRequests.isNotEmpty()) {
//            SheetsManager.sheetsService.await().spreadsheets()
//                .batchUpdate(spreadsheetId, bodyAdding).execute()
//        }
//
//
//    }


}

 fun getAllData(sprId: String, tabName: String): List<List<String>> {
    val range = "'$tabName'!A1:QL1000"
    SheetsManager.sheetsService.spreadsheets()
    val  response =   SheetsManager.sheetsService.spreadsheets().values()
            .get(sprId, range)
            .execute();
    return     response.getValues().map { it.map { it.toString() } }



}
/**
 * get id from google spreadsheet url taken from
 * https://stackoverflow.com/questions/45539506/how-to-extract-id-from-url-google-sheet
 * */
fun getSpreadSheedIdFromSpreadsheetLink(spreadsheetLink: String)=
        Pattern.compile("\\/d\\/(.*?)(\\/|$)")
                .matcher(spreadsheetLink).group(1)



