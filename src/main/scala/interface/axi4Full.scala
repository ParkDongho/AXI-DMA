package axi

import chisel3._
import chisel3.util._


 /**
   *  ='''Overview'''=
   *  AXI4 Full Master IO with Read and Write Channel
   *  
   *
   *  ='''Structure'''=
   *  - [[axi.AXI4FullIO AXI4 Full IO]]
   *    - [[axi.AXI4FullWriteIO Write IO]]
   *      - [[axi.AXI4WriteAddressChannel AXI4 Write Channel]]
   *      - [[axi.AXI4WriteDataChannel    AXI4 Read Channel]]
   *      - [[axi.AXI4WriteResponseChannel AXI4 Response Channel]]
   *    - [[axi.AXI4FullReadIO Read IO]]
   *      - [[axi.AXI4ReadAddressChannel Read Address Channel]]
   *      - [[axi.AXI4ReadDataChannel    Read Data Channel]]
   *
   *  ='''Examples'''=
   *
   *  {{{
   *  val io = new AXI4FullIO(
   *    writeDataWidth, writeAddrWidth, 
   *    readDataWidth, radAddrWidth,
   *    userReqWidth
   *  ) 
   *  //Master IO
   *  }}}
   *      
   *  {{{
   *  val io = Flipped(new AXI4FullIO(
   *    writeDataWidth, writeAddrWidth, 
   *    readDataWidth, radAddrWidth,
   *    userReqWidth
   *  )) 
   *  //Slave IO
   *  }}}
   *
   *  ='''Revision History'''=
   *  - 1.0 : initial version
   *
   *  @param writeDataWidth is WDATA Width
   *  @param writeAddrWidth is WADDR Width
   *  @param readDataWidth is RDATA Width
   *  @param readAddrWidth is RADDR Width
   *  @param userReqWidth is xxUSER Width
   */
class AXI4FullIO(params: AXI4BundleParameters) extends Bundle {
  val write = new AXI4FullWriteIO(params)
  val read =  new AXI4FullReadIO(params)
}

 /**
   * AXI4 Write Peripheral
   *
   */
class AXI4FullWriteIO(params: AXI4BundleParameters) extends Bundle {
  val address  = new IrrevocableIO(new AXI4WriteAddressChannel(params))
  val data     = new IrrevocableIO(new AXI4WriteDataChannel(params))
  val response = Flipped(new IrrevocableIO(new AXI4WriteResponseChannel(params)))
}

/**
  * AXI4 Read Peripheral
  *
  */
 class AXI4FullReadIO(params: AXI4BundleParameters) extends Bundle {
   val address = new IrrevocableIO(new AXI4ReadAddressChannel(params))
   val data    = new IrrevocableIO(new AXI4ReadDataChannel(params))
}

/**
  * AW(Address Write) Channel of AXI4 Protocol
  *
  */
class AXI4WriteAddressChannel(params: AXI4BundleParameters) extends Bundle {
  val awid     = UInt(params.idBits.W) 
  val awaddr   = UInt(params.addrBits.W)
  val awlen    = UInt(params.lenBits.W)
  val awsize   = UInt(params.sizeBits.W)
  val awburst  = UInt(params.burstBits.W)
  val awlock   = UInt(params.lockBits.W)
  val awcache  = UInt(params.cacheBits.W)
  val awprot   = UInt(params.protBits.W)
  val awqos    = UInt(params.qosBits.W)
  //val awregion = UInt(4.W)
  val awuser   = UInt(params.requestFields.filter(_.key.isControl).W)
}

/**
  * W(Write Data) of AXI4 Protocol
  *
  */
class AXI4WriteDataChannel(params: AXI4BundleParameters) extends Bundle{
  val wid   = UInt(params.idBits.W)
  val wdata = UInt(params.dataBits.W)
  val wstrb = UInt((params.dataBits/8).W)
  val wlast = Bool()
  val wuser = UInt(params.requestFields.filter(_.key.isData).W)
}

/**
  * Write Response Channel of AXI4 Protocol
  *
  */
class AXI4WriteResponseChannel(params: AXI4BundleParameters) extends Bundle{
  val bid   = UInt(params.idBits.W)
  val bresp = UInt(params.respBits.W)
  val buser = UInt(params.requestFields.filter(_.key.isData).W)
}

/**
  * Read Address Channel of AXI4 Protocol
  *
  */
class AXI4ReadAddressChannel(params: AXI4BundleParameters) extends Bundle{
  val arid     = UInt(params.idBits.W)
  val araadr   = UInt(params.addrBits.W)
  val arlen    = UInt(params.lenBits.W)
  val arsize   = UInt(params.sizeBits.W)
  val arburst  = UInt(params.burstBits.W)
  val arlock   = UInt(params.lockBits.W)
  val arcache  = UInt(params.cacheBits.W)
  val arprot   = UInt(params.protBits.W)
  //val arregion = UInt(4.W)
  val aruser   = UInt(params.requestFields.filter(_.key.isData).W)
}

/**
  * Read Data Channel of AXI4 Protocol
  *
  */
class AXI4ReadDataChannel(params: AXI4BundleParameters) extends Bundle {
  val rid   = UInt(2.W)
  val rdata = UInt(params.dataBits.W)
  val rstrb = UInt((params.dataBits/8).W)
  val rlast = Bool()
  val ruser = UInt(params.requestFields.filter(_.key.isData).W)
}


