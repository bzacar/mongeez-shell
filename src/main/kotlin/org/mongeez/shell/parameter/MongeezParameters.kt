package org.mongeez.shell.parameter

import org.mongeez.MongoAuth

val MONGEEZ_PARAMETERS: MongeezParameters = MongeezParameters()

data class MongeezParameters(var changeSetListFileParameter: String? = null,
                             var context: String? = null,
                             var useMongoShell: Boolean = true,
                             var hostAddress: String = "localhost",
                             var port: Int = 27017,
                             var databaseName: String = "test",
                             var authenticationEnabled: Boolean = true,
                             var authenticationDatabase: String = "admin",
                             var userName: String? = null,
                             var password: CharArray? = null) {

    fun getMongoAuth(): MongoAuth? {
        return if (authenticationEnabled) {
            val userName = userName ?: error("User name is not provided!")
            val password = password ?: error("Password is not provided!")
            MongoAuth(userName, password, authenticationDatabase)
        } else {
            null
        }
    }

    override fun toString(): String {
        return sequenceOf(
                "changeSetListFile" to changeSetListFileParameter,
                "hostAddress" to hostAddress,
                "port" to port,
                "databaseName" to databaseName,
                "authenticationEnabled" to authenticationEnabled,
                "authenticationDatabase" to authenticationDatabase,
                "userName" to userName,
                "password" to password?.let { "***" },
                "context" to context,
                "useMongoShell" to useMongoShell)
                .filter { (_, value) -> value != null }
                .toMap()
                .toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MongeezParameters

        if (changeSetListFileParameter != other.changeSetListFileParameter) return false
        if (hostAddress != other.hostAddress) return false
        if (port != other.port) return false
        if (databaseName != other.databaseName) return false
        if (authenticationEnabled != other.authenticationEnabled) return false
        if (authenticationDatabase != other.authenticationDatabase) return false
        if (userName != other.userName) return false
        val password = password
        if (password != null) {
            val otherPassword = other.password ?: return false
            if (!password.contentEquals(otherPassword)) return false
        } else if (other.password != null) return false
        if (context != other.context) return false
        if (useMongoShell != other.useMongoShell) return false

        return true
    }

    override fun hashCode(): Int {
        var result = changeSetListFileParameter?.hashCode() ?: 0
        result = 31 * result + hostAddress.hashCode()
        result = 31 * result + port
        result = 31 * result + databaseName.hashCode()
        result = 31 * result + authenticationEnabled.hashCode()
        result = 31 * result + authenticationDatabase.hashCode()
        result = 31 * result + (userName?.hashCode() ?: 0)
        result = 31 * result + (password?.contentHashCode() ?: 0)
        result = 31 * result + (context?.hashCode() ?: 0)
        result = 31 * result + useMongoShell.hashCode()
        return result
    }
}
