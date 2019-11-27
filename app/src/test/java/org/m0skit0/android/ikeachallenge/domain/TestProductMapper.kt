package org.m0skit0.android.ikeachallenge.domain

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.toOption
import io.kotlintest.assertions.arrow.option.shouldBeSome
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.data.api.ProductDto
import org.m0skit0.android.ikeachallenge.shouldNotBeCalled

class TestProductMapper : FreeSpec() {
    init {

        "when map with correct values should return correct ChairInfo" {
            val chairInfo = mapOf("material" to "wood with cover", "color" to "white").toChairInfo()
            with (chairInfo) {
                shouldBeSome()
                getOrElse { null }?.material shouldBe Option.just("wood with cover")
                getOrElse { null }?.color shouldBe Option.just("white")
            }

        }

        "when map with no values should return correct ChairInfo" {
            val chairInfo = mapOf<String, String>().toChairInfo()
            with (chairInfo) {
                shouldBeSome()
                getOrElse { null }?.material shouldBe Option.empty()
                getOrElse { null }?.color shouldBe Option.empty()
            }
        }

        "when map with some wrong should return correct ChairInfo" {
            val chairInfo = mapOf("numberOfSeats" to "2", "color" to "white").toChairInfo()
            with (chairInfo) {
                shouldBeSome()
                getOrElse { null }?.material shouldBe Option.empty()
                getOrElse { null }?.color shouldBe Option.just("white")
            }
        }

        "when map with all wrong values should return correct ChairInfo" {
            val chairInfo = mapOf("numberOfSeats" to "2", "foo" to "bar").toChairInfo()
            with (chairInfo) {
                shouldBeSome()
                getOrElse { null }?.material shouldBe Option.empty()
                getOrElse { null }?.color shouldBe Option.empty()
            }
        }

        "when map with correct values should return correct CouchInfo" {
            val couchInfo = mapOf("numberOfSeats" to "2", "color" to "white").toCouchInfo()
            with (couchInfo) {
                shouldBeSome()
                getOrElse { null }?.numberOfSeats shouldBe Option.just("2")
                getOrElse { null }?.color shouldBe Option.just("white")
            }
        }

        "when map with no values should return correct CouchInfo" {
            val couchInfo = mapOf<String, String>().toCouchInfo()
            with (couchInfo) {
                shouldBeSome()
                getOrElse { null }?.numberOfSeats shouldBe Option.empty()
                getOrElse { null }?.color shouldBe Option.empty()
            }
        }

        "when map with some wrong values should return correct CouchInfo" {
            val couchInfo = mapOf("material" to "wood with cover", "color" to "white").toCouchInfo()
            with (couchInfo) {
                shouldBeSome()
                getOrElse { null }?.numberOfSeats shouldBe Option.empty()
                getOrElse { null }?.color shouldBe Option.just("white")
            }
        }

        "when map with all wrong values should return correct CouchInfo" {
            val couchInfo = mapOf("material" to "wood with cover", "foo" to "bar").toCouchInfo()
            with (couchInfo) {
                shouldBeSome()
                getOrElse { null }?.numberOfSeats shouldBe Option.empty()
                getOrElse { null }?.color shouldBe Option.empty()
            }
        }

        "when all ProductDto fields are set should return correct Product" {
            val dto = ProductDto(
                Option.just("1"),
                Option.just("someUrl"),
                mapOf("material" to "wood with cover", "color" to "white").toOption(),
                Option.just("foo"),
                PriceDto("kr".toOption(), 1.toOption()).toOption(),
                Option.just("chair")
            )
            dto.toProductType()?.run {
                (info.getOrElse { null } as? ChairInfo)?.let {
                    it.color shouldBeSome "white"
                    it.material shouldBeSome "wood with cover"
                } ?: shouldNotBeCalled()
            } ?: shouldNotBeCalled()
        }

        "when ProductDto missing type should return correct null" {
            val dto = ProductDto(
                Option.just("1"),
                Option.just("someUrl"),
                mapOf("material" to "wood with cover", "color" to "white").toOption(),
                Option.just("foo"),
                PriceDto("kr".toOption(), 1.toOption()).toOption(),
                Option.empty()
            )
            dto.toProductType().shouldBeNull()
        }

        "when ProductDto missing info should return correct Product without info" {
            val dto = ProductDto(
                Option.just("1"),
                Option.just("someUrl"),
                Option.empty(),
                Option.just("foo"),
                PriceDto("kr".toOption(), 1.toOption()).toOption(),
                Option.just("chair")
            )
            dto.toProductType().shouldNotBeNull()
        }

        "when ProductDto empty info map should return correct Product without info" {
            val dto = ProductDto(
                Option.just("1"),
                Option.just("someUrl"),
                mapOf<String, String>().toOption(),
                Option.just("foo"),
                PriceDto("kr".toOption(), 1.toOption()).toOption(),
                Option.just("chair")
            )
            dto.toProductType().shouldNotBeNull()
        }
    }
}
