package utils

import com.google.common.collect.Range
import org.openqa.selenium.Keys
import java.lang.reflect.Array

fun genereteStringWithNumberOfTabs (tabsNumb : Int) = (1..tabsNumb).map { Keys.TAB }.joinToString("")

fun genereteStringWithNumberOfBackspaces (keysNum : Int) = (1..keysNum).map { Keys.BACK_SPACE }.joinToString("")