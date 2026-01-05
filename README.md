# sentiment-analyzer-spring

## Технологии:
- Java 17
- Spring Boot 3.5.9
- Lombok
- Gradle
- Grafana
- Prometheus
- Docker
- K8S

## Требования:
- Java 17
- Gradle
- Docker
- Minikube
- Браузер

## Клонируйте репозиторий:
```bash
git clone https://github.com/nikolay-977/sentiment-analyzer-spring
cd sentiment-analyzer-spring
```


## Запустите minikube
```bash
minikube start --cpus=2 --memory=4G --nodes=2
minikube addons enable ingress
minikube addons enable metrics-server
```

## Соберите приложения
```bash
./gradlew clean build
```

## Соберите docker-образ
```bash
docker rmi sentiment-analyzer:latest
docker build -t sentiment-analyzer:latest .
```

## Загрузите образ в Minikube
```bash
minikube image load sentiment-analyzer:latest
```

## Примените конфигурацию
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/prometheus.yaml
kubectl apply -f k8s/grafana.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/ingress.yaml
kubectl apply -f k8s/hpa.yaml
```

## Проверьте статуса
```bash
kubectl get all -n sentiment-analysis
kubectl get all -n monitoring
kubectl get hpa -n sentiment-analysis
kubectl get ingress -n sentiment-analysis
```

## Пробросьте порты
```bash
kubectl port-forward svc/sentiment-service -n sentiment-analysis 8080:8080 &
kubectl port-forward svc/prometheus -n monitoring 9090:9090 &
kubectl port-forward svc/grafana -n monitoring 3000:3000 &
```

## Откройте prometheus и grafana в браузере
```bash
open http://localhost:9090
open http://localhost:3000
```

## Проверьте метрики
```bash
curl http://localhost:8080/actuator/health
```

```bash
curl http://localhost:8080/actuator/prometheus
```

## Протестируйте
```bash
curl -X POST http://localhost:8080/api/sentiment -H "Content-Type: text/plain" -d "I love this product"
```

```bash
curl -X POST http://localhost:8080/api/sentiment -H "Content-Type: text/plain" -d "I hate this product"
```