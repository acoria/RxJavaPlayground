package com.example.rxjavaplayground

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.TestScheduler
import kotlin.concurrent.schedule
import java.util.Timer
import java.util.Random
import java.util.concurrent.TimeUnit


val list = Observable.fromIterable(listOf(1, 2, 3))

fun reduceOp() {
    list
        .reduce { x, y -> x + y }
        .subscribeBy { println("reduced: $it") }
}

fun filterOp() {
    list
        .filter { it % 2 == 0 }
        .subscribeBy { println("filter uneven: $it") }
}

fun takeOp() {
    list
        .take(4)
        .subscribeBy { println("take: $it") }
}

fun takeUntilOp() {
    list
        .takeUntil { it == 3 }
        .subscribeBy(
            onNext = { println("takeUntil: $it") }
        )
}

val testScheduler = TestScheduler()

fun testMapInsteadOfFlatMap() {
    //map does not wait for asynchronous calls to return
    Observable.fromIterable('A'..'D')
        .map {
            addNextCharWithDelay(it)
        }
        .toList()
        .subscribeBy { println("ListOfChars: $it") }
    testScheduler.advanceTimeBy(1, TimeUnit.MINUTES)
}


fun flatMapOp() {
    Observable.fromIterable('A'..'D')
        .flatMap { addNextChar(it) }
        .toList()
        .subscribeBy { println("ListOfChars: $it") }

    testScheduler.advanceTimeBy(1, TimeUnit.MINUTES)
}

fun addNextCharWithDelay(c: Char): String {
    var newResult = ""
    val delay: Long = Random().nextInt(10).toLong()
    val newChar = c + 1
    Observable.just(c.toString() + newChar)
        .delay(delay, TimeUnit.SECONDS, testScheduler)
        .subscribeBy(
            onNext = { newResult = it })
    return newResult
}

fun addNextChar(c: Char): Observable<String> {
    val delay: Long = Random().nextInt(10).toLong()
    val newChar = c + 1

    return Observable.just(c.toString() + newChar)
        .delay(delay, TimeUnit.SECONDS, testScheduler)
}

fun main() {
    testMapInsteadOfFlatMap()
//    flatMapOp()
//    reduceOp()
//    takeOp()
//    takeUntilOp()
//    filterOp()
}