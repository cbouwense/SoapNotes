package use_cases

import entities.Product
import ports.ProductRepo

class CreateProduct(val product: Product, val productRepo: ProductRepo) {
    fun run(): Int {
        return productRepo.create(product)
    }
}