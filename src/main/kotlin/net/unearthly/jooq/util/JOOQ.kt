package net.unearthly.jooq.util

import org.jooq.SQLDialect

interface JOOQ {

    fun dialect() : SQLDialect

}