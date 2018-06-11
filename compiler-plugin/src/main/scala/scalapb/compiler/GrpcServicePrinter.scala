package scalapb.compiler

trait GrpcServicePrinter {

  def printService(printer: FunctionalPrinter): FunctionalPrinter

}
