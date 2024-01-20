package model

import jakarta.persistence.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    var id: Int? = null,

    @field:NotNull
    @field:Size(min = 2, max = 255)
    @Column(name = "order_number")
    var orderNumber: String? = null,

    @field:Valid
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_rows", joinColumns = [JoinColumn(name = "orders_id", referencedColumnName = "id")])
    var orderRows: MutableList<OrderRow> = ArrayList()
)