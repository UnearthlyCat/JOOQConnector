package net.unearthly.jooq.database

import net.unearthly.jooq.util.JOOQ
import net.unearthly.jooq.util.exeption.ConnectionNotEstablishedException
import org.jooq.SQLDialect
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.concurrent.CompletableFuture

class MySQLDatabase(
    private var host: String,
    private val database: String,
    private val user: String,
    private val password: String,
    private var connection: Connection
) : IDatabase, JOOQ {

    init {
        this.host = if (host.contains(":")) host else "$host:3306"
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("Unable to find MySQL driver", e)
        }
    }

    override fun getConnection(): CompletableFuture<Connection> {
        return CompletableFuture.supplyAsync {
            try {
                if (connection.isClosed) {
                    val password = URLEncoder.encode(this.password, StandardCharsets.UTF_8)
                    val url = "jdbc:mysql://$user:$password@$host/$database"
                    connection = DriverManager.getConnection(url, user, password)
                }
                return@supplyAsync connection
            } catch (e: SQLException) {
                throw ConnectionNotEstablishedException(e)
            }
        }
    }

    override fun dialect(): SQLDialect {
        return SQLDialect.MYSQL
    }

}