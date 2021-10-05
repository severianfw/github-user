package com.severianfw.githubuser.viewmodel

import com.severianfw.githubuser.database.SettingPreferences
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class SettingsViewModelTest {

    private lateinit var settingPreferences: SettingPreferences
    private lateinit var settingsViewModel: SettingsViewModel

    private val dummyIsDarkModeActive: Boolean = false

    @Before
    fun setUp() {
        settingPreferences = mock(SettingPreferences::class.java)
        settingsViewModel = SettingsViewModel(settingPreferences)
    }

    @Test
    fun saveThemeSetting() {
        settingsViewModel.saveThemeSetting(dummyIsDarkModeActive)
    }
}