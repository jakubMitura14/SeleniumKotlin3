package com.example.common

import kotlinx.coroutines.delay
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty

object librariesIds  {

    val MTA = "MiReZhIA1JVcoRNalRUMYFvZQHuFeqzZk"
    val AssecoEduPortalIOntegrations = "MbGDtVvWk1uaIPUKRqTE40vZQHuFeqzZk"
    val SubModulesManager = "MRugS1sx-c1AqbUXv8VS5X8vMQXSGRMYb"
    val coordinateCalendar = "MP6KqDryLHzoZ2gNxYkENkvZQHuFeqzZk"
}


abstract class AppScriptBasicManager {

   abstract var functionOnTextChange : (str: String)->Unit

    var observableData: String by Delegates.observable("Initial value") {
            property, oldValue, newValue ->
        functionOnTextChange(newValue)
    }




    /** executing the app script function in specified app script library and returning String which
     * will be true if execution goes well and false otherwise */
    abstract suspend fun executeASfunAndGetString(libraryCode: String, functionName: String): String

    /**
     * @description  getting data from spreadsheet that constitutes interface between  app script and android in order to  get data what codes needs to be created
     * */
   abstract suspend fun createNeededCodes()

    inner abstract class basicExecutionClass() {
        // code needed for connection with proper library
        abstract val libraryCode: String

        /** executes app script function that is returning String*/
        suspend fun execute(functionName: String): String {
            var funresult = executeASfunAndGetString(libraryCode, functionName)

            observableData = "$functionName  executed with result:   $funresult"
            println(" fuuuuunction Of Appscript $functionName  executed with result:   $funresult")
            return funresult
        }

        /** executes  the functions that are based on iterators (my functions in app script that works by adding to appropriate column with true false value and evry iteration it invokes the same)*/
        suspend fun executeAndLoop(functionName: String): String {
            var funResult = execute(functionName)
            when (funResult) {
                "true" -> {
                }// there is nothing that needs to be done
                "false timeOut" -> {
                    executeAndLoop(functionName)
                }// recursively invokes this function untill it does not call this
                else -> { // it probably means that we have some error we will try to invoke this after some time
                    repeatAfterSetTimeIfNoMoreThan(10000,800000,{it=="true"}){ execute(functionName)}
                    }
                }
            return funResult
        }
        /**
         * refreshing functions typically use a lot of read quotas and are relatively fast so we will use delay before its execution to limit problems with exceeding quotas
         * */
        suspend fun executeAndLoopRefreshing(functionName: String): String {
            delay(50000)
         return    executeAndLoop(functionName)
        }
    }

    /**
     * functions connected to assesment task spreadsheets
     * */
    inner class SubModulesManager : basicExecutionClass() {
        override val libraryCode: String
            get() = "MRugS1sx-c1AqbUXv8VS5X8vMQXSGRMYb"
        suspend fun createSubPresentations() : String {
            return  executeAndLoop("createSubPresentations")}

    }

/**
 * @descritpion repeats function after set amount of time if this time does not exceeds maximum time or if some condition is not met function will be invoked recursively until some of
 * this will happen
 * @param functionToInvoke {()-> String} main function that we are invoking here
 * @param time {INT} after so many seconds there would be invocation of the function after evry iteration it double the amount of time it will wait
 * @param maxTime {INT} we well not invoke function one more time if threfrcreateQuestionSheetsTOPe time will exceed this time
 * @param conditionFunction {(res: String)->Boolean} takes as a parameter result of invoked function it will return true if execution gone well and
 * we do not need to invoke ito more
 * @return {String} returns the result of the invoked function
 * */
suspend fun repeatAfterSetTimeIfNoMoreThan (time:Long, maxTime :Long, conditionFunction : (String)->Boolean ,functionToInvoke :suspend ()->String) : String {
   delay(time)
 return  functionToInvoke().let {
         it-> if(time<maxTime && !conditionFunction(it)){ repeatAfterSetTimeIfNoMoreThan(time*2,maxTime,conditionFunction,functionToInvoke) } else{ it}
 }
}

