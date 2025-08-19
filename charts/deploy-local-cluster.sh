#!/bin/bash

# --- Configurações e Cores ---
# Interrompe o script se qualquer comando falhar.
set -e

# Define cores para uma saída mais legível.
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # Sem Cor

NAMESPACE="nikke-hub"

# --- Funções Auxiliares ---

# Função para verificar se um comando existe.
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Função para imprimir cabeçalhos.
print_header() {
    echo -e "\n${YELLOW}=== $1 ===${NC}"
}

# Função para verificar e criar o namespace.
setup_namespace() {
    print_header "0 - A configurar o Namespace"
    if kubectl get namespace "$NAMESPACE" > /dev/null 2>&1; then
        echo "Namespace '$NAMESPACE' já existe. A saltar a criação."
    else
        echo "A criar o namespace '$NAMESPACE'..."
        kubectl create namespace "$NAMESPACE"
        echo -e "${GREEN}Namespace '$NAMESPACE' criado com sucesso.${NC}"
    fi
}

# Função para fazer o deploy do chart de segredos de forma interativa.
deploy_secrets_chart() {
    print_header "2 - A configurar e a fazer o deploy dos Segredos"

    # Pede os segredos de forma interativa para não os guardar no histórico do shell.
    echo -n "Introduza o postgresUser: "
    read postgresUser
    echo -n "Introduza o postgresPassword: "
    read -sp "" postgresPassword
    echo
    echo -n "Introduza o springDatasourceUsername: "
    read springDatasourceUsername
    echo -n "Introduza o springDatasourcePassword: "
    read -sp "" springDatasourcePassword
    echo
    echo -n "Introduza o springFlywayUser: "
    read springFlywayUser
    echo -n "Introduza o springFlywayPassword: "
    read -sp "" springFlywayPassword
    echo

    # Faz o deploy do chart de segredos com os valores fornecidos.
    helm upgrade --install "nikke-hub-secrets" "./nikke-hub-secrets" \
        --namespace "$NAMESPACE" \
        --set "secrets.data.postgresUser=$postgresUser" \
        --set "secrets.data.postgresPassword=$postgresPassword" \
        --set "secrets.data.springDatasourceUsername=$springDatasourceUsername" \
        --set "secrets.data.springDatasourcePassword=$springDatasourcePassword" \
        --set "secrets.data.springFlywayUser=$springFlywayUser" \
        --set "secrets.data.springFlywayPassword=$springFlywayPassword" \
        --wait \
        --timeout "30s"

    echo -e "${GREEN}Deploy de 'nikke-hub-secrets' concluído com sucesso.${NC}"
}

# Função para fazer o deploy de um chart do Helm.
# Parâmetros: 1-Nome do Release, 2-Caminho do Chart, 3-Timeout
deploy_chart() {
    local release_name=$1
    local chart_path=$2
    local timeout=$3

    print_header "A fazer o deploy de: $release_name"
    helm upgrade --install "$release_name" "$chart_path" \
        --namespace "$NAMESPACE" \
        --wait \
        --timeout "$timeout"
    echo -e "${GREEN}Deploy de '$release_name' concluído com sucesso.${NC}"
}

# --- Script Principal ---

# Verificar se as ferramentas necessárias estão instaladas.
if ! command_exists kubectl || ! command_exists helm; then
    echo -e "${RED}ERRO: 'kubectl' e 'helm' são necessários para executar este script.${NC}"
    exit 1
fi

# 1. Configurar o Namespace.
setup_namespace

# 2. Definir o contexto atual do kubectl.
print_header "1 - A definir o contexto atual do kubectl para o namespace '$NAMESPACE'"
kubectl config set-context --current --namespace="$NAMESPACE"
echo -e "${GREEN}Contexto definido com sucesso.${NC}"

# 3. Fazer o deploy dos charts do Helm.
deploy_secrets_chart
deploy_chart "nikke-hub-postgres"        "./nikke-hub-postgres"        "1m"
deploy_chart "nikke-hub-redis"           "./nikke-hub-redis"           "1m"
deploy_chart "nikke-hub-eureka-server"   "./nikke-hub-eureka-server"   "1m"
deploy_chart "nikke-hub-config-server"   "./nikke-hub-config-server"   "1m"
deploy_chart "nikke-hub-gateway"         "./nikke-hub-gateway"         "1m"
deploy_chart "nikke-hub-otel-collector"  "./nikke-hub-otel-collector"  "1m"
deploy_chart "nikke-hub-ingress"         "./nikke-hub-ingress"         "1m"
deploy_chart "nikke-hub-zipkin"          "./nikke-hub-zipkin"          "1m"
deploy_chart "nikke-hub-prometheus"      "./nikke-hub-prometheus"      "1m"
deploy_chart "nikke-hub-grafana"         "./nikke-hub-grafana"         "2m"
deploy_chart "nikke-hub-backend"         "./nikke-hub-backend"         "4m"

echo -e "\n${GREEN}✅ Deploy de todo o cluster local finalizado com sucesso!${NC}"
