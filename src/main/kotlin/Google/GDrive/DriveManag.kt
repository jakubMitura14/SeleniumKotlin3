package com.example.mituratest5.GDrive


import Google.MituraTestBasicDataAccessors.resultsFromAssecoFolder
import com.example.mituratest5.GDrive.DriveManag.googleDriveService
import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import java.util.*
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine


object DriveManag {


    //lateinit var  continueDrive : Continuation<Drive>
    lateinit var googleDriveService: Drive
   // var isServiceInitialized = false

}


/**
 * uploads given excel spreadsheet file into specified folder on goolgle drive
as google sheets taken from https://stackoverflow.com/questions/41482168/java-how-to-import-the-excel-file-stored-in-google-drive-to-a-specific-google
filePathStrOnHard String telling from where to take the spreadsheet
 * */
fun uploadEscelToGoogleSheets (newFileName : String, filePathStrOnHard : String): String? {

    val fileMetadata = File().apply{
        setName(newFileName)
        setMimeType("application/vnd.google-apps.spreadsheet")
        setParents(listOf(resultsFromAssecoFolder))
    }

    val filePath = java.io.File(filePathStrOnHard)
    val mediaContent = FileContent("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", filePath)
    val file: File? = googleDriveService.files().create(fileMetadata, mediaContent)
            .setFields("id")

            .execute()
    return file?.getId()

}
