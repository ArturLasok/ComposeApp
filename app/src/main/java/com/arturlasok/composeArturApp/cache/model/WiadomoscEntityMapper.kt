package com.arturlasok.composeArturApp.cache.model

import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.domain.util.DomainMapper



class WiadomoscEntityMapper: DomainMapper<WiadomoscEntity, Wiadomosc> {

    override fun mapToDomainModel(model: WiadomoscEntity): Wiadomosc {
        return Wiadomosc(
            domid = model.cacheid,
            domtytul = model.cachetytul,
            domtresc = model.cachetresc,
            domurl = model.cacheurl,
            domimg = model.cacheimg
        )
    }

    override fun mapFromDomainModel(domainModel: Wiadomosc): WiadomoscEntity {
        return WiadomoscEntity(
            cacheid = domainModel.domid,
            cachetytul = domainModel.domtytul,
            cachetresc = domainModel.domtresc,
            cacheurl = domainModel.domurl,
            cacheimg = domainModel.domimg
        )
    }
    fun fromEntityToDomainList(initial: List<WiadomoscEntity>) : List<Wiadomosc>
    {
        return initial.map { mapToDomainModel(it) }
    }
    fun fromDomainToEntityList(initial: List<Wiadomosc>) : List<WiadomoscEntity>
    {
        return initial.map { mapFromDomainModel(it) }
    }
}