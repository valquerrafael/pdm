package br.edu.ifpb.rgb

class Paleta {
    private var lista = mutableListOf<Cor>()

    fun add(chocolate: Cor){
        this.lista.add(chocolate)
    }

    fun get(index: Int): Cor{
        return this.lista[index]
    }

    fun get(): MutableList<Cor>{
        return this.lista
    }

    fun delete(index: Int){
        this.lista.removeAt(index)
    }

    fun size(): Int{
        return this.lista.size
    }
}
