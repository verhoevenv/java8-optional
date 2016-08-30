package com.github.verhoevenv.java8.optional

import com.github.verhoevenv.java8.optional.domain.{Location, Office, OfficeRepository}

class OfficeByLocationRetrievalScalaMonadic(officeRepository: OfficeRepository) extends OfficeByLocationRetrieval {

  override def retrieveOfficeForLocation(location: Location): Office = retrieveOfficeForLocationImpl(location)

  def retrieveOfficeForLocationImpl = implementationMonadic

  def implementationMonadic: (Location => Office) = { location =>
    val office: Option[Office] = for {
      loc <- Option(location)
      office <- Option(officeRepository.findFor(loc.getZipCode))
    } yield office
    office.getOrElse(retrieveDefaultOffice)
  }

  private def retrieveDefaultOffice: Office = officeRepository.findWithLowestId

}
