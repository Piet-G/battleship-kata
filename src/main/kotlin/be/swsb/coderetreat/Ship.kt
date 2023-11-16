package be.swsb.coderetreat

class Ship(val position: Position, shape: List<Position>) {
    enum class SquareState { FLOATING, HIT }

    private val squareStates: MutableMap<Position, SquareState> = shape.associateWith { SquareState.FLOATING }.toMutableMap();

    fun isSunken(): Boolean {
        return squareStates.values.all { it == SquareState.HIT }
    }

    fun attemptHit(position: Position): Boolean {
        val relativePosition = position - this.position;

        if (squareStates[relativePosition] == SquareState.FLOATING) {
            squareStates[relativePosition] = SquareState.HIT
            return true
        }

        return false
    }

    fun render(renderingGrid: RenderingGrid) {
        squareStates.forEach { (relativePosition, state) ->
            renderingGrid.setAtPosition(relativePosition + position,
                    if(isSunken()) {
                        "\uD83C\uDFCA"
                    } else {
                        when (state) {
                            SquareState.FLOATING -> "\uD83D\uDEF3\uFE0F\uFE0F\uFE0F\uFE0F"
                            SquareState.HIT -> "💥"
                        }
                    }
            )
        }
    }
}