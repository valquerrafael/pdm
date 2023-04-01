package br.edu.ifpb.chocolates

class Basket {
    private var basket = mutableListOf<Chocolate>()

    fun add(chocolate: Chocolate){
        this.basket.add(chocolate)
    }

    fun get(index: Int): Chocolate{
        return this.basket[index]
    }

    fun get(): MutableList<Chocolate>{
        return this.basket
    }

    fun del(index: Int){
        this.basket.removeAt(index)
    }
}
