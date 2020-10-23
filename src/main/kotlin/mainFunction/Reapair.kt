package mainFunction

/**
 * will hold information what to do in case of failure of subsection
 * */
sealed class Repair (){
    /** will just pass information to refresh current website and start over the execution of subsection*/
    class    refreshSiteAndStartOver() :Repair() {}
    /**
     * gets to some particular side
     * */
    class getToSomeParticularSide(val siteUrl : String) :Repair()  {}

}