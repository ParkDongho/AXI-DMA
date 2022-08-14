package axi

case class AXI4BundleParameters(
  addrBits: Int,
  dataBits: Int,
  idBits:   Int,
  echoFields:     Seq[BundleFieldBase] = Nil,
  requestFields:  Seq[BundleFieldBase] = Nil,
  responseFields: Seq[BundleFieldBase] = Nil
){

  val lenBits   = 8
  val sizeBits  = 2
  val burstBits = 2
  val lockBits  = 2
  val cacheBits = 3
  val protBits  = 3
  val qosBits   = 4
  val respBits  = 2

}
