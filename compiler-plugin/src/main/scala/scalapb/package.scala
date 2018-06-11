import protocbridge.JvmGenerator
import com.google.protobuf.Descriptors.ServiceDescriptor
import scalapb.compiler.{GeneratorParams, DefaultGrpcServicePrinter, GrpcServicePrinter, GrpcParams}

package object scalapb {
  def gen(
      flatPackage: Boolean = false,
      javaConversions: Boolean = false,
      grpc: Boolean = true,
      singleLineToProtoString: Boolean = false,
      asciiFormatToString: Boolean = false,
      grpcGen: (ServiceDescriptor, GeneratorParams) => GrpcServicePrinter = {
        (sd, params) => new DefaultGrpcServicePrinter(sd, params)
      }
  ): (JvmGenerator, Seq[String]) =
    (
      JvmGenerator("scala", new ScalaPbCodeGenerator(Some(GrpcParams(grpcGen = grpcGen)))),
      Seq(
        "flat_package"                -> flatPackage,
        "java_conversions"            -> javaConversions,
        "grpc"                        -> grpc,
        "single_line_to_proto_string" -> singleLineToProtoString,
        "ascii_format_to_string"      -> asciiFormatToString
      ).collect { case (name, v) if v => name }
    )
}
