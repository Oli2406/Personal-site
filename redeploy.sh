#!/bin/bash
set -e

log() { echo -e "\033[1;36m[INFO]\033[0m $1"; }

log "🚀 Building backend image..."
docker build -t personal-site-backend:latest ./backend

log "🚀 Building frontend image..."
docker build -t personal-site-frontend:latest ./frontend

log "📜 Applying Kubernetes manifests..."
kubectl apply -f all.yml

log "🔁 Restarting deployments..."
kubectl rollout restart deployment backend
kubectl rollout restart deployment frontend
kubectl rollout restart deployment postgres

log "⏳ Waiting for rollouts to finish..."
kubectl rollout status deployment backend
kubectl rollout status deployment frontend
kubectl rollout status deployment postgres

log "✅ Redeployment complete!"
