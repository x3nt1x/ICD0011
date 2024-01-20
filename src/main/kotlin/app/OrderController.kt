package app

import dao.OrderDao
import jakarta.validation.Valid
import model.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class OrderController(private val dao: OrderDao) {
    @GetMapping("orders")
    fun getOrders(): List<Order> {
        return dao.getAllOrders()
    }

    @GetMapping("orders/{id}")
    fun getOrder(@PathVariable id: Int): Order {
        return dao.getOrderById(id)
    }

    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveOrder(@RequestBody order: @Valid Order): Order {
        return dao.saveOrder(order)
    }

    @DeleteMapping("orders")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@RequestParam id: Int) {
        dao.deleteOrder(id)
    }
}