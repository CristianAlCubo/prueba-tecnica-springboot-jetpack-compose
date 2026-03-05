package com.ccubillos.prueba.dto.user

import com.ccubillos.prueba.models.User

public class UserDTO (
    var id: String,
    var username: String
){
    constructor(user: User) : this(user.id.toString(), user.username)
}
