package cli

import entities.*
import use_cases.repo.SQLiteRecipeRepo
import use_cases.CreateBatch
import use_cases.CreateProduct
import use_cases.CreateRecipe
import use_cases.GetAllProducts
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

    var command: Int?
    do {
        displayMenu()
        command = input.nextInt()
        input.nextLine()
        runCommand(command)
    } while (command != 0)
}

fun displayWelcomeMessage() {
    println("Welcome to SoapNotes (${version}")
}

fun displayMenu() {
    println("+---+---------------+")
    println("| 1 | Add batch     |")
    println("| 2 | Add product   |")
    println("| 3 | List batches  |")
    println("| 4 | List products |")
    println("| 0 | Exit          |")
    println("+---+---------------+")
    println("")
}

fun runCommand(c: Int) {
    when (c) {
        1 -> addBatch()
        2 -> addProduct()
        3 -> println("Not implemented yet")
        4 -> listProducts()
        else -> println("Unknown command")
    }
}

fun listProducts() {
    val products = GetAllProducts(SQLiteProductRepo(s)).run()
    println("----------------")
    println("Products")
    println("----------------")
    products.forEach{ println("| ${it.id}\t| ${it.name}\t| ${it.netWeightAmount}\t| ${it.priceInCents}") }
    println("----------------")
    println("")
}

fun addBatch() {
    // Get raw input for batch row
    println("----------------")
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
    println("----------------")
    println("Products used")
    println("----------------")
    do {
        listProducts()
        print("Product ((g)-(product_id)): ")
        val productRaw = input.nextLine()
        if (productRaw != "") productsRaw.add(productRaw)
    } while (productRaw != "")
    println("productsRaw $productsRaw.toString()")

    // Process the raw product input into an array of Ingredients
    val recipe = Recipe()
    productsRaw.forEach { recipe.addIngredient(productRawToIngredient(it)) }
    CreateRecipe(recipe, SQLiteRecipeRepo(s)).run()

    // Use raw input or default
    val name = if (nameRaw == "") "Unnamed batch" else nameRaw
    val pourDate =
        if (pourDateRaw == "") LocalDate.now().atStartOfDay(zoneId).toEpochSecond()
        else LocalDate.parse(pourDateRaw).atStartOfDay(zoneId).toEpochSecond()
    val cureDate =
        if (cureDateRaw == "") LocalDate.now().plusWeeks(6).atStartOfDay(zoneId).toEpochSecond()
        else LocalDate.parse(cureDateRaw).atStartOfDay(zoneId).toEpochSecond()

    // Invoke use case
    val returnCode = CreateBatch(
        Batch(name = name, pourDate = pourDate, cureDate = cureDate, recipe = recipe),
        SQLiteBatchRepo(s, recipeRepo = SQLiteRecipeRepo(s))
    ).run()

    println("Returned $returnCode")
}

fun productRawToIngredient(p: String): Ingredient {
    val tokens = p.split('-')

    val product = SQLiteProductRepo(s).findById(tokens[1].toInt())

    return Ingredient(
        product = product,
        measurementAmount = tokens[0].toFloat()
    )
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