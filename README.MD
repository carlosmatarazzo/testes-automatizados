# README.ME

Para executar os testes é necessário:

Subir a Aplicação API Java Spring Boot
1. Descompactar a aplicação alarme_cicd_mysql.zip; 
2. Executar o Docker Desktop;
3. Acessar a pasta alarme_cicd_mysql pelo PowerShell;
4. Montar a imagem da aplicação:
```
docker compose up
```

Executar os testes
1. Descompactar a aplicação testes-automatizados.zip;
2. Acessar a pasta testes-automatizados;
3. Executar a rotina de testes:
```
mvn clean test
```
4. Visualizar arquivo:
```
/target/cucumber-reports.html
```