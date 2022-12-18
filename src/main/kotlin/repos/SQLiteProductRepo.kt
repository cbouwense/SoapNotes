package repos

import entities.Product
import ports.ProductRepo
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SQLiteProductRepo(val s: Statement) : ProductRepo {
    override fun create(p: Product): Int {
        return update("INSERT INTO products VALUES (NULL, '${p.name}', ${p.netWeightAmount}, ${p.priceInCents})")
    }

    override fun getAll(): List<Product> {
        val result = query("SELECT * FROM products")

        val listOfProducts = ArrayList<Product>()

        while (result.next()) {
            val product = translateResultSetToEntity(result)
            if (product == null) break
            listOfProducts.add(product)
        }

        return listOfProducts.toList()
    }

    override fun findById(id: Int): Product? {
        val result = query("SELECT * FROM products WHERE id = ${id}")
        return translateResultSetToEntity(result)
    }

    override fun findByName(name: String): Product? {
        val result = query("SELECT * FROM products WHERE name = ${name}")
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

    // TODO: this should probably be in a parent class.
    private fun update(sql: String): Int {
        try {
            return s.executeUpdate(sql)
        } catch (e: SQLException) {
            println(e.toString())
            return -1
        }
    }

    // TODO: this should probably be in a parent class.
    private fun query(sql: String): ResultSet {
        return s.executeQuery(sql)
    }
}
