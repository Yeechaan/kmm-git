package io.test.kmmgit.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlin.math.log

data class GitUser(
    var id: Int = 0,
    var login: String = "",
    var name: String = "",
    var company: String = "",
    var followers: List<GitFollower> = listOf(),
)

class GitUserRealm : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var login: String = ""
    var name: String = ""
    var company: String = ""
    var followers: RealmList<GitFollowerRealm> = realmListOf()
}

fun GitUserRealm.asData() =
    GitUser(
        id = id,
        login = login,
        name = name,
        company = company,
        followers = followers.map { it.asData() }
    )


fun GitUser.asRealm() =
    GitUserRealm().also { gitUserRealm ->
        gitUserRealm.id = id
        gitUserRealm.login = login
        gitUserRealm.name = name
    }


//interface GitUser {
//    var id: Int
//    var login: String
//    var name: String
//    var company: String
//}

//@Serializable
//data class GitUserDTO(
//    @SerialName("login")
//    val login: String,
//    @SerialName("blog")
//    val blog: String,
//    @SerialName("location")
//    val location: String,
//)