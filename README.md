# backend-SIGA

## Informações de desenvolvimento

A dependência "mapper" que realiza as transferências de objeto entidade/DTO não faz um rebuild correto quando os objetos mudam, então é necessário excluir a pasta "target" para que as funcionalidades sejam implementadas corretamente.

## Como levantar esse servidor localmente?

É necessário preencher as variáveis de ambiente **"spring.datasource.username"** e **"spring.datasource.password"**, no arquivo **"src/main/resources/application.properties"**.

O corpo desse arquivo é:

    spring.datasource.url=jdbc:mariadb://146.164.2.125:3306/sgo
    spring.datasource.username=SEU_USUARIO
    spring.datasource.password=SUA_SENHA

    spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    
    spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
    spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    spring.jpa.hibernate.ddl-auto=validate

O usuário é seu username, sem @, do gitlab (@exemplousuario) e a senha é igual ao usuário, mas convertida em base64.

