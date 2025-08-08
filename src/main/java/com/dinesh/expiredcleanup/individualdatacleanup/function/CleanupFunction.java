package com.dinesh.expiredcleanup.individualdatacleanup.function;

import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.microsoft.azure.functions.ExecutionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CleanupFunction {

    @Autowired
    private DataSource dataSource;

    @FunctionName("DeleteExpiredRecords")
    public void run(
            @TimerTrigger(name = "timerInfo", schedule = "0 0 2 * * *") String timerInfo,
            final ExecutionContext context) {

        context.getLogger().info("Timer trigger started: " + timerInfo);

        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM your_table WHERE status = 'EXPIRED'";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                int deletedRecords = ps.executeUpdate();
                context.getLogger().info("Deleted " + deletedRecords + " expired records.");
            }
        } catch (SQLException e) {
            context.getLogger().severe("Database error: " + e.getMessage());
        }

        context.getLogger().info("Timer trigger completed.");
    }
}
