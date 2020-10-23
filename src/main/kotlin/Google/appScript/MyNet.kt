package appScript

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.lang.Exception
import java.util.*
import kotlin.random.asKotlinRandom

/**some of the network functions*/
object MyNet {
    var random = GlobalScope.async { java.util.Random().asKotlinRandom() }
    /**catches network errors and repats the funcions after some delay*/
    suspend fun err (functName:String, netFunct :suspend ()->Unit) {

        //  repeatAfterSetTimeIfNoMoreThan (1000, 60*1000*15 ,functName,netFunct)
        try {
            delay(10000 + random.await().nextLong(1000,3000))
            netFunct.invoke()
        }catch (e: Exception) {
            println(" error catchedin $functName \n ${e.stackTrace.map { it.toString() }}")
            delay(9000)
          try{ netFunct.invoke()} catch (e: Exception){
              delay(30000+ random.await().nextLong(8000,38000))
             try {
                 delay (30000+ random.await().nextLong(10000,38000))
              netFunct.invoke()
          } catch (e: Exception) {
                 println(" error catchedin $functName \n ${e.stackTrace}")
                 try{
                     delay (110000+ random.await().nextLong(10000,99000))
                     netFunct.invoke()
                 }catch (e: Exception){
                     println(" error catchedin $functName \n ${e.stackTrace}")
                  //   netFunct.invoke()

                     err(functName,netFunct )
                 }
             }

          }
        }
    }

    /**
     * @descritpion repeats function after set amount of time if this time does not exceeds maximum time  will be invoked recursively until  function will succesfully run or the max time will be reached
     * @param functionToInvoke {()-> UNIT} main function that we are invoking here
     * @param time {INT} after so many seconds there would be invocation of the function after evry iteration it double the amount of time it will wait
     * @param maxTime {INT} we well not invoke function one more time if the time will exceed this time
     * @param functionName{String} name of the function passed to enable better logging
     * */
    suspend fun repeatAfterSetTimeIfNoMoreThan (time:Long, maxTime :Long, functionName: String,functionToInvoke :suspend ()->Unit)  {
        delay(time)
         if(time<maxTime){
             try{
                 functionToInvoke()} catch (error: Error) {
                 println(functionName+ error.message)
                 repeatAfterSetTimeIfNoMoreThan(time*2,maxTime,functionName,functionToInvoke)
             }
        }

    }

}