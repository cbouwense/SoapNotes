package use_cases.repo

import entities.MeasurementUnit
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
            listOfProducts.add(
                Product(
                    id = result.getInt("id"),
                    name = result.getString("name"),
                    netWeightAmount = result.getFloat("total_grams"),
                    netWeightUnit = MeasurementUnit.GRAMS,
                    priceInCents = result.getInt("total_cents")
                )
            )
        }

        return listOfProducts.toList()
    }

    override fun findById(id: Int): Product? {
        val result = query("SELECT * FROM products WHERE id = ${id}")

        return Product(
            id = result.getInt("id"),
            name = result.getString("name"),
            netWeightAmount = result.getFloat("total_grams"),
            netWeightUnit = MeasurementUnit.GRAMS,
            priceInCents = result.getInt("total_cents")
        )
    }

    override fun findByName(name: String): Product? {
        val result = query("SELECT * FROM products WHERE name = ${name}")

        return Product(
            id = result.getInt("id"),
            name = result.getString("name"),
            netWeightAmount = result.getFloat("total_grams"),
            netWeightUnit = MeasurementUnit.GRAMS,
            priceInCents = result.getInt("total_cents")
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
