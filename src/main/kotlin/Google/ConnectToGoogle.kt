package Google
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.script.ScriptScopes
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.slides.v1.SlidesScopes

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

object ConnectToGoogle  {
    val APPLICATION_NAME = "Google Drive API Java Quickstart";
    val JSON_FACTORY = JacksonFactory.getDefaultInstance();
    val TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    val SCOPES = listOf(*ClassroomScopes.all().toTypedArray(),*ScriptScopes.all().toTypedArray(),*DriveScopes.all().toTypedArray(), *SheetsScopes.all().toTypedArray(), *SlidesScopes.all().toTypedArray())
    val CREDENTIALS_FILE_PATH = "/client_secret_575883587851-nes1cd71e17neht9lt63c726maks1i7u.apps.googleusercontent.com.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        // Load client secrets.
        val inp = ConnectToGoogle::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH);

        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(inp));

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(FileDataStoreFactory(java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        val receiver = LocalServerReceiver.Builder().setPort(8888).build();
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public fun mainn() {
        // Build a new authorized API client service.
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        val service = Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        val result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        val files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            files.forEach {file->
                System.out.printf("%s (%s)\n", file.getName(), file.getId());

            }

        }
    }

    // creating http transport for sign in  use
    fun createHttpTransport(): HttpTransport {
        var res = com.google.api.client.http.javanet.NetHttpTransport()
        //  var res = AndroidHttp.newCompatibleTransport()
        return res
    }

     fun createRequestInitializer(reqInit: HttpRequestInitializer): HttpRequestInitializer {



        var res = object : HttpRequestInitializer {
            override fun initialize(request: HttpRequest?) {
                reqInit.initialize(request);
                request?.setConnectTimeout(10 * 600000);
                request?.setReadTimeout(10 * 600000);
            }
        }
        //  var res = local
        return res
    }

}