    /** setting up basic sheets*/
    inner class setUpDataSheets : basicExecutionClass() {
        override val libraryCode: String
            get() = "McXQs1kA3vHgxhYv4upb_isvMQXSGRMYb"

        /**
         * creates basic sheets that will be used to store and process data
         *@return {string} true string if execution goes ok false with error message otherwise
         */
        suspend fun createBasicSheetsTop() {
            execute("createBasicSheetsTop")
        }


        /**
         *creating appropriate tab and titles row in semsheet
         *@param {String} semsheetId semester sheet Id
         */
        suspend fun prepareSemesterSheetTOP() {
            execute("prepareSemesterSheetTOP")
        }


        /*controlls the data that will be injected ino semsheet metaData
*/
        suspend fun setDataForSemSheetMetaDataTop() {
            execute("setDataForSemSheetMetaDataTop")
        }


        /**
         *@description appending to the semsheet metadat options all subjects and subsubjcts on the basis of presentations and jams folder
         *@param presentationAndJamFolderId {String} id of a folder where we have stored presentations and jams
         *@param semsheetId {String} id of main semester spreadsheet
         */
        suspend fun appendSubjectsAndSubsubjectsToMetaDAtOptionsTOP() : String{
            return executeAndLoop("appendSubjectsAndSubsubjectsToMetaDAtOptionsTOP")
        }

        /**
         *@description sets up exersise or lectures, field of study, semewster of subject and classroom type columns in metadata options
         *@param semsheetId {String} id of main semester spreadsheet
         */
        suspend fun setUpNoSubjectColumnsInMetaDatOptionsTOP(): String{
            return executeAndLoop("setUpNoSubjectColumnsInMetaDatOptionsTOP")
        }

        /**
         *@description preapares dropdowns in semsheet metadata in order to be able to convininetly manually set up variables needed to instatniate classrooms
         * @param semSheetManagId {String} : Id of semester sheet spreadsheet
         */
        suspend fun preapareDropDownsForsemsheetMetaTabTOP(): String{
            return executeAndLoop("preapareDropDownsForsemsheetMetaTabTOP")
        }

        /**
         *@description creates names of classrooms and in the same time adds some basic data about classroom
         * @param semSheetManagId {String} : Id of semester sheet spreadsheet
         *@param forTeacherFolderId {String} id of for teacher folder in main semester folder
         *@param forStudentsFolderId {String} id of for students folder in main semester folder
         */
        suspend fun addBasicDataToSemsheetTOP () : String{
            return executeAndLoop("addBasicDataToSemsheetTOP")
        }


    }

    /** setting up basic folders*/
    inner class setUpFolders : basicExecutionClass() {
        override val libraryCode: String
            get() = "MdJujqj6wPPfekqSLhiBwz8vMQXSGRMYb"



        /**
         * adds folders to the basic semester folder
         */
        suspend fun setMainFoldersInSemesterFolderTOP() : String {
            return executeAndLoop("setMainFoldersInSemesterFolderTOP")

        }

        /**
        @description creates main Folders including root folder
         */
       suspend fun  getmainFolders () : String {
            return executeAndLoop("getmainFolders")
        }


    }

    /** managing test base*/
    inner class manageTestBase : basicExecutionClass() {
        override val libraryCode: String
            get() = "M63K-EoliVqwmNh6yaye6psvMQXSGRMYb"



        /**
         * modyfying question bases by removing doubles , etc...
         */
        suspend fun modifyQuestionBases() : String {
            return executeAndLoop("modifyQuestionBases")

        }



    }



    /** setting up basic folders*/
    inner class createNewForms : basicExecutionClass() {
        override val libraryCode: String
            get() = "Ml5hf_l3fV_-hbsTdpfRLo8vMQXSGRMYb"

        /**
         *@description creating new test form on the basis on the given template , this test is linked
        to some specific date and specific classroom and will reflect this properties in its name ant title
         *@return {string} string marking weather execution gone well
         */
        suspend fun  createFormForGivenTestMeetingTOP() : String {
            return executeAndLoop("createFormForGivenTestMeetingTOP")
        }


/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of settingWhichTestIsFirstLastETC
**/
suspend fun  refrcreateFormForGivenTestMeetingTOP()  : String{
    return executeAndLoopRefreshing("refrcreateFormForGivenTestMeetingTOP")
}

        /**
         *@description checking is given form ready - the answers are modified from default values to  sth else
         *@return {string} string marking weather execution gone well
         */
        suspend fun  markIsFormReadyTOP ()  : String{
            return executeAndLoop("markIsFormReadyTOP")
        }
/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of markIsFormReady
**/
suspend fun  refrmarkIsFormReadyTOP()   : String{
    return executeAndLoopRefreshing("refrmarkIsFormReadyTOP")
}


    }



