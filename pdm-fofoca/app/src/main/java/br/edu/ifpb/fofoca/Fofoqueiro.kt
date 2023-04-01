package br.edu.ifpb.fofoca

class Fofoqueiro {
    private val fofocas = mutableListOf<Fofoca>()

    fun addFofoca(fofoca: Fofoca) {
        this.fofocas.add(fofoca)
    }

    fun getTotalFofocas(): Int {
        return this.fofocas.size
    }

    fun getRandomFofoca(): Fofoca {
        return this.fofocas.random()
    }
}
