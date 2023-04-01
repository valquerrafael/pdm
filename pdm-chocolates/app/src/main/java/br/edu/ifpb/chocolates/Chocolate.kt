package br.edu.ifpb.chocolates

var CODIGO = 0L
class Chocolate (var description: String, var quantity: Int): java.io.Serializable {
    val id = ++CODIGO

    override fun toString(): String {
        return "$description - $quantity"
    }
}
