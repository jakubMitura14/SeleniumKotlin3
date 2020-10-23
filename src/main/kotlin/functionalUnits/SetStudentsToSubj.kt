package functionalUnits

import mainFunction.*
import org.openqa.selenium.Keys
import utils.genereteStringWithNumberOfBackspaces
import utils.genereteStringWithNumberOfTabs

/**
 * creating subject and adding appropriate students groups to it
 * */
class createSubjectAndSetStudents(title : String, groupsList: List<String>) : Section(
        {
            val subsection1 = SubSection(listOf(
                    Step.openWebpage("https://wssp.eduportal.pl/Lms/SciezkaSzkoleniowa/SciezkiSzkoleniowe"),
                    Step.click(selectingString = title, selectMet = SelectorMethods.byText(title),checking = Checking.dummy()),
                    Step.click(selectingString = ".action-menu-group > div:nth-child(1) > a:nth-child(2)", checking = Checking.dummy()),
                    Step.click(selectingString = ".zmianaWidokuZasobButton", checking = Checking.dummy()),


            ), Repair.refreshSiteAndStartOver())

            fun selectGroupAndAdd (groupName : String) = SubSection(listOf(

                            Step.sendKeys(selectingString = "#GrupyNieprzypisaneSearch",
                                    genereteStringWithNumberOfBackspaces(40)
                    //+ genereteStringWithNumberOfTabs(2) + Keys.SPACE
            ),
                    Step.specialFunct({ driver -> Thread.sleep(5000) }),
                    Step.sendKeys(selectingString = "#GrupyNieprzypisaneSearch",
                            groupName
                                    //+ genereteStringWithNumberOfTabs(2) + Keys.SPACE
                    ),
                    Step.specialFunct({ driver -> Thread.sleep(5000) }),

                    Step.click(selectingString = "tr.tr-draggable:nth-child(1) > td:nth-child(1) > div:nth-child(1) > label:nth-child(2) > div:nth-child(1) > ins:nth-child(2)", checking = Checking.dummy()),
                    Step.click(selectingString = "#BtnPrzypiszGrupy", checking = Checking.dummy()),
                    Step.sendKeysToDummy(Keys.ENTER.toString(), 5),
              //      Step.specialFunct({ driver -> Thread.sleep(5000) })
                    Step.navigation(perform = Perform.refreshWebsite())

                    ), Repair.refreshSiteAndStartOver())

            listOf<SubSection>(subsection1, *groupsList.filter { it.trim().length>4 }.map { selectGroupAndAdd(it) }.toTypedArray())}.invoke() )

//e - 1 Fizt