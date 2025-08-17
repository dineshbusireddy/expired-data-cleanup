package com.cleanup.function;

import com.cleanup.service.CleanupService;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HttpFunction {

    @Value("${env}")
    private String env;

    @Autowired
    private CleanupService cleanupService;

    @FunctionName("expiredRecordsCheck")
    public String expiredRecordsCheck(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        return "Azure Functions! Available Records: " + cleanupService.findAvilableRecords()
                +", Expired Records: " + cleanupService.findExpiredRecords() + ", Env:" + env;
    }
}
