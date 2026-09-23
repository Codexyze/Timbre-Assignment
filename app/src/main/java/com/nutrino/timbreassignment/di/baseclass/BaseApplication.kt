package com.nutrino.timbreassignment.di.baseclass

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base [Application] class for the Timbre Assignment app.
 *
 * Annotated with [@HiltAndroidApp] to trigger Hilt's code generation and set up
 * the application-level dependency injection container.
 */
@HiltAndroidApp
class BaseApplication : Application()
