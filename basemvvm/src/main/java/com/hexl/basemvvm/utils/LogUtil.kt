package com.hexl.basemvvm.utils

import android.util.Log

/**
 * @desc
 * @author Hexl
 * @date 2019/11/13
 */
object LogUtil {
    var DEBUG = true
    var TAG = "LogUtil"

    fun d(message: String) {
        if (DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun d(tag: String, message: String) {
        if (DEBUG) {
            Log.e(tag, message)
        }
    }

    fun e(message: String) {
        if (DEBUG) {
            Log.e(TAG, message)
        }
    }

    fun e(tag: String, message: String) {
        if (DEBUG) {
            Log.e(tag, message)
        }
    }

    fun i(message: String) {
        if (DEBUG) {
            Log.i(TAG, message)
        }
    }

    fun i(tag: String, message: String) {
        if (DEBUG) {
            Log.i(tag, message)
        }
    }

}