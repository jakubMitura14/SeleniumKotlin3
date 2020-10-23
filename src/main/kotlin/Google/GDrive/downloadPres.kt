package Google.GDrive

import Google.ConnectToGoogle
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object DownloadPres {
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    val credential = ConnectToGoogle.getCredentials(HTTP_TRANSPORT)
    val driveServ = Drive.Builder(
            ConnectToGoogle.createHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            ConnectToGoogle.createRequestInitializer(credential)
    )
            .setApplicationName("SeleniumJava2")
            .build()

   fun getPresToFile(fileId : String) {
       val file = File("/home/jakub/Downloads/${fileId}.pdf");
       if (!file.exists()) {
           file.createNewFile();
       }

       val outputStream: OutputStream = FileOutputStream(file)
       driveServ.files().export(fileId, "application/pdf")
               .executeMediaAndDownloadTo(outputStream)
   }





}