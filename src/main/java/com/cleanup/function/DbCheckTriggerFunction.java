package com.cleanup.function;

import com.cleanup.service.CleanupService;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbCheckTriggerFunction {

    @Autowired
    private CleanupService cleanupService;

    @FunctionName("expiredCountTimer")
    public void run(@TimerTrigger(name = "timerInfo", schedule = "%timer.dbcheck.cron%")
            String timerInfo, final ExecutionContext context) {
        int count = cleanupService.findExpiredRecords();
        context.getLogger().info("Expired Record Count: " + count);
        context.getLogger().info("DB connection test: " + (count >= 0 ? "OK" : "Failed"));
    }

}
