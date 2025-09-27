# Fase de build (criação do JAR)
FROM eclipse-temurin:17-jdk AS build
WORKDIR /work/

# Atualiza pacotes e instala o Maven
RUN apt update && apt install -y maven

# Copia o código-fonte para dentro do container
COPY . /work/

# Executa o Maven para empacotar a aplicação
RUN mvn clean package -DskipTests

# Fase de execução (criação da imagem final)
FROM eclipse-temurin:17-jre

# Define o diretório de trabalho no container
WORKDIR /app/

# Copia o JAR gerado na fase de build
COPY --from=build /work/target/*.jar /app/app.jar

# Define a variável de ambiente para a porta
ENV PORT=8080

# Expõe a porta configurada
EXPOSE ${PORT}

# Define o comando de entrada para rodar o JAR do Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
