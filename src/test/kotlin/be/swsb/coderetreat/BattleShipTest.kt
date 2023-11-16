package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class BattleShipTest {

    @Test
    fun `renderAnEmptyOcean`() {
        val game = Game(6, 6, listOf(listOf()));

        assertThat(game.render(0)).isEqualTo(
                """
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent()
        )
    }

    @Test
    fun `aShipCanBePlacedHorizontally`() {
        val game = Game(
                6,
                6,
                listOf(listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(1, 0), Position(2, 0), Position(3, 0)))))
        );

        assertThat(game.render(0)).isEqualTo(
                """
                    🛳️️️️🛳️️️️🛳️️️️🛳️️️️🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());

    }

    @Test
    fun `aShipCanBePlacedHorizontallyAtAnderePositie`() {
        val game = Game(6, 6, listOf(listOf(Ship(Position(3, 4), listOf(Position(0, 0), Position(1, 0), Position(2, 0))))));

        assertThat(game.render(0)).isEqualTo(
                """
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🛳️️️️🛳️️️️🛳️️️️
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
    }

    @Test
    fun `A ship can be placed vertically`() {
        val game = Game(6, 6, listOf(listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2))))));

        assertThat(game.render(0)).isEqualTo(
                """
                    🛳️️️️🌊🌊🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());

    }

    @Test
    fun `We can fire and miss`() {
        val game = Game(6, 6, listOf(listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2))))));

        game.fire(Position(2, 1));

        assertThat(game.render(0)).isEqualTo(
                """
                    🛳️️️️🌊🌊🌊🌊🌊
                    🛳️️️️🌊💥🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
    }

    @Test
    fun `We can fire and hit`() {
        val game = Game(6, 6, listOf(listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2))))));

        game.fire(Position(0, 1));

        assertThat(game.render(0)).isEqualTo(
                """
                    🛳️️️️🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
    }

    @Test
    fun `We can fire and hit multiple times`() {
        val game = Game(6, 6, listOf(listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2))))));

        game.fire(Position(0, 1));
        game.fire(Position(0, 2));

        assertThat(game.render(0)).isEqualTo(
                """
                    🛳️️️️🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
    }

    @Test
    fun `We can sink a ship`() {
        val game = Game(6, 6, listOf(listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2))))));

        game.fire(Position(0, 1));
        game.fire(Position(0, 2));
        game.fire(Position(0, 0));

        assertThat(game.render(0)).isEqualTo(
                """
                    🏊🌊🌊🌊🌊🌊
                    🏊🌊🌊🌊🌊🌊
                    🏊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
    }

    @Test
    fun `A game can be multiplayer`() {
        val game = Game(6, 6, listOf(
                listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2)))),
                listOf(Ship(Position(0, 0), listOf(Position(0, 0)))),
        ));

        game.fire(Position(0, 1));
        game.fire(Position(0, 2));
        game.fire(Position(0, 0));

        assertThat(game.render(0)).isEqualTo(
                """
                    💥🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
        assertThat(game.render(1)).isEqualTo(
                """
                    🛳️️️️🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());

        assertThat(game.getLoser()).isEqualTo(null)
    }

    @Test
    fun `A game can be won`() {
        val game = Game(6, 6, listOf(
                listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2)))),
                listOf(Ship(Position(2, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2)))),
        ));

        game.fire(Position(0, 1));
        game.fire(Position(0, 2));
        game.fire(Position(0, 0));
        game.fire(Position(0, 0));
        game.fire(Position(0, 2));

        assertThat(game.render(0)).isEqualTo(
                """
                    🏊🌊🌊🌊🌊🌊
                    🏊🌊🌊🌊🌊🌊
                    🏊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
        assertThat(game.render(1)).isEqualTo(
                """
                    💥🌊🛳️️️️🌊🌊🌊
                    🌊🌊🛳️️️️🌊🌊🌊
                    💥🌊🛳️️️️🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());

        assertThat(game.getLoser()).isEqualTo(0)
    }

    @Test
    fun `A hard game allows for another shot if hit`() {
        val game = Game(6, 6, listOf(
                listOf(Ship(Position(0, 0), listOf(Position(0, 0), Position(0, 1), Position(0, 2)))),
                listOf(Ship(Position(0, 0), listOf(Position(0, 0)))),
        ), hard = true);

        // Supposed to be player 0 board
        game.fire(Position(0, 0));
        game.fire(Position(0, 3));

        // Supposed to be player 1 board
        game.fire(Position(0, 2));

        assertThat(game.render(0)).isEqualTo(
                """
                    💥🌊🌊🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    🛳️️️️🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());
        assertThat(game.render(1)).isEqualTo(
                """
                    🛳️️️️🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    💥🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                    🌊🌊🌊🌊🌊🌊
                """.trimIndent());

        assertThat(game.getLoser()).isEqualTo(null)
    }
}
