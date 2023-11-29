package typescript.example.client.gradle;

import java.util.List;
import software.amazon.smithy.model.shapes.OperationShape;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.typescript.codegen.ApplicationProtocol;
import software.amazon.smithy.typescript.codegen.integration.ProtocolGenerator;
import software.amazon.smithy.typescript.codegen.integration.TypeScriptIntegration;

public class ExampleClientProtocolGeneratorIntegration implements TypeScriptIntegration {
    // ProtocolGenerator implementation is inline for brevity, but should be in its
    // own file
    private static class ExampleClientProtocolGenerator implements ProtocolGenerator {
        // Protocol generator for a @example.client#protocol protocol trait
        @Override
        public ShapeId getProtocol() {
            return ShapeId.from("example.client#protocol");
        }
        // Implement ProtocolGenerator methods ...

        @Override
        public ApplicationProtocol getApplicationProtocol() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getApplicationProtocol'");
        }

        @Override
        public void generateRequestSerializers(GenerationContext context) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateRequestSerializers'");
        }

        @Override
        public void generateRequestDeserializers(GenerationContext context) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateRequestDeserializers'");
        }

        @Override
        public void generateResponseSerializers(GenerationContext context) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateResponseSerializers'");
        }

        @Override
        public void generateFrameworkErrorSerializer(GenerationContext serverContext) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateFrameworkErrorSerializer'");
        }

        @Override
        public void generateServiceHandlerFactory(GenerationContext context) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateServiceHandlerFactory'");
        }

        @Override
        public void generateOperationHandlerFactory(GenerationContext context, OperationShape operation) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateOperationHandlerFactory'");
        }

        @Override
        public void generateResponseDeserializers(GenerationContext context) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateResponseDeserializers'");
        }

        @Override
        public void generateProtocolTests(GenerationContext context) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'generateProtocolTests'");
        }
    }

    @Override
    public List<ProtocolGenerator> getProtocolGenerators() {
        return List.of(new ExampleClientProtocolGenerator());
    }
}
