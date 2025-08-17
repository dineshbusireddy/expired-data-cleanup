package com.cleanup.function;

import com.cleanup.service.CleanupService;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CleanupTriggerFunction {

    @Autowired
    private CleanupService cleanupService;

    @FunctionName("cleanupTimer")
    public void run(@TimerTrigger(name = "timerInfo", schedule = "%timer.cron%")
            String timerInfo, final ExecutionContext context) {
        int count = cleanupService.cleanExpiredIndividuals();
        context.getLogger().info("Cleared Expired record count: " + count);
    }

}
