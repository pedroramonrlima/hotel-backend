API do Sistema de Hotel
========================

API para gerenciar reservas, tipos de quartos, hóspedes e outros recursos em um sistema de hotel. Este projeto está em fase de desenvolvimento e destina-se a fornecer uma base sólida para operações de gerenciamento de hotel, incluindo reservas, controle de disponibilidade de quartos e informações de hóspedes.

Tecnologias Utilizadas
----------------------
- Java com Spring Boot
- PostgreSQL para banco de dados
- R2DBC para comunicação reativa com o banco de dados
- WebFlux para suporte a operações assíncronas e não-bloqueantes
- Docker (opcional) para ambientes de desenvolvimento consistentes

Pré-requisitos
--------------
Certifique-se de que você tenha as seguintes ferramentas instaladas:
- Java 17 ou superior
- Maven
- Docker (opcional, caso deseje rodar o PostgreSQL em contêiner)

Configuração
------------
1. Clone o repositório:
   ```bash
   git clone git@github.com:pedroramonrlima/hotel-backend.git
   ```
2. Instale as dependências e compile o projeto:
   ```bash
   cd hotel-backend
   mvn clean install
