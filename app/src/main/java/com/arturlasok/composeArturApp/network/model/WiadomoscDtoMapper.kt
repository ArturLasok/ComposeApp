package com.arturlasok.composeArturApp.network.model


import com.arturlasok.composeArturApp.domain.model.Wiadomosc
import com.arturlasok.composeArturApp.domain.util.DomainMapper

class WiadomoscDtoMapper: DomainMapper<WiadomoscDto, Wiadomosc> {

    override fun mapToDomainModel(model: WiadomoscDto): Wiadomosc {
        return Wiadomosc(
            domid = model.netid,
            domtytul = model.nettytul,
            domtresc = model.nettresc,
            domurl = model.neturl,
            domimg = model.netimg
        )
    }

    override fun mapFromDomainModel(domainModel: Wiadomosc): WiadomoscDto {
        return WiadomoscDto(
            netid = domainModel.domid,
            nettytul = domainModel.domtytul,
            nettresc = domainModel.domtresc,
            neturl = domainModel.domurl,
            netimg = domainModel.domimg
        )
    }
    fun fromDtoToDomainList(initial: List<WiadomoscDto>) : List<Wiadomosc>
    {
        return initial.map { mapToDomainModel(it) }
    }
    fun fromDomainToDtoList(initial: List<Wiadomosc>) : List<WiadomoscDto>
    {
        return initial.map { mapFromDomainModel(it) }
    }
}