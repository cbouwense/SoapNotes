package cli

import db.SQLiteRecipeRepo
import entities.Recipe
import use_cases.CreateRecipe
import java.sql.DriverManager
import java.util.*

val version = "v0.0.1"
val input = Scanner(System.`in`)

fun main() {
    printWelcomeMessage()

    val command = input.nextInt()
    input.nextLine()
    runCommand(command)
}

fun printWelcomeMessage() {
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
        1 -> println("Not implemented yet")
        2 -> createNewRecipe()
        3 -> println("Not implemented yet")
        4 -> println("Not implemented yet")
        else -> println("Unknown command...")
    }
}

fun createNewRecipe() {
    println("Create new recipe")
    print("Name: ")
    val name = input.nextLine()
    print("Version: ")
    val version = input.nextLine()

    val c = DriverManager
        .getConnection("jdbc:sqlite:C:\\Users\\bouwe\\code\\SoapNotes\\resources\\soapnotes.db")
    val s = c.createStatement()

    CreateRecipe(
        Recipe(name = name, version = version),
        SQLiteRecipeRepo(s)
    ).run()
}