    /** managink bank of files - created to bypass limitations connected to limited quotas of files we can create one day*/
    inner class createBankOfFiles : basicExecutionClass() {
        override val libraryCode: String
            get() = "MjugUbJ_unZ75SQNU0649vsvMQXSGRMYb"

        /**
         *@description  creates bank of blank google docs and stores them in given folder, first it checks how many docs are already in folder and wheather the number that was supplied as a target was reached
             */
        suspend fun createBankOfEmptyDocsTOP () : String{
            return    executeAndLoop("createBankOfEmptyDocsTOP")        }

        /**
         *@description in order to recycle docs (we have constraints of how many docs daily we can create)
        we can recycle some of the docs created previously using this method it will take all of the docs that it can find in source folder
        than clear them from content change name to 'empty' and push to target folder
         */
        suspend fun recycleDocsTOP () : String{
            return    executeAndLoop("recycleDocsTOP")  }

        suspend fun createBankOfEmptySheets () : String{
            return    executeAndLoop("createBankOfEmptySheets")  }



    }



    /**
     * functions connected to assesment task spreadsheets
     * */
    inner class AssesmentTasks : basicExecutionClass() {
        override val libraryCode: String
            get() = "MMMOh7MveZg7naMzyr2w-X8vMQXSGRMYb"

        /**
         *@description adds tab for each classroom that the student is involved in to the assesment task table
         *@return {String} true String if execution gone well
         */
      suspend  fun prepareSmallAssesmentTaskTablesTOP() : String{
            return  executeAndLoop("prepareSmallAssesmentTaskTablesTOP")
        }

        /**
         *@description prepare  tabs and titles row for main assesment task spreadsheet
         *@param semSheetManagId {String} id of main semester spreadsheet
         *@param assesmentTaskSprId {String} id of spreadsheet with accumulated data with assesment task
         *@return {String} true String if execution gone well
         */
        suspend  fun prepareTabsAndTitlesRowFormMainAssesmentsTasksSprTOP(): String{
            return  executeAndLoop("prepareTabsAndTitlesRowFormMainAssesmentsTasksSprTOP")}

        /**
         *@description adds tickBoxes to  the is ready column of the small spreadsheet controlling assesment task
         *@param semSheetManagId {String} id of main semester spreadsheet
         *@return {String} true String if execution gone well
         */
        suspend   fun addCheckBoxesToSmallAssesmentTaskSprTOP(): String{
            return  executeAndLoop("addCheckBoxesToSmallAssesmentTaskSprTOP")}

        /**
         *@description creates formula that adds needed data into assesment task main table that gets from students
        assesment tasks mini spreadsheets and basic student Data
         *@param semSheetManagId {String} id of main semester spreadsheet
         *@param assesmentTaskSprId {String} id of spreadsheet with accumulated data with assesment task
         */
        suspend  fun getDataFromSmallassTaskToBigSprTOP(): String{
            return  executeAndLoop("getDataFromSmallassTaskToBigSprTOP")}

        /**
         *@description this step is to synchronize back the data that is collected from all students to enable
        creating unique assesment tasks and to give student back information about his grade and if needed what he or she needs to redo
         *@param semSheetManagId {String} id of main semester spreadsheet
         *@param assesmentTaskSprId {String} id of spreadsheet with accumulated data with assesment task
         *@return {String} true String if execution gone well
         */
        suspend   fun getDataBackFromBigToSmallTOP(): String{
            return  executeAndLoop("getDataBackFromBigToSmallTOP")}



    }



    /****************************** setting up basic folders*/
    inner class sheetUtils : basicExecutionClass() {
        override val libraryCode: String
            get() = "M8bdbKxt-UKC6mAimlsjSzsvMQXSGRMYb"




    }


