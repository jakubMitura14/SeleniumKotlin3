package functionalUnits.Grader

/** keeps data about the student like name surname id  faculty semester it will be taken from appropriate table from google spreadsheet*/
data class StudentDat (val name : String, val surname : String, val id : Int , val faculty : faculties , val semester : Int)

/**
 * get data about  student data from  spreadsheet that is related to the form where students put data
 */
fun  getStudentData () {}

enum class faculties(){
    nursing,
    physiotheraphy,
    sportScience,
    cosmetology


}