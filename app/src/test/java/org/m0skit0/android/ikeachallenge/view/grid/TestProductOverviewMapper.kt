package org.m0skit0.android.ikeachallenge.view.grid

import arrow.core.Option
import arrow.core.toOption
import io.kotlintest.Spec
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.m0skit0.android.ikeachallenge.data.api.PriceDto
import org.m0skit0.android.ikeachallenge.domain.Product
import org.m0skit0.android.ikeachallenge.view.product.grid.toProductOverviews

class TestProductOverviewMapper : FreeSpec() {

    @MockK
    private lateinit var mockPriceDto: PriceDto

    private lateinit var products: List<Product>

    private fun getMockProduct(): Product =
        mockk<Product>().apply {
            every { id } returns "id".toOption()
            every { name } returns "name".toOption()
            every { price } returns mockPriceDto.toOption()
            every { imageUrl } returns "url".toOption()
        }

    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        MockKAnnotations.init(this)
        products =  (1..10).map { getMockProduct() }
        every { mockPriceDto.value } returns 0.0.toOption()
        every { mockPriceDto.currency } returns "kr".toOption()
    }

    init {
        "when list of products has incorrect products should filter them out" {

            every { products[0].id } returns Option.empty()
            every { products[1].name } returns Option.empty()
            every { products[2].price } returns Option.empty()

            products.toProductOverviews().run {
                size shouldBe 7
                get(0).id shouldBe "id"
                get(1).name shouldBe "name"
                get(2).price shouldBe "0.00 kr"
                get(3).imageUrl shouldBe "url".toOption()
            }
        }
    }
}