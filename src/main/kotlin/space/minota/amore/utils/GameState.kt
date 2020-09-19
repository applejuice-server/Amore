package space.minota.amore.utils

enum class GameState {
    LOBBY, WAITING, INGAME;

    companion object {
        /**
         * Gets the current state.
         * @return The state
         */
        fun setState(state: GameState) {
            currentState = state
            Settings.instance.data?.set("game.state", state.name.toUpperCase())
            Settings.instance.saveData()
        }

        var currentState: GameState? = null

    }
}