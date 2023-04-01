package br.edu.ifpb.sorteio

class Sorteador {
    private val texts = mutableListOf<String>()

    fun addText(text: String) {
        if (text.isNotEmpty() && text.isNotBlank())
            this.texts.add(text)
    }

    fun sorteio(): String? {
        if (this.texts.isEmpty())
            return null

        return this.texts.random()
    }
}
