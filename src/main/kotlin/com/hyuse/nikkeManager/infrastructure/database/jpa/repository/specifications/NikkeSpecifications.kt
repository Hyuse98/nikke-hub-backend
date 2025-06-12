package com.hyuse.nikkeManager.infrastructure.database.jpa.repository.specifications

import com.hyuse.nikkeManager.domain.enums.*
import com.hyuse.nikkeManager.domain.entities.Nikke
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object NikkeSpecifications {

    fun byFilters(
        rarity: Rarity?,
        ownedStatus: OwnedStatus?,
        burstType: BurstType?,
        company: Company?,
        code: Code?,
        weapon: Weapon?,
        nikkeClass: NikkeClass?,
        cube: Cubes?
    ): Specification<Nikke> {
        return Specification { root, query, cb ->
            val predicates = mutableListOf<Predicate>()

            rarity?.let {
                predicates.add(cb.equal(root.get<Rarity>("rarity"), it))
            }
            ownedStatus?.let {
                predicates.add(cb.equal(root.get<OwnedStatus>("ownedStatus"), it))
            }
            burstType?.let {
                predicates.add(cb.equal(root.get<BurstType>("burstType"), it))
            }
            company?.let {
                predicates.add(cb.equal(root.get<Company>("company"), it))
            }
            code?.let {
                predicates.add(cb.equal(root.get<Code>("code"), it))
            }
            weapon?.let {
                predicates.add(cb.equal(root.get<Weapon>("weapon"), it))
            }
            nikkeClass?.let {
                predicates.add(cb.equal(root.get<NikkeClass>("nikkeClass"), it))
            }
            cube?.let {
                predicates.add(cb.equal(root.get<Cubes>("cube"), it))
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}