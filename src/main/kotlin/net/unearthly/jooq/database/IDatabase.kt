package net.unearthly.jooq.database

import net.unearthly.jooq.util.JOOQ
import java.sql.Connection
import java.util.concurrent.CompletableFuture

interface IDatabase {

    fun getConnection(): CompletableFuture<Connection>

}