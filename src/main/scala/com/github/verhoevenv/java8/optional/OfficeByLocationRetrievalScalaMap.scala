package com.github.verhoevenv.java8.optional

import com.github.verhoevenv.java8.optional.domain.{Location, Office, OfficeRepository}

class OfficeByLocationRetrievalScalaMap(officeRepository: OfficeRepository) extends OfficeByLocationRetrieval {

  override def retrieveOfficeForLocation(location: Location): Office = retrieveOfficeForLocationImpl(location)

  def retrieveOfficeForLocationImpl = implementationWithMap

  def implementationWithMap: (Location => Office) = { location =>
    Option(location)
      .map({loc:Location => officeRepository.findFor(loc.getZipCode)})
      .getOrElse(retrieveDefaultOffice)
  }

  private def retrieveDefaultOffice: Office = officeRepository.findWithLowestId

}
