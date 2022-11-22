package cli

import entities.Batch
import use_cases.repo.SQLiteRecipeRepo
import entities.Recipe
import use_cases.CreateBatch
import use_cases.CreateRecipe
import use_cases.repo.SQLiteBatchRepo
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
    println("+---+-------------------+")
    println("| 1 | Create new batch  |")
    println("| 2 | Create new recipe |")
    println("| 3 | List all batches  |")
    println("| 4 | List all recipes  |")
    println("+---+-------------------+")
    println("")
}

fun runCommand(c: Int) {
    when (c) {
        1 -> createNewBatch()
        2 -> println("Not implemented yet")
        3 -> println("Not implemented yet")
        4 -> println("Not implemented yet")
        else -> println("Unknown command")
    }
}

fun createNewBatch() {
    println("Create new batch")
    println("----------------")
    print("Name: ")
    val nameRaw = input.nextLine()
    println("Pour date (yyyy-mm-dd)")
    print("[today]: ")
    val pourDateRaw = input.nextLine()
    println("Cure date (yyyy-mm-dd)")
    print("[6 weeks from today]: ")
    val cureDateRaw = input.nextLine()

    val name = if (nameRaw == "") "Unnamed batch" else nameRaw
    val pourDate =
        if (pourDateRaw == "") LocalDate.now().atStartOfDay(zoneId).toEpochSecond()
        else LocalDate.parse(pourDateRaw).atStartOfDay(zoneId).toEpochSecond()
    val cureDate =
        if (cureDateRaw == "") LocalDate.now().plusWeeks(6).atStartOfDay(zoneId).toEpochSecond()
        else LocalDate.parse(cureDateRaw).atStartOfDay(zoneId).toEpochSecond()

    val returnCode = CreateBatch(
        Batch(name = name, pourDate = pourDate, cureDate = cureDate),
        SQLiteBatchRepo(s, recipeRepo = SQLiteRecipeRepo(s))
    ).run()

    println("Returned $returnCode")
}
