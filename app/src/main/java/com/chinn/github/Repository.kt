package com.chinn.github

class Repository(_name: String = "", _description: String? = "", _star: Int = 0) {
    val name: String = _name;
    val description: String? = _description;
    val star: Int = _star;
}