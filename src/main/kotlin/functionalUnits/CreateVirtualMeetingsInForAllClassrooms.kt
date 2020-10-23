package functionalUnits

import kotlinx.datetime.LocalDateTime
import mainFunction.Section
import mainFunction.SubSection


/**
 * on AssecoEdu portal it will create virtual meetings on preset date and hour
 * */
class CreateVirtualMeetingsInForAllClassrooms(val classroomTitle: String,
                                              val meeteingsDataList: List<infoFrorInterActiveMeetingCreation?>) : Section(
{    listOf<SubSection>( getIntoSubject(classroomTitle), *meeteingsDataList.map { meetingDat ->
        // creating big blue button meeting for each time we meet
        listOf(createVideoConference("${meetingDat!!.startTime} $classroomTitle", startAndEndDateTime(meetingDat.startTime,meetingDat.endTime))
        )
    }.flatten().toTypedArray())

}.invoke())

data class infoFrorInterActiveMeetingCreation (val subModulesTitles : List<String>, val startTime : LocalDateTime, val endTime : LocalDateTime,
                                               val linkOfSummarizingSpreadsheet : String)

