package io.test.kmmgit.model

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.annotations.Index
import org.mongodb.kbson.ObjectId

data class GitFollower(
    var id: Int = 0,
    var login: String = "",
    var url: String = "",
)

class GitFollowerRealm : EmbeddedRealmObject {
    var id: Int = 0
    var login: String = ""
    var url: String = ""

    @Index
    var index: ObjectId = ObjectId()
}

fun GitFollowerRealm.asData() =
    GitFollower(
        id = id,
        login = login,
        url = url
    )