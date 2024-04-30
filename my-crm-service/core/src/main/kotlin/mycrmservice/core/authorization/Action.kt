package mycrmservice.core.authorization

/**
 * 操作
 */
sealed class Action(
    /**
     * 値
     */
    val value: String,
) {
    /**
     * Read
     */
    data object Read : Action("read")

    /**
     * Write
     */
    data object Write : Action("write")
}
