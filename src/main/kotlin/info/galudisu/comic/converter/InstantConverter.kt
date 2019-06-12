package info.galudisu.comic.converter

import org.jooq.Converter
import java.sql.Timestamp
import java.time.Instant

internal class InstantConverter : Converter<Timestamp, Instant> {
    override fun from(databaseObject: Timestamp?): Instant? = databaseObject?.toInstant()
    override fun to(userObject: Instant?): Timestamp? = userObject?.let { Timestamp.from(it) }
    override fun fromType(): Class<Timestamp> = Timestamp::class.java
    override fun toType(): Class<Instant> = Instant::class.java
}