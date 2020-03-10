package com.example.rxjavaplayground

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule


data class User(val id: Int)

val pubSubject: PublishSubject<User> = PublishSubject.create()
val printMyUser = { user: User -> println(user)}
val printMyInt = { number: Int -> println(number)}


fun main() {

    Timer("MyTimer", false).schedule( 1000){
        pubSubject.onNext(User(1))
    }

    Observable.just(1)
        .delay(1, TimeUnit.SECONDS)
        .subscribeBy(
            onNext = {pubSubject.onNext(User(7))}
        )

    pubSubject.subscribeBy { printMyUser(it) }
}

