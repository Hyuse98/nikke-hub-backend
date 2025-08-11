#!/bin/bash
set -e

echo "=== 0 - Creating NameSpace ==="
kubectl create namespace nikke-hub --wait --timeout 5s

echo "=== 1 - Setting current context ==="
kubectl config set-context --current --namespace=nikke-hub --wait --timeout 5s

echo "=== 2 - Secrets ==="
helm upgrade --install nikke-hub-secrets ./charts/nikke-hub-secrets --wait --timeout 5s

echo "=== 3 - Postgres ==="
helm upgrade --install nikke-hub-postgres ./charts/nikke-hub-postgres --wait --timeout 1m

echo "=== 4 - Redis ==="
helm upgrade --install nikke-hub-redis ./charts/nikke-hub-redis --wait --timeout 1m

echo "=== 5 - Eureka ==="
helm upgrade --install nikke-hub-eureka-server ./charts/nikke-hub-eureka-server --wait --timeout 1m

echo "=== 6 - Config Server ==="
helm upgrade --install nikke-hub-config-server ./charts/nikke-hub-config-server --wait --timeout 1m

echo "=== 7 - Gateway ==="
helm upgrade --install nikke-hub-gateway ./charts/nikke-hub-gateway --wait --timeout 1m

echo "=== 8 - OpenTelemetry Collector ==="
helm upgrade --install nikke-hub-otel-collector ./charts/nikke-hub-otel-collector --wait --timeout 2m

echo "=== 9 - Ingress ==="
helm upgrade --install nikke-hub-ingress ./charts/nikke-hub-ingress --wait --timeout 1m

echo "=== 10 - Zipkin ==="
helm upgrade --install nikke-hub-zipkin ./charts/nikke-hub-zipkin --wait --timeout 2m

echo "=== 11 - Prometheus ==="
helm upgrade --install nikke-hub-prometheus ./charts/nikke-hub-prometheus --wait --timeout 2m

echo "=== 12 - Grafana ==="
helm upgrade --install nikke-hub-grafana ./charts/nikke-hub-grafana --wait --timeout 2m

echo "=== 13 - Backend === "
helm upgrade --install nikke-hub-backend ./charts/nikke-hub-backend --wait --timeout 4m

echo "✅ Deploy finalizado com sucesso!"