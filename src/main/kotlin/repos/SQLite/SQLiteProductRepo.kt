package repos.SQLite

import entities.Product
import ports.ProductRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteProductRepo(override val s: Statement) : SQLiteRepo(s), ProductRepo {
    override fun create(p: Product): Int {
        return update("INSERT INTO products VALUES (NULL, '${p.name}', ${p.netWeightAmount}, ${p.priceInCents})")
    }

    override fun getAll(): List<Product> {
        val result = query("SELECT * FROM products")
        val listOfProducts = ArrayList<Product>()
        if (result == null) return listOfProducts.toList()

        while (result.next()) {
            val product = translateResultSetToEntity(result)
            if (product == null) continue

            listOfProducts.add(product)
        }

        return listOfProducts.toList()
    }

    override fun findById(id: Int): Product? {
        val result = query("SELECT * FROM products WHERE id = ${id}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    override fun findByName(name: String): Product? {
        val result = query("SELECT * FROM products WHERE name = ${name}")
        if (result == null) return null

        return translateResultSetToEntity(result)
    }

    fun translateResultSetToEntity(r: ResultSet): Product? {
        val productId = r.getInt("id")
        val productName = r.getString("name")
        val totalGrams = r.getFloat("total_grams")
        val totalCents = r.getInt("total_cents")

        if (productId == null || productName == null || totalGrams == null || totalCents == null) return null

        return Product(
            id = productId,
            name = productName,
            netWeightAmount = totalGrams,
            priceInCents = totalCents
        )
    }
}
