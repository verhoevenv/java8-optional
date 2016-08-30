package com.github.verhoevenv.java8.optional

import java.util.Optional

import org.scalatest._

class EssentialOptionalDifferenceSpec extends FlatSpec with Matchers {

  "A java Optional" should "map null to Optional.empty" in {
    val javaOptional = Optional.of("a value")

    val result = javaOptional.map(s2j(_ => null))

    result.isPresent shouldBe false
  }

  "A scala Option" should "map null to Some(null)" in {
    val scalaOptional = Option("a value")

    val result = scalaOptional.map(_ => null)

    result.isDefined shouldBe true
    result shouldBe Some(null)
  }


   def s2j[A,R](fn: (A) => R ) : java.util.function.Function[A,R] =
    new java.util.function.Function[A,R] {
      override def apply(t: A): R = fn(t)
  }
}