#!/bin/bash
set -e

echo "=== Iniciando remoção do Nikke Hub ==="

echo "=== 13 - Removendo Backend ==="
helm uninstall nikke-hub-backend --wait --timeout 15s || echo "⚠️ Backend não encontrado ou já removido"

echo "=== 12 - Removendo Grafana ==="
helm uninstall nikke-hub-grafana --wait --timeout 15s || echo "⚠️ Grafana não encontrado ou já removido"

echo "=== 11 - Removendo Prometheus ==="
helm uninstall nikke-hub-prometheus --wait --timeout 15s || echo "⚠️ Prometheus não encontrado ou já removido"

echo "=== 10 - Removendo Zipkin ==="
helm uninstall nikke-hub-zipkin --wait --timeout 15s || echo "⚠️ Zipkin não encontrado ou já removido"

echo "=== 9 - Removendo Ingress ==="
helm uninstall nikke-hub-ingress --wait --timeout 15s || echo "⚠️ Ingress não encontrado ou já removido"

echo "=== 8 - Removendo OpenTelemetry Collector ==="
helm uninstall nikke-hub-otel-collector --wait --timeout 15s || echo "⚠️ OTel Collector não encontrado ou já removido"

echo "=== 7 - Removendo Gateway ==="
helm uninstall nikke-hub-gateway --wait --timeout 15s || echo "⚠️ Gateway não encontrado ou já removido"

echo "=== 6 - Removendo Config Server ==="
helm uninstall nikke-hub-config-server --wait --timeout 15s || echo "⚠️ Config Server não encontrado ou já removido"

echo "=== 5 - Removendo Eureka ==="
helm uninstall nikke-hub-eureka-server --wait --timeout 15s || echo "⚠️ Eureka não encontrado ou já removido"

echo "=== 4 - Removendo Redis ==="
helm uninstall nikke-hub-redis --wait --timeout 15s || echo "⚠️ Redis não encontrado ou já removido"

echo "=== 3 - Removendo Postgres ==="
helm uninstall nikke-hub-postgres --wait --timeout 15s || echo "⚠️ Postgres não encontrado ou já removido"

echo "=== 2 - Removendo Secrets ==="
helm uninstall nikke-hub-secrets --wait --timeout 15s || echo "⚠️ Secrets não encontrado ou já removido"

echo "=== 1 - Removendo PVCs persistentes (se existirem) ==="
kubectl delete pvc --all --wait --timeout 15s || echo "⚠️ Nenhum PVC encontrado"

echo "=== 0 - Removendo Namespace ==="
kubectl delete namespace nikke-hub --wait --timeout 15s || echo "⚠️ Namespace não encontrado ou já removido"

echo "🗑️ Remoção finalizada com sucesso!"