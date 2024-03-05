package mycrmservice.data.util

import kotlin.enums.EnumEntries

/**
 * 名前が一致する Enum の定数を返します。
 *
 * @param value 値
 * @return 名前が一致する定数が存在する場合は [T]。存在しない場合は null。
 */
inline fun <reified T : Enum<T>> EnumEntries<T>.valueOfOrNull(value: String) = this.firstOrNull { it.name == value }
