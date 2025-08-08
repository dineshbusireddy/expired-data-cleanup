# Azure Function SQL Cleanup - Java Spring Boot Timer Trigger Example

This project demonstrates how to create a Java Azure Function using Spring Boot, triggered daily by an Azure Functions Timer Trigger, that securely connects to an Azure SQL Database using Azure AD authentication and deletes records with `status = 'EXPIRED'`.

---

## Features
- Runs once daily at 2 AM UTC using Azure Functions Timer Trigger.
- Uses Azure AD (MSAL) for secure access to Azure SQL Database.
- Built with Spring Boot inside Azure Functions.
- Cost efficient: serverless architecture only runs on schedule.
- Complete Maven setup for build, local development, and deployment.

---

## Prerequisites
- Java 21 or later installed.
- Maven installed.
- Azure Functions Core Tools installed (for local development).
- Azure Subscription.
- Azure Function App created.
- Azure SQL Database created.
- Azure AD Service Principal (client ID, secret, tenant ID) with database access permissions.
- Network access to Azure SQL Database from local machine (for local testing).

---

## Configuration

### 1. `application.properties`

Set your database connection and Azure AD credentials here or, preferably, use Azure Function App Settings or Azure Key Vault for managing secrets.

SqlDbUrl=jdbc:sqlserver://your-sql-server.database.windows.net:1433;database=your-db-name
azure.client-id=your-client-id
azure.client-secret=your-client-secret
azure.tenant-id=your-tenant-id

text

### 2. `pom.xml`

Dependencies include Azure Functions Java library 3.0.0+, Spring Boot 3.1.4, Microsoft SQL Server JDBC driver 12.8.0.jre11 (compatible with Java 21), and MSAL4J for OAuth2 token acquisition.

---

## Development Steps

### Local Build and Run

1. Clone or create the project with the above structure.
2. Set local environment variables or create `local.settings.json` file with your Azure and database credentials.
3. Run the following commands to build and run locally:

mvn clean package
mvn azure-functions:run

text

The function will execute the Timer Trigger immediately for test and thereafter once daily at 2 AM UTC.

### Deployment to Azure

1. Update `resourceGroup`, `appName` in `pom.xml` for your Azure subscription.
2. Deploy with:

mvn azure-functions:deploy

text

3. Confirm the function runs on schedule in Azure Portal logs.

---

## Security Recommendations

- Use Azure Managed Identity instead of client secrets if possible.
- Store secrets securely (Azure Key Vault or Function App Configuration).
- Grant least privileges to your Azure AD app on the Azure SQL Database.
- Do not commit secrets to version control.

---

## Notes

- The SQL Server JDBC driver version `12.8.0.jre11` is recommended for Java 21 environments.
- Adjust the cron schedule `0 0 2 * * *` in `CleanupFunction.java` as needed for your timing.
- Monitor execution logs in Azure Portal or via Azure Functions Core Tools local logs.

---

## References

- Azure Functions Java Developer Guide
- Azure SQL Database authentication options
- Microsoft Authentication Library (MSAL) for Java
- Azure Functions Maven Plugin documentation

---

## Support

For additional help with:

- Using Managed Identity authentication instead of client secrets.
- Optimizing connection pooling or using Spring Data JPA.
- Customizing schedules or function triggers.

Feel free to raise issues or ask!

---

Thank you for using this example to build your scheduled Azure Function for cleaning expired individual data records securely and efficiently.