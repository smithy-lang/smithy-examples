package typescript.example.client.gradle;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.typescript.codegen.LanguageTarget;
import software.amazon.smithy.typescript.codegen.TypeScriptDependency;
import software.amazon.smithy.typescript.codegen.TypeScriptSettings;
import software.amazon.smithy.typescript.codegen.TypeScriptWriter;
import software.amazon.smithy.typescript.codegen.integration.RuntimeClientPlugin;
import software.amazon.smithy.typescript.codegen.integration.TypeScriptIntegration;
import software.amazon.smithy.typescript.codegen.integration.RuntimeClientPlugin.Convention;

public class ExampleClientEndpointResolutionIntegration implements TypeScriptIntegration {
    @Override
    public List<RuntimeClientPlugin> getClientPlugins() {
        return List.of(
            RuntimeClientPlugin.builder()
                .withConventions(
                    TypeScriptDependency.CONFIG_RESOLVER.dependency,
                    "CustomEndpoints",
                    Convention.HAS_CONFIG)
                .build());
    }

    @Override
    public Map<String, Consumer<TypeScriptWriter>> getRuntimeConfigWriters(
        TypeScriptSettings settings,
        Model model,
        SymbolProvider symbolProvider,
        LanguageTarget target
    ) {
        // Runtime config value also be specified per platform by using the `target`
        // argument, e.g.
        // if (target.equals(LanguageTarget.NODE)) { ... }
        if (target.equals(LanguageTarget.SHARED)) {
            // This example provides an arbitrary endpoint on the shared runtime config
            return Map.of("endpoint", w -> w.write("$S", "https://www.example.com"));
        }
        // No need to redefine endpoint for other targets since it's inherited from the
        // shared target
        return Collections.emptyMap();
    }
}
