package cli

import entities.Batch
import entities.MeasurementUnit
import entities.Product
import use_cases.repo.SQLiteRecipeRepo
import entities.Recipe
import use_cases.CreateBatch
import use_cases.CreateProduct
import use_cases.CreateRecipe
import use_cases.repo.SQLiteBatchRepo
import use_cases.repo.SQLiteProductRepo
import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

val version = "v0.0.1"
val input = Scanner(System.`in`)
val c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\bouwe\\code\\SoapNotes\\resources\\soapnotes.db")
val s = c.createStatement()
val zoneId = ZoneId.systemDefault()

fun main() {
    displayWelcomeMessage()

    val command = input.nextInt()
    input.nextLine()
    runCommand(command)
}

fun displayWelcomeMessage() {
    println("Welcome to SoapNotes (${version}")
    println("+---+---------------+")
    println("| 1 | Add batch     |")
    println("| 2 | Add product   |")
    println("| 3 | View batches  |")
    println("| 4 | View recipes  |")
    println("+---+---------------+")
    println("")
}

fun runCommand(c: Int) {
    when (c) {
        1 -> addBatch()
        2 -> addProduct()
        3 -> println("Not implemented yet")
        4 -> println("Not implemented yet")
        else -> println("Unknown command")
    }
}

fun addBatch() {
    // Get raw input for batch row
    println("Add batch")
    println("----------------")
    print("Name: ")
    val nameRaw = input.nextLine()
    println("Pour date (yyyy-mm-dd)")
    print("[today]: ")
    val pourDateRaw = input.nextLine()
    println("Cure date (yyyy-mm-dd)")
    print("[6 weeks from today]: ")
    val cureDateRaw = input.nextLine()

    // Get raw input for product
    val productsRaw = arrayListOf<String>()
    println("Products used")
    println("-------------")
    do {
        print("Product [g-product_id]: ")
        val productRaw = input.nextLine()
        if (productRaw != "") productsRaw.add(productRaw)
    } while (productRaw != "")

    // Use raw input or default
    val name = if (nameRaw == "") "Unnamed batch" else nameRaw
    val pourDate =
        if (pourDateRaw == "") LocalDate.now().atStartOfDay(zoneId).toEpochSecond()
        else LocalDate.parse(pourDateRaw).atStartOfDay(zoneId).toEpochSecond()
    val cureDate =
        if (cureDateRaw == "") LocalDate.now().plusWeeks(6).atStartOfDay(zoneId).toEpochSecond()
        else LocalDate.parse(cureDateRaw).atStartOfDay(zoneId).toEpochSecond()

    // Create recipe for batch


    // Invoke use case
    val returnCode = CreateBatch(
        Batch(name = name, pourDate = pourDate, cureDate = cureDate),
        SQLiteBatchRepo(s, recipeRepo = SQLiteRecipeRepo(s))
    ).run()

    println("Returned $returnCode")
}

fun addProduct() {
    println("Add product")
    println("-----------")
    print("Name: ")
    val name = input.nextLine()
    print("Weight (g): ")
    val grams = input.nextFloat()
    print("Price (c): ")
    val cents = input.nextInt()

    val returnCode = CreateProduct(
        Product(
            name = name,
            netWeightAmount = grams,
            priceInCents = cents
        ),
        SQLiteProductRepo(s)
    ).run()

    println("Returned $returnCode")
}