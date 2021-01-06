package org.example.SeleniumJavaFx

import Google.ConnectToGoogle
import Google.ConnectToGoogle.createHttpTransport
import Google.ConnectToGoogle.createRequestInitializer
import Google.GSheetsManag.manageMeetingDatesSpr
import GoogleClassroomTasks
import GoogleClassroomTasks.createTasksOnTheBasisOfData
import appScript.AppsScriptConnection
import com.example.common.librariesIds
import com.example.mituratest5.GDrive.DriveManag
import com.example.mituratest5.GDrive.DriveManag.googleDriveService
import com.example.mituratest5.GDrive.uploadEscelToGoogleSheets
import com.example.mituratest5.GSheetsManag.SheetsManager
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.classroom.Classroom
import com.google.api.services.drive.Drive
import com.google.api.services.script.Script
import com.google.api.services.sheets.v4.Sheets
import functionalUnits.*
import javafx.application.Application
import javafx.stage.Stage
import jdk.jfr.internal.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import org.example.SeleniumJavaFx.ForStart.mainInStart
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets.addClassrooms
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets.createInteractiveMeetings
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets.createInteractiveTasksLessons
import org.example.SeleniumJavaFx.combinedFunctionsWithSpreadsheets.createPassiveSzkolenies
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import info.debatty.java.stringsimilarity.*;


class Main : Application() {

    /**object performing main actions*/


