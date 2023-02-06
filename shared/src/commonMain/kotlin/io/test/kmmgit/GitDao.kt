package io.test.kmmgit

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import io.test.kmmgit.model.GitFollowerRealm
import io.test.kmmgit.model.GitUserRealm
import io.test.kmmgit.model.asData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitDao {

    private val realm: Realm by lazy {
        val configuration = RealmConfiguration.Builder(
            setOf(GitUserRealm::class, GitFollowerRealm::class)
        ).apply {
            deleteRealmIfMigrationNeeded()
        }.build()

        Realm.open(configuration)
    }

    // Sync
    fun insertBlocking() {
        realm.writeBlocking {
            copyToRealm(gitUserData, UpdatePolicy.ALL)
        }
    }

    fun get() =
        realm.query<GitUserRealm>()
            .find()
            .asData()

    fun get(id: String) =
        realm.query<GitUserRealm>("id == '$id'")
            .find()
            .asData()

    fun deleteBlocking() {
        realm.writeBlocking {
            val gitUser = query<GitUserRealm>().find()
            delete(gitUser)
        }
    }

    fun deleteBlocking(id: Int) {
        realm.writeBlocking {
            val gitUser = query<GitUserRealm>("id == '$id'").find()
            delete(gitUser)
        }
    }

    private fun RealmResults<GitUserRealm>.asData() =
        this.map { gitUserRealm ->
            gitUserRealm.asData()
        }

    // Async by Coroutines
    suspend fun insert() {
        realm.write {
            copyToRealm(gitUserData, UpdatePolicy.ALL)
        }
    }

    fun getAsFlow() =
        realm.query<GitUserRealm>()
            .asFlow()
            .asData()

    fun getAsFlow(id: Int) =
        realm.query<GitUserRealm>("id == '$id'")
            .asFlow()
            .asData()

    suspend fun delete() {
        realm.write {
            val gitUser = query<GitUserRealm>().find()
            delete(gitUser)
        }
    }

    suspend fun delete(id: Int) {
        realm.write {
            val gitUser = query<GitUserRealm>("id == '$id'").find()
            delete(gitUser)
        }
    }

    private fun Flow<ResultsChange<GitUserRealm>>.asData() =
        this.map { results ->
            results.list.map { gitUserRealm ->
                gitUserRealm.asData()
            }
        }
}


// https://api.github.com/users/Yeechaan
val gitUserData = GitUserRealm().apply {
    id = 1
    login = "Yeechaan"
    name = "이예찬"
    company = "SNPLab"
    followers = realmListOf(
        GitFollowerRealm().apply {
            id = 1
            login = "junsuboy"
            url = "https://api.github.com/users/junsuboy"
        },
        GitFollowerRealm().apply {
            id = 2
            login = "inkwon-jeong"
            url = "https://api.github.com/users/inkwon-jeong"
        }
    )
}