package com.github.verhoevenv.java8.optional

import com.github.verhoevenv.java8.optional.domain.{Location, Office, OfficeRepository}

class OfficeByLocationRetrievalScalaFlatMap(officeRepository: OfficeRepository) extends OfficeByLocationRetrieval {

  override def retrieveOfficeForLocation(location: Location): Office = retrieveOfficeForLocationImpl(location)

  def retrieveOfficeForLocationImpl = implementationWithFlatMap

  def implementationWithFlatMap: (Location => Office) = { location =>
    Option(location)
      .flatMap({loc:Location => Option(officeRepository.findFor(loc.getZipCode))})
      .getOrElse(retrieveDefaultOffice)
  }

  private def retrieveDefaultOffice: Office = officeRepository.findWithLowestId

}