    override fun start(primaryStage: Stage?) {
        mainInStart()
    }
}
 object ForStart   {
     val projectName = "SeleniumJavaFx"
    fun mainInStart() {

        DriveManag
        AppsScriptConnection
        preapreGoogleServices()
       // MainFunction.driver
        //uploadEscelToGoogleSheets("tefffffffsst", "/home/jakub/Downloads/Genetics sem1 Lectures Nur A Nur B NurC Winter 2020for beta_17.10.2020 (2).xlsx")


//        GlobalScope.launch { for (i in 1..500) {
//            AppsScriptConnection.runAppScripFunction("addWhyImportantInfo", linkedMapOf(), librariesIds.coordinateCalendar)
//         AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutImagesAndFaq", linkedMapOf(), librariesIds.coordinateCalendar)
//   AppsScriptConnection.runAppScripFunction("createSubPresentations", linkedMapOf(), "MRugS1sx-c1AqbUXv8VS5X8vMQXSGRMYb")
//       }
//    }
//
       GlobalScope.launch {
        createTasksOnTheBasisOfData ()

           for (i in 1..500) {
               AppsScriptConnection.runAppScripFunction("iterateAndAddDLinkOfSummarizingSpreadsheet", linkedMapOf(), librariesIds.coordinateCalendar)
//             AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutImagesAndFaq", linkedMapOf(), librariesIds.coordinateCalendar)
//               AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutOpenQAndVideo", linkedMapOf(), librariesIds.coordinateCalendar)
//               AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutClosedQ", linkedMapOf(), librariesIds.coordinateCalendar)
//               AppsScriptConnection.runAppScripFunction("addWhyImportantInfo", linkedMapOf(), librariesIds.coordinateCalendar)

//
           }

       }

//        GlobalScope.launch {
//            for (i in 1..500) {  AppsScriptConnection.runAppScripFunction("iterateAndAddDLinkOfSummarizingSpreadsheet", linkedMapOf(), librariesIds.coordinateCalendar)
//        }}


////AppsScriptConnection.runAppScripFunction("addUniqueCodes", linkedMapOf(), "MRugS1sx-c1AqbUXv8VS5X8vMQXSGRMYb")
//AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutOpenQAndVideo", linkedMapOf(), librariesIds.coordinateCalendar)
//AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutClosedQ", linkedMapOf(), librariesIds.coordinateCalendar)
//AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutImagesAndFaq", linkedMapOf(), librariesIds.coordinateCalendar)
//AppsScriptConnection.runAppScripFunction("addWhyImportantInfo", linkedMapOf(), librariesIds.coordinateCalendar)
//
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutOpenQAndVideo", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutClosedQ", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutImagesAndFaq", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("addWhyImportantInfo", linkedMapOf(), librariesIds.coordinateCalendar)
//
//
//
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutOpenQAndVideo", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutClosedQ", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutImagesAndFaq", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("addWhyImportantInfo", linkedMapOf(), librariesIds.coordinateCalendar)
//
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutOpenQAndVideo", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutClosedQ", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDataAboutImagesAndFaq", linkedMapOf(), librariesIds.coordinateCalendar)
//          AppsScriptConnection.runAppScripFunction("addWhyImportantInfo", linkedMapOf(), librariesIds.coordinateCalendar)
//
//
//          AppsScriptConnection.runAppScripFunction("iterateAndAddDLinkOfSummarizingSpreadsheet", linkedMapOf(), librariesIds.coordinateCalendar)
//
//
// // }
//      for (i in 1..50) {
//          // AppsScriptConnection.runAppScripFunction("iterateAndAddDLinkOfSummarizingSpreadsheet", linkedMapOf(), librariesIds.coordinateCalendar)
//      }
//
//
//
//
//
//  }


//        GlobalScope.launch {
//            delay(900000)
//      for (i in 1..50) {
//          AppsScriptConnection.runAppScripFunction("createBankOfPres", linkedMapOf(), "MjugUbJ_unZ75SQNU0649vsvMQXSGRMYb")
//          AppsScriptConnection.runAppScripFunction("createBankOfEmptyGoogleSheets", linkedMapOf(), "MjugUbJ_unZ75SQNU0649vsvMQXSGRMYb")
//          AppsScriptConnection.runAppScripFunction("createBankOfEmptyGoogleForms", linkedMapOf(), "MjugUbJ_unZ75SQNU0649vsvMQXSGRMYb")
//      }  }


        // }

//GlobalScope.launch {
//    listOf( LoginSection(),*addClassrooms().toTypedArray()).forEach {
//        MainFunction.processSection(it)
//    }}

//
//GlobalScope.launch {
//    val listOFDat = createPassiveSzkolenies()
//    println("lllllllllllllllllllll  "+ listOFDat.flatMap{it.subModulesToAdd})
//
//    listOf( LoginSection(),*listOFDat.toTypedArray()).forEach {
//        MainFunction.processSection(it)
//    }}


//GlobalScope.launch {
//    listOf( LoginSection(),*createInteractiveMeetings () .toTypedArray()).forEach {
//        MainFunction.processSection(it)
//    }}

//        GlobalScope.launch {
//            listOf(LoginSection(), *createInteractiveTasksLessons().toTypedArray()).forEach {
//                MainFunction.processSection(it)
//            }
//        }
    }



fun preapreGoogleServices (){

    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    val credential = ConnectToGoogle.getCredentials(HTTP_TRANSPORT)
    val appScriptService = Script.Builder(
            createHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            createRequestInitializer(credential))

            .setApplicationName(projectName)
            .build()


    if (!AppsScriptConnection.isScriptInitialized) {
        AppsScriptConnection.isScriptInitialized = true
        AppsScriptConnection.scriptContinuation.resume(
                appScriptService
        )
    }


//    val googleDriveService: Drive = Drive.Builder(
//            createHttpTransport(),
//            JacksonFactory.getDefaultInstance(),
//            createRequestInitializer(credential)
//    )
//            .setApplicationName(projectName)
//            .build()
//    if (!DriveManag.isServiceInitialized) {
//        DriveManag.isServiceInitialized = true
//        DriveManag.continueDrive.resume(googleDriveService)
//    }

    SheetsManager.sheetsService = Sheets.Builder(
            createHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            createRequestInitializer(credential)
    )
            .setApplicationName(projectName)
            .build()

    googleDriveService = Drive.Builder(
            createHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            createRequestInitializer(credential)
    )
            .setApplicationName(projectName)
            .build()

    GoogleClassroomTasks.classroomServ = Classroom.Builder(
            createHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            createRequestInitializer(credential)
    )
            .setApplicationName(projectName)
            .build()

}


}