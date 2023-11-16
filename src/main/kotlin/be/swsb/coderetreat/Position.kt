package be.swsb.coderetreat

data class Position(val x: Int, val y: Int) {
    operator fun minus(position: Position): Position {
        return Position(x - position.x, y - position.y)
    }

    operator fun plus(position: Position): Position {
        return Position(x + position.x, y + position.y)
    }
}
