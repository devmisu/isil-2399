package pe.solera.repository.util

interface EntityMapper <Entity, DomainModel> {

    fun mapToEntity(domainModel: DomainModel) : Entity

}