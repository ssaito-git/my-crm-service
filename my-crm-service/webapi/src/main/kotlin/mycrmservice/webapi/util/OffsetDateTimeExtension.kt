package mycrmservice.webapi.util

import java.time.OffsetDateTime

/**
 * エポック (1970-01-01T00:00:00Z) からのミリ秒数に変換します。
 *
 * @return エポック (1970-01-01T00:00:00Z) からのミリ秒数
 */
fun OffsetDateTime.toEpochMilliSecond(): Long {
    return this.toInstant().toEpochMilli()
}
