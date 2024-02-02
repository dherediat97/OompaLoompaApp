package com.dherediat97.oompaloompaapp

import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertValueEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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

    private val modules = listOf(networkModule, repositoryModule)

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
        assertTrue(responseGetAllOompaLoompaMaxPage.results.isNotEmpty())
        assertTrue(responseGetAllOompaLoompaPageInvalid.results.isNotEmpty())
        assertTrue(
            responseGetAllOompaLoompaMaxPage.results[0] == responseGetAllOompaLoompaPageInvalid.results[0]
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
                        dataListView = {
                            OompaLoompaList(
                                innerPadding = paddingValues
                            ) {}
                        })
                }
            }
        }

        composeTestRule.onNodeWithTag("oompaLoompaList")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.waitUntilExists(hasTestTag("oompaLoompaList"))
        val oompaLoompaList = composeTestRule.onNodeWithTag("oompaLoompaList")
        oompaLoompaList.performScrollToIndex(9)
        composeTestRule.waitForIdle()
    }


    @Test
    fun detailTest() {
        composeTestRule.activity.setContent {
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    OompaLoompaDetail(innerPadding = paddingValues, oompaLoompaId = 9)
                }
            }
            composeTestRule.onNodeWithTag("oompaLoompaName")
                .assertIsDisplayed()
                .performClick()
                .assertIsOn()


            composeTestRule.waitUntilExists(hasTestTag("oompaLoompaName"))
            composeTestRule.onNodeWithTag("oompaLoompaName").assertExists()
            composeTestRule.onNodeWithTag("oompaLoompaName").assertValueEquals("Jesselyn Flasby")

            composeTestRule.onNodeWithTag("oompaLoompaProfession").assertExists()
            composeTestRule.onNodeWithTag("oompaLoompaProfession").assertValueEquals("Developer")

            composeTestRule.onNodeWithTag("oompaLoompaGender").assertExists()
            composeTestRule.onNodeWithTag("oompaLoompaGender").assertValueEquals("Female")

            composeTestRule.waitForIdle()
        }
    }


    /**
     * Strange success :(
     */
    @Test
    fun detailTestInvalid() {
        composeTestRule.activity.setContent {
            OompaLoompaAppTheme {
                Scaffold { paddingValues ->
                    OompaLoompaDetail(innerPadding = paddingValues, oompaLoompaId = 2)
                }
            }
            composeTestRule.onNodeWithTag("oompaLoompaName")
                .assertIsDisplayed()
                .performClick()
                .assertIsOn()


            composeTestRule.waitUntilExists(hasTestTag("oompaLoompaName"))
            composeTestRule.onNodeWithTag("oompaLoompaName").assertExists()
            composeTestRule.onNodeWithTag("oompaLoompaName").assertValueEquals("Jesselyn Flasby")

            composeTestRule.onNodeWithTag("oompaLoompaProfession").assertExists()
            composeTestRule.onNodeWithTag("oompaLoompaProfession").assertValueEquals("Developer")

            composeTestRule.onNodeWithTag("oompaLoompaGender").assertExists()
            composeTestRule.onNodeWithTag("oompaLoompaGender").assertValueEquals("Female")

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
                        dataListView = {
                            OompaLoompaList(innerPadding = paddingValues
                            ) {}
                        })
                }
            }
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithTag("searchBarFilter")
                .assertIsDisplayed()
                .performClick()
                .assertIsOn()

            composeTestRule.waitUntilExists(hasTestTag("searchBarFilter"))
            composeTestRule.onNodeWithTag("searchBarFilter").performTextInput("marcy")
            composeTestRule.onNodeWithTag("searchBarFilter").assert(hasText("marcy"))


            composeTestRule.onNodeWithTag("oompaLoompaList")
                .assertIsDisplayed()
                .assertIsOff()
                .performClick()
                .assertIsOn()

            composeTestRule.waitUntilExists(hasTestTag("oompaLoompaList"))
            composeTestRule.onNodeWithTag("oompaLoompaList").onChildAt(0).assertExists()
        }

    }

    @After
    fun destroy() {
        unloadKoinModules(modules)
    }
}

private const val WAIT_UNTIL_TIMEOUT = 10_000L
fun ComposeContentTestRule.waitUntilNodeSize(
    matcher: SemanticsMatcher,
    count: Int,
    timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
) {
    waitUntil(timeoutMillis) {
        onAllNodes(matcher).fetchSemanticsNodes().size == count
    }
}

fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
) = waitUntilNodeSize(matcher, 1, timeoutMillis)

fun ComposeContentTestRule.waitUntilDoesNotExist(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = WAIT_UNTIL_TIMEOUT
) = waitUntilNodeSize(matcher, 0, timeoutMillis)
