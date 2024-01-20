package model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotNull

@Embeddable
data class Authority(
    @field:NotNull
    @Column(name = "authority")
    var userAuthority: String? = null
)