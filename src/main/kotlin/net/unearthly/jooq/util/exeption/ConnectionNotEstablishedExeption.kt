package net.unearthly.jooq.util.exeption

class ConnectionNotEstablishedException : RuntimeException {
    constructor() : super("Connection not established")

    constructor(throwable: Throwable?) : super("Connection not established", throwable)
}
