package use_cases.ports

import java.sql.DriverManager


class CreateBatchUseCase {
    fun readFromDB() {
        val c = DriverManager.getConnection("jdbc:sqlite:soapnotes.db")
        val statement = c.createStatement()

        statement.executeUpdate("create table person (id integer, name string)")
        statement.executeUpdate("insert into person values(1, 'leo')")
        statement.executeUpdate("insert into person values(2, 'yui')")

        val rs = statement.executeQuery("select * from person")
        while(rs.next()) {
            println("name = " + rs.getString("name"))
            println("id = " + rs.getInt("id"))
        }
    }
}

