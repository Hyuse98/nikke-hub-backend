# Nikke Hub

Nikke Hub é um projeto pessoal de estudo feito com proposito de botar em pratica varios conceitos que aprendi na minha graduação em ciencias da computação.

Falando sobre o projeto, o intuiro dele é facilitar a visualização e acompanhamento dos personagens de um jogo gacha chamado GODDESS OF VICTORY: NIKKE, onde sera é possivel (porque eu ainda não fiz :p) adicionar os personagens a sua coleção e assim conseguir fazer as operações de atualizar os dados delas, leveis de skills, equipamentos e etc, e ainda não tem uma interface por enquanto. 

## Tecnologias Utilizadas

- Linguagem principal: Kotlin
- Framework: Spring Boot, Spring Cloud, Spring Data, Hibernate
- Banco de dados: PostgreSQL, MongoDB, Redis
- Ferramentas: Docker, Kubernetes
## Requisitos

```
Java 17
Kotlin 1.9.25
Spring 3.4.2
Spring Cloud 2024.0.0
Docker
Kubernetes
```

## Instalação

```bash
# Spring Cloud Netflix Eureka Server
git clone https://github.com/Hyuse98/Nikke-Hub-Service-Registry.git

# Inicie Container PostgreSQL

# Inicie Container Redis

# Spring Cloud Config Server
git clone https://github.com/Hyuse98/Nikke-Hub-Config-Server.git

# Nikke Hub Backend
git clone https://github.com/Hyuse98/Nikke-Hub-Backend.git

# Spring Cloud Gateway
git clone https://github.com/Hyuse98/Nikke-Hub-Gateway.git
```

## Configuração

### variáveis de ambiente

As variáveis de ambiente estão sendo gerenciadas pelo kubernetes, com os arquivos presentes no diretorio 'k8s' respectivos de cada micro serviço

## Executando o Projeto

```bash
# Fazer uma build limpa do projeto
./gradlew clean build

# Construir a imagem Docker
docker image build -t user/reponame:tag .

# Subir a imagem Docker para o Registry
docker image push user/reponame:tag

# Ir para o diretorio do kubernetes
cd k8s

# Adicionar as secrets
kubectl apply -f secret.yaml

# Iniciar o PostgreSQL
kubectl apply -f postgres-init-script.yaml
kubectl apply -f postgres-statefulset.yaml
kubectl apply -f postgres-service.yaml

# Iniciar o Redis
kubectl apply -f redis-pvc.yaml
kubectl apply -f redis-deployment.yaml
 kubectl apply -fredis-service.yaml

# Esperar tudo estar pronto
# Iniciar o Backend
kubectl apply -f backend-configmap.yaml
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
```

## Estrutura do Projeto

```
src/
├── config/                             # Configurações
├── controller/                         # Controller Rest
├── dto/                                # Classes de Dados
├── enums                               # Classes de Enumeradores
├── exceptions                          # Classes de Exceções Custons
├── handler/                            # Classes de Manipulação de Exceções
├── model/                              # Classes Modelos e Entidades
├── repository/                         # Classes Responsaveis pela Persistencia
├── services/                           # Classes de Regras de Negocios
└── NikkeManagerApplication.kt          # Arquivo principal
```

## Endpoints API

### Nikkes

- `POST /api/nikke`             - Cria uma nova nikke
- `GET /api/nikke`              - Lista todas as nikkes
- `GET /api/nikke/filtered`     - Lista todos as nikkes filtrados com parametros
- `GET /api/nikke/:name`        - Retorna uma nikke específica
- `PUT /api/nikke/:name`        - Atualiza dados de uma nikke
- `DELETE /api/nikke/:name`     - Remove uma nikke

### Dolls

- `POST /api/doll`              - Cria uma nova Doll
- `GET /api/doll`               - Lista todas as Dolls
- `GET /api/doll/search`        - Retorna uma Doll que corresponde aos parametros passados
- `GET /api/doll/:id`           - Retorna uma Doll dado o id
- `DELETE /api/doll/:id`        - Remove uma Doll

## Testes

Para executar os testes em CLI

Esta adicionado o plugin jacoco para exibir relatorio e acompanha a cobertura de teste que esta configurado para 80%
```bash
./gradlew test
```

## Contribuição

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adicionando nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a [GNU General Public License v3.0](LICENSE.md).