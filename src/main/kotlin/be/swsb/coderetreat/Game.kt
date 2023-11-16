package be.swsb.coderetreat

class Game(private val gridWidth: Int, private val gridHeight: Int, private val boats: List<List<Ship>>, private val hard: Boolean = false) {
    private val misfires = Array(boats.size) { mutableListOf<Position>() }
    private var currentPlayer = 0;

    private fun getPlayerCount(): Int {
        return boats.size;
    }

    fun getLoser(): Int? {
        for (playerNr in 0 until getPlayerCount()) {
            if (boats[playerNr].all { boat -> boat.isSunken() }) {
                return playerNr;
            }
        }

        return null;
    }

    fun render(playerNr: Int): String {
        val renderingGrid = RenderingGrid(gridWidth, gridHeight);
        boats[playerNr].forEach { boat -> boat.render(renderingGrid) };
        misfires[playerNr].forEach { misfire -> renderingGrid.setAtPosition(misfire, "\uD83D\uDCA5") };

        return renderingGrid.render();
    }

    fun fire(position: Position) {
        val missed = !boats[currentPlayer].map { boat -> boat.attemptHit(position) }.contains(true);

        if (missed) {
            misfires[currentPlayer].add(position);
        }

        println("Player ${currentPlayer} was shot at at ${position} and was ${if (missed) "missed" else "hit"}")

        if (!(hard && !missed)) {
            currentPlayer = (currentPlayer + 1) % getPlayerCount();
        }

        println("It's player ${currentPlayer}'s turn to be shot at now");
    }
}