package space.minota.amore.utils

enum class GameState {
    LOBBY, WAITING, INGAME;

    companion object {
        /**
         * Gets the current state.
         * @return The state
         */
        var state: GameState? = null
            private set

        private val currentState: GameState? = null

        /**
         * Sets the current state to be #.
         * @param state the state setting it to.
         */
        @JvmName("setState1")
        fun setState(state: GameState) {
            Companion.state = state
            Settings.getInstance()?.getData()?.set("game.state", state.name.toUpperCase())
            Settings.getInstance()?.saveData()
        }

        /**
         * Checks if the state is #.
         * @param state The state checking.
         * @return True if it's the given state.
         */
        fun isState(state: GameState): Boolean {
            return Companion.state == state
        }

        @JvmName("getState1")
        fun getState(): GameState? {
            return currentState
        }
    }
}