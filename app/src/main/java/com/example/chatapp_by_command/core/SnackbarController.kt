package com.example.chatapp_by_command.core

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {
    private var snackbarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackbar(snackbarHostState: SnackbarHostState, message: String, actionLabel: String?){
        if(snackbarJob == null){
            snackbarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
            }
            snackbarJob!!.invokeOnCompletion {
                cancelActiveJob()
            }
        }else{
            cancelActiveJob()
            snackbarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
            }
            snackbarJob!!.invokeOnCompletion {
                cancelActiveJob()
            }
        }
    }

    private fun cancelActiveJob(){
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}