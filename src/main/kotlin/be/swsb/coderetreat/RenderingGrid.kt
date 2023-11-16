package be.swsb.coderetreat

class RenderingGrid(height: Int, width: Int) {
    private val cells = Array(height) { Array(width) { "🌊" } }
    fun setAtPosition(position: Position, value: String) {
        cells[position.y][position.x] = value
    }

    fun render(): String {
        return cells.joinToString(separator = "\n") { row -> row.joinToString("")}
    }
}

