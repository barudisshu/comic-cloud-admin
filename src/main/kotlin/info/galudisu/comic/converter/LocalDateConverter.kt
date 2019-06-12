package info.galudisu.comic.converter

import org.jooq.Converter
import java.sql.Date
import java.time.LocalDate

internal class LocalDateConverter : Converter<Date, LocalDate> {
    override fun from(databaseObject: Date?): LocalDate? = databaseObject?.toLocalDate()
    override fun to(userObject: LocalDate?): Date? = userObject?.let { Date.valueOf(it) }
    override fun fromType(): Class<Date> = Date::class.java
    override fun toType(): Class<LocalDate> = LocalDate::class.java
}