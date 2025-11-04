#!/bin/bash
# === redeploy.sh ===
# Rebuild and restart your full Docker + Kubernetes stack using Docker Compose

set -e  # exit immediately on error

# === LOGGING ===
log() {
  echo -e "\033[1;36m[INFO]\033[0m $1"
}

# === STEP 1: Rebuild Docker images ===
log "🚀 Rebuilding Docker images via Docker Compose..."
docker-compose down
docker-compose up --build -d

log "✅ Docker containers rebuilt and running"

# === STEP 2: Restart Kubernetes deployments (optional sync step) ===
log "🔁 Restarting Kubernetes deployments (frontend, backend, postgres)..."
kubectl rollout restart deployment frontend || true
kubectl rollout restart deployment backend || true
kubectl rollout restart deployment postgres || true

log "⏳ Waiting for rollouts to complete..."
kubectl rollout status deployment frontend || true
kubectl rollout status deployment backend || true
kubectl rollout status deployment postgres || true

log "✅ Redeployment complete!"
