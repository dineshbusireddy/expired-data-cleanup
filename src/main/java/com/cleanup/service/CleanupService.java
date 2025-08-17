package com.cleanup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CleanupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int cleanExpiredIndividuals() {
        return jdbcTemplate.update("DELETE FROM INDIVIDUAL WHERE status = 'EXPIRED'");
    }

    public int findExpiredRecords() {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM INDIVIDUAL WHERE status = 'EXPIRED'", Integer.class);
        } catch (Exception e) {
            return -1;
        }
    }


    public int findAvilableRecords() {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM INDIVIDUAL", Integer.class);
        } catch (Exception e) {
            return -1;
        }
    }
}

