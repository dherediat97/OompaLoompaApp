package com.dherediat97.oompaloompaapp

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.dherediat97.oompaloompaapp.presentation.MainActivity
import com.dherediat97.oompaloompaapp.presentation.composable.MyNavHost
import com.dherediat97.oompaloompaapp.presentation.composable.OompaLoompaList
import com.dherediat97.oompaloompaapp.presentation.composable.SearchBarFilterOompaLoompa
import com.dherediat97.oompaloompaapp.presentation.theme.OompaLoompaAppTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class TestsUnitCaseApp {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    /**
     * Test to verify the arraylist of the lazy column
     */
    @Test
    fun listTest() {
        composeTestRule.activity.setContent {
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    SearchBarFilterOompaLoompa(onClearFilters = {}, content = {
                        OompaLoompaList(innerPadding = paddingValues, onNavigateOompaLoompa = {})
                    })
                }
            }
        }
        composeTestRule.waitForIdle()
        val oompaLoompaList = composeTestRule.onNodeWithTag("oompaLoompaList")
        assertTrue(oompaLoompaList.isDisplayed())
        oompaLoompaList.performScrollToIndex(9)
        composeTestRule.waitForIdle()
    }


    /**
     * Test to verify the details of Oompa Loompa
     */
    @Test
    fun detailTest() {
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            OompaLoompaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavHost()
                    Scaffold { paddingValues ->
                        SearchBarFilterOompaLoompa(onClearFilters = {}, content = {
                            OompaLoompaList(
                                innerPadding = paddingValues,
                                onNavigateOompaLoompa = { id ->
                                    navController.navigate(id)
                                })
                        })
                    }
                }
            }
        }
        composeTestRule.waitForIdle()
        val oompaLoompaList = composeTestRule.onNodeWithTag("oompaLoompaList")
        oompaLoompaList.performScrollToIndex(9).onChildAt(9).performClick()

        composeTestRule.waitForIdle()
        Thread.sleep(5000)
    }


    /**
     * Test to check the filter
     */
    @Test
    fun filterTest() {
        composeTestRule.activity.setContent {
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    SearchBarFilterOompaLoompa(onClearFilters = {}, content = {
                        OompaLoompaList(
                            innerPadding = paddingValues,
                            onNavigateOompaLoompa = {})
                    })
                }
            }
        }
        composeTestRule.waitForIdle()
        val oompaLoompaSearcher = composeTestRule.onNodeWithTag("searchBarFilter")
        oompaLoompaSearcher.performClick()
        oompaLoompaSearcher.performTextInput("marcy")

        composeTestRule.waitForIdle()
        Thread.sleep(5000)
    }
}