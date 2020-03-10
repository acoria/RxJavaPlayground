package com.example.rxjavaplayground

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

fun testJustWithString() {
    Observable.just("Hi there")
        .subscribe { v -> println(v) }
}
fun testJustWithCollection(){
    Observable.fromIterable(listOf(1,2,3))
        .subscribe { println("number is: $it") }
}

fun justReturnsSingleValue(): Observable<Int>{
    return Observable.just(10)
}

fun justReturnCollection(): Observable<Int> {
    return Observable.fromIterable(listOf(1,2,3))
}
private fun singleReturn() {
    val single: Single<Int> = justReturnsSingleValue().singleOrError()
    single
        .subscribeBy(
            onSuccess = { println(it) },
            onError = { println(it.message) }
        )
}

private fun subscribingInKotlin() {
    Observable.fromIterable(listOf(1, 2, 3))
        .subscribeBy(
            onNext = { println("number was $it") },
            onComplete = { println("I completed") },
            onError = { error -> println(error.message) }
        )
}
fun multipleReturnToSingle() {
    justReturnCollection().singleOrError()
        .subscribeBy(
            onError = { println("error: ${it.message}") },
            onSuccess = { println(it)}
        )
}

fun testToList(){

    val listObservable = Observable.fromIterable(listOf(1,2,3))
//    listObservable
//        .subscribeBy(
//            onNext = { println("without toList(): $it")}
//        )
//    listObservable
//        .toList()
//        .subscribeBy(
//            onSuccess = {println("with toList(): $it")}
//        )


}
val myPrintlnFunction = { x:Int -> println(x) }

fun main() {
//    testJustWithString()
//    testJustWithCollection()
//    println(calculator(4,2, multiplication))
//    subscribingInKotlin()
//    singleReturn()
//    multipleReturnToSingle()
//    testToList()
}

fun multiply(x: Int, y: Int): Int{
    return x * y
}

val multiplication = {x: Int, y: Int -> x * y}
fun calculator(numberX: Int, numberY: Int, operation: (x: Int, y: Int) -> Int): Int{
    return operation(numberX, numberY)
}

fun add2(doXTimes:Int, number: Int){
    var result: Int = number
    repeat(doXTimes){
        result +=2
    }
    println(result)
}

