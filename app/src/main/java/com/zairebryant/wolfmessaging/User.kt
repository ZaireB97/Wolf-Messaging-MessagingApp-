package com.zairebryant.wolfmessaging

/*Firebase needs any empty constructor to work with,
Hence the reason why we are using a normal class and not a data class

 */


class User {

    var name:String? = null
    var email:String? = null
    var userId:String? = null

    constructor(){}

    constructor(name:String?,email:String?,userId:String?){

        this.name = name
        this.email = email
        this.userId = userId
    }


}