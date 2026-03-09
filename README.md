# Actividad 3 - Trabajo K8S

Autor: **Carlos Javier Gutierrez Torres**

Este repositorio implementa un workflow de despliegue de un microservicio contenerizardo 'patrones-api'.

El repositorio contiene el codigo del microservicio, empaquetado con Helm, Orquestacion con ArgoCD y Kubernetes, ademas de automatizacion CI/CD con Github Actions.

## Flujo

Cuando se realzia un cambio desde una rama y se crea un Pull request apuntando hacia la rama main, GitHub Actions construye la imagen docker del microservicio, luego publica esa imagen a Docker Hub 'krlitos/actividad3-gutierrez' y luego realiza la actualizacion del tag de la imagen docker al chart de Helm.

Cuando se hace Merge de la rama, ArgoCD detecta el cambio y despliega la nueva version automaticamente en el cluster.

### Pipeline CI/CD Github Actions
El pipeline se ejecuta cuando se crea un pull request hacia main.

**Pasos:**
1. Checkout de la rama del PR
2. Configurar Java 21
3. Generar tag a partir del SHA del commit
4. Login a Docker Hub
5. Construir y publicar imagen Docker
6. Actualizar `helm/patrones-api/values.yaml` con el nuevo tag
7. Commit y push de vuelta a la rama del PR

Al hacer merge el PR a main, ArgoCD detecta el tag actualizado en `values.yaml` y despliega la nueva version de forma automatica.


## Componentes K8S

En K8S se creo:
1. Un Namespace 'microservicios'
1. Un Service de tipo load balancer para acceder los pods del microservicio desde afuera del cluster
2. Un Deployment con la configuracion del Microservicio
3. Un Deployment para redis (El microservicio almacena en redis)
4. Un Persistent Volume para redis (Evita que se pierda la data de redis en caso de actualizacion)
5. Un Service de tipo clusterIp para redis
6. Un Namespace 'argo' donde argo habitara.

### Visualmente



## Stack

| Capa | Tecnologia |
|------|-----------|
| Microservicio | Java 21, Spring Boot 3.2, Spring Data Redis |
| Contenedorizacion | Docker |
| Orquestacion | Kubernetes (kubeadm via Docker Desktop) |
| Gestion de paquetes | Helm |
| GitOps | ArgoCD |
| CI/CD | GitHub Actions |
| Almacenamiento | Redis |
| Registro de imagenes | Docker Hub |


## Estructura del Proyecto

```
.
├── microservicios/patrones-api/src                                # Codigo fuente Spring Boot
│   └── main/java/com/actividad3/gutierrez/patronesapi/
│       ├── config/RedisConfig.java                                # Configuracion de RedisTemplate
│       ├── controller/                                            # Endpoints REST
│       ├── model/                                                 # Entidad PatronDiseno
│       ├── repository/                                            # Operaciones sobre hash de Redis
│       └── service/                                               # Logica de negocio
├── helm/
│   └── patrones-api/
│       ├── Chart.yaml
│       ├── values.yaml                                            # Valores de configuracion por defecto
│       ├── patrones-microservice-deployment.yaml                  # Deployment del microservicio
│       ├── patrones-microservice-service.yaml                     # Service tipo LoadBalancer
│       ├── redis-deployment.yaml                                  # Deployment de Redis
│       ├── redis-service.yaml                                     # Service ClusterIP de Redis
│       └── redis-persistentvc.yaml                                # Volumen persistente para Redis
├── argocd/
│   └── application.yaml                                           # Manifiesto Application de ArgoCD
├── .github/workflows/
│   └── integration-delivery.yml                                   # Pipeline de GitHub Actions
└── Dockerfile                         
```

## Documentacion Api

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/api/patrones` | Listar todos los patrones |
| GET | `/api/patrones/{id}` | Obtener patron por ID |
| POST | `/api/patrones` | Crear un patron |
| PUT | `/api/patrones/{id}` | Actualizar un patron |
| DELETE | `/api/patrones/{id}` | Eliminar un patron |
| GET | `/actuator/health` | Health check |

### Body para requests (POST / PUT)

```json
{
  "titulo": "Singleton",
  "descripcion": "Garantiza que una clase tenga una unica instancia.",
  "palabrasClave": ["creacional", "instancia unica"]
}
```

## Instalacion ArgoCD

Crear el namespace para ArgoCD:
```
kubectl create namespace argocd
```

Instalar ArgoCD:
```
kubectl apply -n argocd --server-side --force-conflicts \
  -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```

Exponer UI de ArgoCD
```
kubectl port-forward svc/argocd-server -n argocd 8090:443
```

Exponer Password de ArgoCD (Usuario: admin)
```
kubectl -n argocd get secret argocd-initial-admin-secret \
  -o jsonpath="{.data.password}" | base64 -d && echo ""
```