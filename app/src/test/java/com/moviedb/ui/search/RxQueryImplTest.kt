package com.moviedb.ui.search

import com.moviedb.RxSchedulersTest
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit


class RxQueryImplTest {

    @Test
    fun oneCharQueriesNotObserved() {
        RxQueryScenarios().run {
            given {
                querySubscribed()
            }
            whenever {
                query("a")
                advanceTime(550)
            }
            then {
                subscriberHasNoValues()
            }
        }
    }

    @Test
    fun queryWhiteSpaceRemoved() {
        RxQueryScenarios().run {
            given {
                querySubscribed()
            }
            whenever {
                query("   abc   ")
                advanceTime(550)
            }
            then {
                subscriberQueryAt(0, "abc")
            }
        }
    }

    @Test
    fun querySequenceObservedRespectingTimeout() {
        RxQueryScenarios().run {
            given {
                querySubscribed()
            }
            whenever {
                query("abc")
                advanceTime(550)
                query("def")
                advanceTime(500)
                query("abc")
                advanceTime(300)
                query("abcdef")
                advanceTime(550)
            }
            then {
                subscriberQueryAt(0, "abc")
                subscriberQueryAt(1, "def")
                subscriberQueryAt(2, "abcdef")
            }
        }
    }
}

class RxQueryScenarios {

    private val testScheduler = TestScheduler()
    private val schedulers = RxSchedulersTest(testScheduler)
    private val publisher = PublishSubject.create<CharSequence>()
    private val rxQuery = RxQueryImpl(schedulers)
    lateinit var subscriber: TestSubscriber<String>
    private val queries = mutableListOf<String>()

    fun given(func: Given.() -> Unit) = Given().apply(func)

    fun whenever(func: Whenever.() -> Unit) = Whenever().apply(func)

    fun then(func: Then.() -> Unit) = Then().apply(func)


    inner class Given {
        fun querySubscribed() {
            subscriber = rxQuery.asFlowable(publisher).test()
        }
    }

    inner class Whenever {

        fun query(query: CharSequence) {
            queries.add(query.toString())
            publisher.onNext(query)
        }

        fun advanceTime(time: Long) {
            testScheduler.advanceTimeBy(time, TimeUnit.MILLISECONDS)
        }
    }

    inner class Then {

        fun subscriberHasNoValues() {
            subscriber.assertNoValues()
        }

        fun subscriberQueryAt(position: Int, query: String) {
            assertEquals(query, subscriber.values()[position])
        }
    }
}