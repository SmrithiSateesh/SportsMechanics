package com.example.smrithi.sportsmechanics.activity

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxSearchObservable {

    fun fromView(searchView: EditText): Observable<String> {

        val subject = PublishSubject.create<String>()


        searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                subject.onNext(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                subject.onNext(s.toString())
            }

        })
        /*searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                subject.onNext(text)
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })*/
        return subject
    }
}