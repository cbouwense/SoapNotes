package use_cases

import entities.Product
import ports.ProductRepo

class GetAllProducts(val productRepo: ProductRepo) {
    fun run(): List<Product> {
        return productRepo.getAll()
    }
}