    inner class basicStudentDataManag : basicExecutionClass() {
        override val libraryCode: String
            get() = "Mg1CrEj4kSMJf_nC3tW1ZZMvMQXSGRMYb"

        /**
         *@decription prepares tab and titles of basic student data tab in main semester spreadsheet
         */
        suspend fun prepareBasicStudentDataTab() : String {
            return  executeAndLoop("prepareBasicStudentDataTab")}

        /**
         *@description adding some additional formulas to basic student data tab it will apply formulas to all rows where there is a student codes
        so it needs to be refreshed when new student codes are created formulas added will
        all connected to this student code  classroom name  classroomIds  classroom codes presentationfolders  formIds of small tests  spreadsheetIdswithAnswersToSmallTests
         *@param semsheetmanagId {String} id of main semester spreadsheet
         *@param formsListSpreadsheet {String} id of formslist spreadsheet
         */
        suspend fun  addAdditionalFunctionToBasicDatTabTOP (): String{
            return  executeAndLoop("addAdditionalFunctionToBasicDatTabTOP")}




        /**
         *@decription add folder for each student in appropriate subfoder of for students folder and attaches there
        main student spreadsheet where the student will have summary of all necessary data there and basicStudentData form which will be used by the student
        to give basic information about her him we will also attach here spreadsheet that will get data from this
        form so we may connect it to appropriate row in basic student data tab in sem sheet
        also we will add google sheet that will controll data about students assesment tasks
         */
        suspend fun  addBasicFilesForEachStudentTOP(): String {
            return  executeAndLoop("addBasicFilesForEachStudentTOP")}



        /**
         *@description connects basic student data form with supplied spreadsheet and this spreadsheed with
        with semsheet matadata
         *@param semsheetId {String} id of main semester spreadsheet
         */
        suspend fun  connectBasicDataFormStudentSpreadshettAndSemSheetTOP (): String{
            return  executeAndLoop("connectBasicDataFormStudentSpreadshettAndSemSheetTOP")}



    }













    /****************************** generate answer sheets*/
    inner class coordinateFormsAndPaper : basicExecutionClass() {
        override val libraryCode: String
            get() = "MeNtjkYhHFJN6WeJ9KbGfN8vMQXSGRMYb"

        /**
         *@description prepares the questions sheets to print from the forms that are marked as ready and are in next nine days (number configurable)
         *@return {string} string marking weather execution gone well
         */
     suspend   fun prepareFormsToPrintTOP() : String{
            return   executeAndLoop("prepareFormsToPrintTOP")}
        /**
         *@description refreshes true false columns in order to enable execution of
        prepareFormsToPrintTOP
         */
        suspend    fun refrprepareFormsToPrintTOP () : String {
            return   executeAndLoopRefreshing("refrprepareFormsToPrintTOP")
        }



        /**
         *@description in order to communicate effectively with for arcode/qr code ... generators needed data will
        be pushed to appropriate spreadsheet in order to enable later processing  - generation of needed codes
        into appropriate folders
         *@return {string} string marking weather execution gone well
         */
        suspend   fun pushCourseIdsAndSubmissionsForBarcodesETCTOP() : String{
            return   executeAndLoop("pushCourseIdsAndSubmissionsForBarcodesETCTOP")
        }

        /**
         *@description refreshes true false columns in order to enable execution of
        prepareFormsToPrintTOP
         */
        suspend     fun refrpushCourseIdsAndSubmissionsForBarcodesETCTOP () : String {
            return   executeAndLoopRefreshing("refrpushCourseIdsAndSubmissionsForBarcodesETCTOP")        }


        /**
         *@description creates answer sheet for each submissionid (so for each student for each test)
        using genereted barcodes, qr codes and barcodes where aztec code encodes data about classroom id; ean codes codes data about question numbers
        and qr codes encodes submission Ids and pushes link to appropriate document for this particular test plus to the document with all tests that needs to be printed
        (we have one document for each courseWork id of which we will take from formsListSpreadSheet)plus one collecting document in root folder of semester forlder
         *@return {string} string marking weather execution gone well
         */
        suspend fun createQuestionSheetsTOP () : String {
            return executeAndLoop("createQuestionSheetsTOP")
        }
        /**
         *@description refreshes true false columns in order to enable execution of
        prepareFormsToPrintTOP
         */
        suspend fun refrcreateQuestionSheetsTOP() : String {
            return  executeAndLoopRefreshing("refrcreateQuestionSheetsTOP ")
        }


    }




