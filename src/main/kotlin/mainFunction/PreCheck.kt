package mainFunction


/**
 * will pass information is submodule to get any precheck - checking weather an action should be performed
 * so if it will fail or of the subsequent SubModules will not be invoked until there will be ossurence of non dummy precheck that will be passed
 * */
sealed class PreCheck {
    class dummy () : PreCheck()

    /**checking weather given text is present in source html*/
    class isThereSuchText (val textToSeek : String): PreCheck()
    /**resetting and enabling futher execution*/
    class resetPreCheck (): PreCheck()
    /**
     * is text occuring given amount of instances
     * */
      class isThereSuchTextTimes (val textToSeek : String,val  times : Int): PreCheck()

}