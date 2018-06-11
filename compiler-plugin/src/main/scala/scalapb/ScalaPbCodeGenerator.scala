package scalapb

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest
import scalapb.compiler.{ProtobufGenerator, GrpcParams}
import scalapb.options.compiler.Scalapb
import protocbridge.{ProtocCodeGenerator, Artifact}

class ScalaPbCodeGenerator(gpcParams: Option[GrpcParams]) extends ProtocCodeGenerator {
  override def run(req: Array[Byte]): Array[Byte] = {
    val registry = ExtensionRegistry.newInstance()
    Scalapb.registerAllExtensions(registry)
    val request = CodeGeneratorRequest.parseFrom(req, registry)
    val grpcP = gpcParams.getOrElse(GrpcParams())
    ProtobufGenerator.handleCodeGeneratorRequest(request, grpcP).toByteArray
  }

  override def suggestedDependencies: Seq[Artifact] = Seq(
    Artifact(
      "com.thesamet.scalapb",
      "scalapb-runtime",
      scalapb.compiler.Version.scalapbVersion,
      crossVersion = true
    )
  )
}

object ScalaPbCodeGenerator extends ScalaPbCodeGenerator(None)
