package cli

import entities.*
import use_cases.*
import repos.SQLite.SQLiteRecipeRepo
import repos.SQLite.SQLiteBatchRepo
import repos.SQLite.SQLiteProductRepo
import java.sql.DriverManager
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

val version = "v0.0.1"
val input = Scanner(System.`in`)
val c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\bouwe\\code\\SoapNotes\\resources\\soapnotes.db")
val zoneId = ZoneId.systemDefault()

fun Test() {
    val s = c.createStatement()
    val recipeRepo = SQLiteRecipeRepo(s)
    val batchRepo = SQLiteBatchRepo(s, recipeRepo)
    val productRepo = SQLiteProductRepo(s)
    val recipe = recipeRepo.findById(1)
    val batch = Batch(
        name = "Test batch",
        pourDate = LocalDate.now().atStartOfDay(zoneId).toEpochSecond(),
        cureDate = LocalDate.now().plusDays(30).atStartOfDay(zoneId).toEpochSecond(),
        recipe = recipe
    )
    val createBatch = CreateBatch(batch, batchRepo)
    val batchId = createBatch.run()
    println("Created batch with id: ${batchId}")
}

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
        0 -> /* no-op */ {}
        1 -> addBatch()
        2 -> addProduct()
        3 -> listBatches()
        4 -> listProducts()
        else -> println("Unknown command")
    }
}

fun listBatches() {
    val batchStatement = c.createStatement()
    val recipeStatement = c.createStatement()
    val batches = GetAllBatches(SQLiteBatchRepo(batchStatement, SQLiteRecipeRepo(recipeStatement))).run()
    println("----------------")
    println("Batches")
    println("----------------")
    batches.forEach{ println("| ${it.id}\t| ${it.name}\t| ${it.pourDate}\t| ${it.cureDate} | ${it.recipe?.id}") }
    println("----------------")
    println("")
}

fun listProducts() {
    val productStatement = c.createStatement()
    val products = GetAllProducts(SQLiteProductRepo(productStatement)).run()
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

    // Process the raw product input into an array of Ingredients
    val recipe = Recipe()
    println("----------------")
    println("Products used")
    println("----------------")
    do {
        listProducts()

        print("Product ID: ")
        val productIdRaw = input.nextInt()
        if (productIdRaw == 0) break
        print("Product Used (g): ")
        val productWeightRaw = input.nextFloat()

        recipe.addIngredient(
            Ingredient(
                Product(
                    id = productIdRaw,
                    netWeightAmount = productWeightRaw
                )
            )
        )
    } while (productIdRaw != 0)

    val recipeStatement = c.createStatement()
    val batchStatement = c.createStatement()
    CreateRecipe(recipe, SQLiteRecipeRepo(recipeStatement)).run()

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
        SQLiteBatchRepo(batchStatement, recipeRepo = SQLiteRecipeRepo(recipeStatement))
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

    val productStatement = c.createStatement()
    val returnCode = CreateProduct(
        Product(
            name = name,
            netWeightAmount = grams,
            priceInCents = cents
        ),
        SQLiteProductRepo(productStatement)
    ).run()

    println("Returned $returnCode")
}
