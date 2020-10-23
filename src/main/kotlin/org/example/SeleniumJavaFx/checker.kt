package org.example.SeleniumJavaFx

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * if no action will be performed for period longer than specified it will restart the program
 * */
object Checker {

    private var now = Clock.System.now()

    val act = GlobalScope.actor<Instant> {
        consumeEach { now=it

        }

    }

    init {
        GlobalScope.launch {
            while(true){
           val  begin = now
            delay(300000)
            if (begin == now){
                println("need to reset")
                //action to reet if nothing happens
                MainFunction.driver.quit()
                MainFunction.driver = MainFunction.prepareDriver()
                ForStart.mainInStart()
            }}
        }
    }


}