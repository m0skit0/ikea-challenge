package org.m0skit0.android.ikeachallenge.view.detail

import arrow.core.Option
import arrow.core.toOption
import io.kotlintest.TestCase
import io.kotlintest.assertions.arrow.either.shouldBeLeftOfType
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.kotlintest.specs.FreeSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.domain.ChairInfo
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.error.InvalidProduct
import org.m0skit0.android.ikeachallenge.util.getStringResource
import org.m0skit0.android.ikeachallenge.view.product.detail.ItemInfo
import org.m0skit0.android.ikeachallenge.view.product.detail.ProductDetail
import org.m0skit0.android.ikeachallenge.view.product.detail.toDetail

class TestProductDetailMapper : FreeSpec() {

    @MockK
    private lateinit var mockProduct: Product

    @MockK
    private lateinit var mockChairInfo: ChairInfo

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        MockKAnnotations.init(this)
        mockkStatic("org.m0skit0.android.ikeachallenge.util.KoinUtilsKt")
        every { mockProduct.id } returns "id".toOption()
        every { mockProduct.name } returns "name".toOption()
        every { mockProduct.price } returns PriceDto("kr".toOption(), 0.0.toOption()).toOption()
        every { mockProduct.imageUrl } returns "url".toOption()
        every { mockProduct.info } returns mockChairInfo.toOption()
        every { mockChairInfo.material } returns "material".toOption()
        every { mockChairInfo.color } returns "color".toOption()
        every { getStringResource(any()) } returns "resource"
    }

    init {
        "when product has no id should return invalid product" {
            every { mockProduct.id } returns Option.empty()
            mockProduct.toDetail().shouldBeLeftOfType<InvalidProduct>()
        }

        "when product has no name should return invalid product" {
            every { mockProduct.name } returns Option.empty()
            mockProduct.toDetail().shouldBeLeftOfType<InvalidProduct>()
        }

        "when product has no price should return valid product with default value as price" {
            every { mockProduct.price } returns Option.empty()
            mockProduct.toDetail().shouldBeRight(
                ProductDetail(
                    "id",
                    "name",
                    "???",
                    "url".toOption(),
                    listOf(ItemInfo("resource", "Material"), ItemInfo("resource", "Color"))
                )
            )
        }

        "when product has all should return valid product" {
            mockProduct.toDetail().shouldBeRight(
                ProductDetail(
                    "id",
                    "name",
                    "0.00 kr",
                    "url".toOption(),
                    listOf(ItemInfo("resource", "Material"), ItemInfo("resource", "Color"))
                )
            )
        }
    }
}
