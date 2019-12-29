package com.hexl.basemvvm.ui

interface IBase {
    fun createLayout():Int

    fun init()

    fun onRequest()
}

interface IActivity : IBase {
}

interface IFragment : IBase {

}