package model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Embeddable
data class OrderRow(
    @field:NotNull
    @field:Size(max = 255)
    @Column(name = "item_name")
    var itemName: String? = null,

    @field:NotNull
    @field:Positive
    var quantity: Int? = null,

    @field:NotNull
    @field:DecimalMin(value = "0.0", inclusive = false)
    var price: Double? = null
)