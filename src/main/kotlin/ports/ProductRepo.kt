package ports

import entities.Product

interface ProductRepo {
    fun create(b: Product): Int
    fun findById(id: Int): Product?
    fun findByName(name: String): Product?
    fun getAll(): List<Product>
}