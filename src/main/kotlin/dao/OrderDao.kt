package dao

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import model.Order
import org.springframework.stereotype.Repository

@Repository
open class OrderDao {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    open fun getOrderById(id: Int): Order {
        return entityManager.createQuery("select o from Order o where o.id = :id", Order::class.java)
                            .setParameter("id", id)
                            .singleResult
    }

    @Transactional
    open fun getAllOrders(): List<Order> {
        return entityManager.createQuery("select o from Order o", Order::class.java).resultList
    }

    @Transactional
    open fun saveOrder(order: Order): Order {
        if (order.id == null) {
            entityManager.persist(order)
        } else {
            entityManager.merge(order)
        }

        return order
    }

    @Transactional
    open fun deleteOrder(id: Int) {
        val order = entityManager.find(Order::class.java, id)

        if (order != null) {
            entityManager.remove(order)
        }
    }
}