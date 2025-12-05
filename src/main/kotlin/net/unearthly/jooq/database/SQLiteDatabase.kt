package net.unearthly.jooq.database

import net.unearthly.jooq.util.JOOQ
import net.unearthly.jooq.util.exeption.ConnectionNotEstablishedException
import org.jooq.SQLDialect
import java.io.File
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.concurrent.CompletableFuture

class SQLiteDatabase(private val file: File) : IDatabase, JOOQ{

    private var connection: Connection? = null

    init {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw IOException("Failed to create the database file")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        try {
            Class.forName("org.sqlite.JDBC")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("Unable to find SQLite driver", e)
        }
    }

    override fun getConnection(): CompletableFuture<Connection> {
        return CompletableFuture.supplyAsync {
            try {
                if (connection == null || connection!!.isClosed) {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + file.absolutePath)
                }
                return@supplyAsync connection
            } catch (e: SQLException) {
                throw ConnectionNotEstablishedException(e)
            }
        }
    }

    override fun dialect(): SQLDialect {
        return SQLDialect.SQLITE
    }

}