package com.dinesh.expiredcleanup.individualdatacleanup.config;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
public class DataSourceConfig {

    @Value("${SqlDbUrl}")
    private String jdbcUrl;

    @Value("${azure.client-id}")
    private String clientId;

    @Value("${azure.client-secret}")
    private String clientSecret;

    @Value("${azure.tenant-id}")
    private String tenantId;

    private static final String SCOPE = "https://database.windows.net/.default";

    private String getAccessToken() throws MalformedURLException, InterruptedException, ExecutionException {
        ConfidentialClientApplication cca = ConfidentialClientApplication.builder(
                        clientId,
                        ClientCredentialFactory.createFromSecret(clientSecret))
                .authority("https://login.microsoftonline.com/" + tenantId)
                .build();

        CompletableFuture<IAuthenticationResult> future = cca.acquireToken(
                ClientCredentialParameters.builder(
                                Collections.singleton(SCOPE))
                        .build());

        return future.get().accessToken();
    }

    @Bean
    public DataSource dataSource() throws MalformedURLException, InterruptedException, ExecutionException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setURL(jdbcUrl);
        ds.setAccessToken(getAccessToken());
        return ds;
    }
}
