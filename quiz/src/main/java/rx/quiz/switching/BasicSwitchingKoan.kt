@file:Suppress("FunctionName")

package rx.quiz.switching

import io.reactivex.Single
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import rx.quiz.switching.BasicSwitchingKoan.LocationApi

/**
 * # Task
 * Using [LocationApi], query [LocationApi.pinpointLocation]
 *
 * ## Detailed description
 * You are given an API, which you can use to query information about the [LocationApi.pinpointLocation].
 * Unfortunately, pinpoint location can only be queried if [LocationApi.fineLocation] is known. [LocationApi.pinpointLocation]
 * in turn requires [LocationApi.coarseLocation].
 *
 * ## Requires
 * * Basic knowledge about switching operators
 */
abstract class BasicSwitchingKoan {
    /**
     * An API for the test. See [BasicSwitchingKoan] description.
     */
    interface LocationApi {
        /**
         * Queries coarse location, e.g. city
         */
        fun coarseLocation(): Single<String>

        /**
         * Queries fine location, e.g. zip code
         * @param coarseLocation value of the previously called [LocationApi.coarseLocation]
         */
        fun fineLocation(coarseLocation: String): Single<String>

        /**
         * Queries fine location, e.g. zip code
         * @param fineLocation value of the previously called [LocationApi.fineLocation]
         */
        fun pinpointLocation(fineLocation: String): Single<Int>
    }

    /**
     * @see [BasicSwitchingKoan]
     * @param locationApi An API instance which must be used to implement the task.
    */
    abstract fun queryPinpointLocation(locationApi: LocationApi): Single<Int>

    @Test
    fun `Use operators to implement an async workflow`() {

        fun Number.toZip() = when (this) {
            0 -> "90425"
            1 -> "80333"
            else -> "91058"
        }

        fun String.toCity() = when (this) {
            "90425" -> "Nürnberg"
            "80333" -> "München"
            else -> "Erlangen"
        }

        val number = Math.random().times(3).toInt().rem(3)

        val result = queryPinpointLocation(
                object : LocationApi {
                    override fun coarseLocation() = Single.fromCallable { number.toZip().toCity() }

                    override fun fineLocation(coarseLocation: String) = Single.fromCallable {
                        when (coarseLocation) {
                            "Nürnberg" -> "90425"
                            "München" -> "80333"
                            else -> "91058"
                        }
                    }

                    override fun pinpointLocation(fineLocation: String) = when (fineLocation) {
                        "90425" -> Single.just(0)
                        "80333" -> Single.just(1)
                        else -> Single.just(2)
                    }
                }
        )
        assertThat(result.test().values()[0])
                .describedAs("Pinpoint location")
                .isEqualTo(number)
    }
}