    /********************************** setting up  functions connected to calendar*/
    inner class coordinateCalendar : basicExecutionClass() {
        override val libraryCode: String
            get() = "MP6KqDryLHzoZ2gNxYkENkvZQHuFeqzZk"

        suspend fun setDatesOfFinalMeetingsToMetaDat() : String {
         return   executeAndLoop("setDatesOfFinalMeetingsToMetaDat")
        }

        /*refreshes true false columns to enable re execution of setDatesOfFinalMeetingsToMetaDat*/
        suspend fun refrsetDatesOfFinalMeetingsToMetaDat(): String  {
            return    executeAndLoopRefreshing("refrsetDatesOfFinalMeetingsToMetaDat")
        }

        /**
         *@description adds needed tabs and titles to each tab in order to enable later work on them
         *@param semSheetId {string} id of main semester spreadsheet
        @param meetingDatesSpr {string} id of spreadsheet with all dates of meetings
         */
        suspend fun  prepareMeetingDatesSpreadsheetTabsAndTitlesTOP() : String {
            return  executeAndLoop("prepareMeetingDatesSpreadsheetTabsAndTitlesTOP")
        }

        /**
         *@description  append all dates of meetings in that classroom with marking is it the last meeting and weather this will be meeting with a
        normal test ( wheather there would be test or not depends on set criteria for example no test on first meeting no notrmal test on last meeting only final test in that situation)
         *@return {string} string marking weather execution gone well
         */
        suspend fun  appendDatesOfMeetingsTOP(): String {
            return executeAndLoop("appendDatesOfMeetingsTOP")
        }
/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of appendDatesOfMeetings
**/
suspend fun  refrappendDatesOfMeetings() : String {
    return  executeAndLoopRefreshing("refrappendDatesOfMeetings")
        }

        /**
         *@description simply sorting the dates of meetings  in order to enable later setting information weather the meeting is first second etc
         */
        suspend fun  sortDatesInCorrectOrderTOP (): String {
            return  executeAndLoop("sortDatesInCorrectOrderTOP")}


/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of sortDatesInCorrectOrder
**/
suspend fun  refrsortDatesInCorrectOrderTOP() : String {
    return  executeAndLoopRefreshing("refrsortDatesInCorrectOrderTOP")
        }

        /**
         *@description after all dates are sorted it will ,ark the first date as a date where we do not have a test and last one as such where we have a final
         *@return {string} string marking weather execution gone well
         */
        suspend fun  settingWhichTestIsFirstLastETCTOP() : String {
            return executeAndLoop("settingWhichTestIsFirstLastETCTOP")
        }

/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of settingWhichTestIsFirstLastETC
**/
suspend fun  refrsettingWhichTestIsFirstLastETCTOP() : String  {
    return  executeAndLoopRefreshing("refrsettingWhichTestIsFirstLastETCTOP")
        }


    }

