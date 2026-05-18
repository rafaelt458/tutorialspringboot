package com.laboratorio.springboot72.config;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class McpCapacities {
    @Bean
    public McpSyncClient firstClient(List<McpSyncClient> clients) {
        log.info("Número de conexiones clientes encontradas: {}", clients.size());
        if (clients.isEmpty()) {
            log.info("No hay conexiones clientes MCP registradas");
            return null;
        }

        for (McpSyncClient client : clients) {
            McpSchema.Implementation implementation = client.getServerInfo();
            log.info("Se ha descubierto un servidor con nombre: {} y versión: {}",
                    implementation.name(), implementation.version());
        }

        return clients.getFirst();
    }

    @Bean
    public int serverCompletions(McpSyncClient client) {
        McpSchema.ListToolsResult tools = client.listTools();
        List<McpSchema.Tool> toolList = tools.tools();
        int nTools = toolList.size();

        log.info("Número de herramientas disponibles en el servidor MCP: {}", nTools);
        int i = 1;
        for (McpSchema.Tool tool : toolList) {
            log.info("Herramienta {}: {} - {}", i, tool.name(), tool.description());
            i++;
        }

        return nTools;
    }

    @Bean
    public int serverResources(McpSyncClient client) {
        McpSchema.ListResourcesResult resources = client.listResources();
        List<McpSchema.Resource> resourcesList = resources.resources();
        int nResources = resourcesList.size();

        log.info("Número de recursos disponibles en el servidor MCP: {}", nResources);
        int i = 1;
        for (McpSchema.Resource resource : resourcesList) {
            log.info("Recursos {}: {} - {}", i, resource.name(), resource.description());
            i++;
        }

        return nResources;
    }

    @Bean
    public int serverPrompts(McpSyncClient client) {
        McpSchema.ListPromptsResult prompts = client.listPrompts();
        List<McpSchema.Prompt> promptList = prompts.prompts();
        int nPrompts = promptList.size();

        log.info("Número de prompts disponibles en el servidor MCP: {}", nPrompts);
        int i = 1;
        for (McpSchema.Prompt prompt : promptList) {
            log.info("Prompt {}: {} - {}", i, prompt.name(), prompt.description());
            i++;
        }

        return nPrompts;
    }
}