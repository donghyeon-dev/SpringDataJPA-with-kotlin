package autocat.sample.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Transient
import org.springframework.data.domain.Persistable
import java.util.*

@MappedSuperclass
abstract class BaseEntity : Persistable<UUID> {

    @Id
    private val id: UUID = Generators.timeBasedEpochGenerator().generate()

    @Transient
    private var _isNew: Boolean = true

    override fun isNew(): Boolean = _isNew

    override fun getId(): UUID = id

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