    /********************coordinate creating annd modifying coursseworks **********************************************/
    inner class coordinateCreatingAndModdingAssingmnets : basicExecutionClass() {
        override val libraryCode: String
            get() = "M2PUYiEsCUf8d7dFezQOERcvMQXSGRMYb"
        /**     * @description : executes preparations to setting basic assignmnets setting proper tabs...     */
        suspend fun executePrepareToExecuteBasicAssignments() {
            executeAndLoop("executePrepareToExecuteBasicAssignments")
        }

        /**  * @description : execute set up of basic assignmnets (creates appropriate assignments in organizational and non organizational - normal classrooms)        */
        suspend fun executeSetUpBasicAssignments(): String {
            return executeAndLoop("executeSetUpBasicAssignments")
        }

        /**   *@description refreshing to enable next execution of createBasicCourseWork       */
        suspend fun refreshexecuteSetUpBasicAssignments(): String {
            return executeAndLoopRefreshing("refreshexecuteSetUpBasicAssignments")
        }

        /**
         * @description executes evrything that needs to be executed in order to add all needed data to non organizational classrooms
         */
        suspend fun executeAddDataToNonOrganizational(): String {
            return executeAndLoop("executeAddDataToNonOrganizational")
        }

        /**
         * @description executes evrything that needs to be executed in order to add all needed data to  organizational classrooms
         */
        suspend fun executeAddDataToOrganizational(): String {
            return executeAndLoop("executeAddDataToOrganizational")
        }

        /**
         *@description refreshing to enable next execution of seUpDataUfBesiAssignmentsToTable
         */
        suspend fun refrseUpDataUfBesiAssignmentsToTableTOP(): String {
            return executeAndLoopRefreshing("refrseUpDataUfBesiAssignmentsToTableTOP")
        }







        /*************************set marks *************************************/
        /**
         *@description adds needed tabs and titles to each tab in order to enable later work on them
         *@return {string} string marking weather execution gone well
         */
        suspend fun prepareFormListTop () : String {
            return executeAndLoop("prepareFormListTop")
        }


        /**
         *@description first gets forms from  folder taken from semSheet metadata for teacher folder checks weather the form has more than two responses if yes it appends it to appropriate sheet  with marking to which classroom it belongs
         *@return {string} string marking weather execution gone well
         */
        suspend fun getFilledFormsIdsToSheetTop() : String{
            return executeAndLoop("getFilledFormsIdsToSheetTop")
        }


        /**
         *@description alternative way to append  data about new forms - it bases not on already created and filled forms but adds completly new ones - those that are created on the
        basis of data from meetings dates spreadsheet
         *@return {string} string marking weather execution gone well
         */
        suspend fun getFormsIdsToSheetFromMeetingDatesTOP () : String{
            return executeAndLoop("getFormsIdsToSheetFromMeetingDatesTOP")}


/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of getFormsIdsToSheetFromMeetingDates
**/
suspend fun refrgetFormsIdsToSheetFromMeetingDates () : String {
                return executeAndLoop("refrgetFormsIdsToSheetFromMeetingDates")

            }


            /*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of getFilledFormsIdsToSheet
*@return {string} string marking weather execution gone well
**/
        suspend fun refrappendingDataFromBasicStudentDataFormsTop () : String{
            return executeAndLoopRefreshing("refrappendingDataFromBasicStudentDataFormsTop")
        }

        /**
         *@description adds position of each particular test that it would have in the formsList and created and adds data about coursework that is placeholder for marks in the
         *@return {string} string marking weather execution gone well
         */
        suspend fun addDataAboutPositionAndInSummaryMarksTableTop( ) : String{
            return executeAndLoop("addDataAboutPositionAndInSummaryMarksTableTop")
        }
/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of getFilledFormsIdsToSheet
*@return {string} string marking weather execution gone well
**/
        suspend fun refraddDataAboutPositionInSummaryMarksTableeTop () : String{
            return executeAndLoopRefreshing("refraddDataAboutPositionInSummaryMarksTableeTop")
        }

        /**
         *@description adds needed tabs and titles to each tab in order to enable later work on them
         *@return {string} string marking weather execution gone well
         */
        suspend fun prepareSubmissionIdsSprTop ()  : String{
            return executeAndLoop("prepareSubmissionIdsSprTop")
        }

        /**
         *@description getting most important data about each submission for each assignmnet connected with each test
         *@return {string} string marking weather execution gone well
         */
        suspend fun appendSubmissionIdsTop() : String{
            return executeAndLoop("appendSubmissionIdsTop")
        }

/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of refrappendSubmissionIds
*@return {string} string marking weather execution gone well
**/
        suspend fun refrappendSubmissionIdsTop ()  : String{
            return executeAndLoopRefreshing("refrappendSubmissionIdsTop")
        }

/*
*@description refreshing values (changing true to false in appropriate columns)
in order to enable one more execution of addMailAndLinkToSubmissionIds
*@return {string} string marking weather execution gone well
**/
        suspend fun refraddMailAndLinkToSubmissionIdsTOP () : String{
            return executeAndLoopRefreshing("refraddMailAndLinkToSubmissionIdsTOP")
        }


        /**
         *@description analyzes submissions of courseworks connected with done tests and adds grades based on from quizzes (google forms)
        and mail from basic student data tab in main semester sheet
         *@return {string} string marking weather execution gone well
         */
        suspend fun addMailAndLinkToSubmissionIdsTOP () : String{
            return executeAndLoop("addMailAndLinkToSubmissionIdsTOP")
        }


        /**
         *@description set mark and  doc if mail is set and there are more than 2 responses in test
        in the doc it will
         *@return {string} string marking weather execution gone well
         */
        suspend fun refrsetMarksAndDocsTOP( ) : String{
            return executeAndLoopRefreshing("refrsetMarksAndDocsTOP")
        }


        /**
         *@description set mark and  doc if mail is set and there are more than 2 responses in test
        in the doc it will
         *@return {string} string marking weather execution gone well
         */
        suspend fun  setMarksAndDocsTOP () : String{
            return  executeAndLoop("setMarksAndDocsTOP")
        }



/***************************  setting final marks etc  *****************************************/
        /**
         * @description adds needed tabs and titles to each tab in order to enable later work on them
         */
        suspend  fun prepareMarksSummarySpreadsheetTabsAndTitlesTop(): String {   return  executeAndLoop("prepareMarksSummarySpreadsheetTabsAndTitlesTop")}

        /**
         * @description apeends all user ids from each normal classrooms from assesment tasks basic data to summary marks
         */
        suspend  fun addAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryTOP(): String {   return  executeAndLoop("addAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryTOP")}

        /**
         * @description refreshes true false columns in order to enable execution of
         * addAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryT
         */
        suspend fun refrForaddAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryTOP(): String {   return  executeAndLoopRefreshing("refrForaddAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryTOP")}

        /**
         * @description takes basic data  from basic student data tab in main semester sheet about each user id and inserts it to summary spreadsheet
         */
        suspend  fun addBasicDataToSummaryMarksTOP(): String {   return  executeAndLoop("addBasicDataToSummaryMarksTOP")}

        /**
         * @description refreshes true false columns in order to enable execution of
         * addAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryT
         */
        suspend fun refrForaddBasicDataToSummaryMarksTOP(): String {   return  executeAndLoopRefreshing("refrForaddBasicDataToSummaryMarksTOP")}

        /**
         * @description adds data about scores from basic assesments like assesment tasks  and activity
         */
        suspend  fun addDataAoutBasicAssesmentsScoresToSummaryTOP(): String {   return  executeAndLoop("addDataAoutBasicAssesmentsScoresToSummaryTOP")}


        /**
         * @description refreshes true false columns in order to enable execution of
         * addAllUserIdsFromBasicAssesmentTasksSpreadsheetToMarksSummaryT
         */
        suspend   fun refraddDataAoutBasicAssesmentsScoresToSummaryTOP(): String {   return  executeAndLoopRefreshing("refraddDataAoutBasicAssesmentsScoresToSummaryTOP")}


        /**
         *@description adding info about score from tests including final one
         *@return {string} string marking weather execution gone well
         */
        suspend fun  setDataAboutTestMarksIntoSummaryMarksTOP (): String {   return  executeAndLoop("setDataAboutTestMarksIntoSummaryMarksTOP")
        }



        /**
         *@description refreshes true false columns in order to enable execution of
        setDataAboutTestMarksIntoSummaryMarksTOP
         */
        suspend fun  refrsetDataAboutTestMarksIntoSummaryMarksTOP(): String{   return  executeAndLoopRefreshing("refrsetDataAboutTestMarksIntoSummaryMarksTOP")
        }

        /**
         *@description processing summary marks table to set the percentage from small tests
         *@return {string} string marking weather execution gone well
         */
        suspend fun  proessDataOfSmallTestsInSummaryMarksiTOP () : String {   return  executeAndLoop("proessDataOfSmallTestsInSummaryMarksiTOP")
        }

        /**
         *@description refreshes true false columns in order to enable execution of
        proessDataOfSmallTestsInSummaryMarksiTOP
         */
        suspend fun  refrproessDataOfSmallTestsInSummaryMarksiTOP() : String{   return  executeAndLoopRefreshing("refrproessDataOfSmallTestsInSummaryMarksiTOP")
        }



        /**
         *@description processing summary marks table to set Flags And FinalMarks
         *@return {string} string marking weather execution gone well
         */
        suspend fun  setFlagsAndFinalMarksTOP () : String{   return  executeAndLoop("setFlagsAndFinalMarksTOP")
        }


        /**
         *@description refreshes true false columns in order to enable execution of
        setFlagsAndFinalMarksTOP
         */
        suspend fun  refrsetsetFlagsAndFinalMarksTOP() : String{   return  executeAndLoopRefreshing("refrsetsetFlagsAndFinalMarksTOP")
        }



        /**
         *@sends scores that we get by processing information from tests activity etc and sends it to appropriate classrooms
         *@return {string} string marking weather execution gone well
         */
        suspend fun  setScoresToBasicAssesmentLikeFlagTOP () : String {   return  executeAndLoop("setScoresToBasicAssesmentLikeFlagTOP")

        }
            /**
             *@description refreshes true false columns in order to enable execution of
            setScoresToBasicAssesmentLikeFlagTOP
             */
            suspend fun  refrsetScoresToBasicAssesmentLikeFlagTOP( ) : String{   return  executeAndLoopRefreshing("refrsetScoresToBasicAssesmentLikeFlagTOP")

            }





    }
}




