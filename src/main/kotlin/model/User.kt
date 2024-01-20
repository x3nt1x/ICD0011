package model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "users")
data class User (
    @Id
    @field:NotNull
    var username:  String? = null,

    @field:NotNull
    var password: String? = null,

    @field:NotNull
    var enabled: Boolean? = null,

    @field:NotNull
    @Column(name = "first_name")
    var firstName: String? = null,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = [JoinColumn(name = "username", referencedColumnName = "username")])
    var authorities: List<Authority> = ArrayList()
)