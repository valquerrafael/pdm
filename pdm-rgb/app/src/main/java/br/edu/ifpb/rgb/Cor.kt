package br.edu.ifpb.rgb

var ID = 0
class Cor(var name: String, var red: Int, var green: Int, var blue: Int): java.io.Serializable {
    val id = ID++

    override fun toString(): String {
        return "$name - R: $red G: $green B: $blue"
    }
}
