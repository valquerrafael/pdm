package br.edu.ifpb.arrocha

class Arrocha(private var minLimit: Int, private var maxLimit: Int) {
    private val drawnNumber = (minLimit + 1 until maxLimit).random()
    private var status = GameStatus.EXECUTING
    private lateinit var guessStatus: GuessStatus

    fun getMinLimit(): Int {
        return this.minLimit
    }

    fun getMaxLimit(): Int {
        return this.maxLimit
    }

    fun getStatus(): GameStatus {
        return this.status
    }

    fun getGuessStatus(): GuessStatus {
        return this.guessStatus
    }

    fun guess(guess: Int) {
        if (this.isAInvalidGuess(guess))
            this.status = GameStatus.LOST
        else {
            this.changeLimit(guess)
            if (this.arrochou())
                this.status = GameStatus.WON
        }
    }

    private fun changeLimit(guess: Int) {
        if (guess < this.drawnNumber) {
            this.minLimit = guess
            this.guessStatus = GuessStatus.MENOR
        } else if (guess > this.drawnNumber) {
            this.maxLimit = guess
            this.guessStatus = GuessStatus.MAIOR
        }
    }

    private fun isAInvalidGuess(guess: Int): Boolean {
        if (guess == this.drawnNumber) {
            this.guessStatus = GuessStatus.IGUAL
            return true
        }

        return guess <= this.minLimit || guess >= this.maxLimit
    }

    private fun arrochou(): Boolean {
        return this.minLimit + 1 == this.maxLimit - 1
    }

    enum class GameStatus {
        WON, LOST, EXECUTING
    }

    enum class GuessStatus {
        MAIOR, MENOR, IGUAL
    }
}