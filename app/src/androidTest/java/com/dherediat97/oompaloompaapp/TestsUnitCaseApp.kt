package com.dherediat97.oompaloompaapp

import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertValueEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import com.dherediat97.oompaloompaapp.di.networkModule
import com.dherediat97.oompaloompaapp.di.repositoryModule
import com.dherediat97.oompaloompaapp.domain.repository.OompaLoompaListRepository
import com.dherediat97.oompaloompaapp.presentation.MainActivity
import com.dherediat97.oompaloompaapp.presentation.composable.OompaLoompaDetail
import com.dherediat97.oompaloompaapp.presentation.composable.OompaLoompaList
import com.dherediat97.oompaloompaapp.presentation.composable.SearchBarFilterOompaLoompa
import com.dherediat97.oompaloompaapp.presentation.theme.OompaLoompaAppTheme
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertNotNull


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestsUnitCaseApp : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    val modules = listOf(networkModule, repositoryModule)

    private val oompaLoompaListRepository: OompaLoompaListRepository by inject()

    @Before
    fun init() {
        loadKoinModules(modules)
    }


    @Test
    fun checkRepositoryCorrectResults() = runTest {
        val responseGetAllOompaLoompa = oompaLoompaListRepository.fetchAllOompaLoompa(1)
        assertNotNull(responseGetAllOompaLoompa)
        assertTrue(responseGetAllOompaLoompa.results.isNotEmpty())
    }


    /**
     * There are 500 results 20 per page, so 25 pages,
     * if get the page 25 and next pages, will be the same results
     */
    @Test
    fun checkRepositoryInvalidResults() = runTest {
        val responseGetAllOompaLoompaMaxPage =
            oompaLoompaListRepository.fetchAllOompaLoompa(25)
        val responseGetAllOompaLoompaPageInvalid =
            oompaLoompaListRepository.fetchAllOompaLoompa(26)
        assertNotNull(responseGetAllOompaLoompaMaxPage)
        assertNotNull(responseGetAllOompaLoompaPageInvalid)
        assertTrue(responseGetAllOompaLoompaPageInvalid.results.isNotEmpty())
        assertTrue(responseGetAllOompaLoompaPageInvalid.results.isNotEmpty())
        assertTrue(
            responseGetAllOompaLoompaPageInvalid.results[0] == responseGetAllOompaLoompaMaxPage.results[0]
        )
    }

    /**
     * Test to verify the arraylist of the lazy column
     */
    @Test
    fun listScrollTest() {
        composeTestRule.activity.setContent {
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    SearchBarFilterOompaLoompa(
                        onClearFilters = {},
                        innerPadding = paddingValues,
                        content = {
                            OompaLoompaList(
                                innerPadding = paddingValues,
                                onNavigateOompaLoompa = {})
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
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    OompaLoompaDetail(innerPadding = paddingValues, oompaLoompaId = 9)
                }
            }
            val oompaLoompaName = composeTestRule.onNodeWithTag("oompaLoompaName")
            oompaLoompaName.assertExists()
            oompaLoompaName.assertValueEquals("Jesselyn Flasby")

            composeTestRule.waitForIdle()
        }
    }


    /**
     * Test to check the filter
     */
    @Test
    fun filterTest() {
        composeTestRule.activity.setContent {
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    SearchBarFilterOompaLoompa(onClearFilters = {},
                        innerPadding = paddingValues,
                        content = {
                            OompaLoompaList(innerPadding = paddingValues,
                                onNavigateOompaLoompa = {})
                        })
                }
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("searchBarFilter").performTextInput("marcy")
        composeTestRule.onNodeWithTag("searchBarFilter").assert(hasText("marcy"))

        composeTestRule.waitForIdle()
    }

    @After
    fun destroy() {
        unloadKoinModules(modules)
    }
}