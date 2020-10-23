package functionalUnits

import mainFunction.*
import org.openqa.selenium.Keys
import java.text.SimpleDateFormat
import java.util.*


/**
 * creating subject and adding appropriate students groups to it
 * */
class createSubject(title: String, startDate: Date, endDate: Date) : Section(
        {
            val format = SimpleDateFormat("yyyy-MM-dd")

            val subsection1 = SubSection(
                    listOf(
                            Step.openWebpage("https://wssp.eduportal.pl/Lms/SciezkaSzkoleniowa/SciezkiSzkoleniowe"),
                            Step.click(selectingString = ".ibox-control > button:nth-child(1)", checking = Checking.dummy()),
                            Step.specialFunct({ driver -> Thread.sleep(6000) })

                            ,Step.sendKeys(selectingString = "#Nazwa", inputText = title),
                            Step.sendKeys(selectingString = "#DataOd", inputText = format.format(startDate)),
                            Step.sendKeys(selectingString = "#DataDo", inputText =format.format(endDate) + Keys.ESCAPE),
                            Step.click(selectingString = ".form-actions > button:nth-child(1)", checking = Checking.dummy())

                    ), Repair.getToSomeParticularSide("https://wssp.eduportal.pl/Lms/SciezkaSzkoleniowa/SciezkiSzkoleniowe"), preCheck = PreCheck.isThereSuchText(title)
            )

            listOf<SubSection>(subsection1)
        }.invoke())






