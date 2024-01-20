package dao

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import model.User
import org.springframework.stereotype.Repository

@Repository
class UserDao {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun getUserByUsername(name: String): User {
        return entityManager.createQuery("select u from User u where u.username = :name", User::class.java)
                            .setParameter("name", name)
                            .singleResult
    }

    fun getAllUsers(): List<User> {
        return entityManager.createQuery("select u from User u", User::class.java).resultList
    }
}