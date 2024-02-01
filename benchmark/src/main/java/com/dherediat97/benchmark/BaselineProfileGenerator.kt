package com.dherediat97.benchmark

import android.os.Build
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @RequiresApi(Build.VERSION_CODES.P)
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @RequiresApi(Build.VERSION_CODES.P)
    @Test
    fun appStartupAndUserJourneys() {
        baselineProfileRule.collect(
            packageName = "com.dherediat97.oompaloompaapp"
        ) {
            // App startup journey.
            startActivityAndWait()
            Thread.sleep(5000)

            device.findObject(By.res("oompaLoompaList")).also {
                it.fling(Direction.DOWN)
                it.fling(Direction.UP)
            }
            device.pressBack()
        }
    }
}