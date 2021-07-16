# POSTGRES

PostgreSQL es un popular sistema de gestión de bases de datos relacionales de objetos de código abierto con una sólida reputación de fiabilidad, integridad de datos y corrección.

- Alta disponibilidad y redundancia administradas.
- Facilidad de escalado hacia arriba o hacia abajo (es decir, escalado vertical) aumentando o disminuyendo el recurso ..- computacional para que la instancia de base de datos controle la carga variable.
- Facilidad de escalado de salida y salida (es decir, escalado horizontal) mediante la creación de instancias replicadas para controlar el tráfico de lectura elevado.
- Copias instantáneas y copias de seguridad automatizadas con la capacidad de revertir fácilmente una base de datos.
- Realización de parches de seguridad, monitoreo de bases de datos, etc.

## DOCKER-COMPOSE
1. Imagen

Toda organización busca optimizar recursos, nosotros debemos encargarnos de que la imagen contenga lo necesario y que sea liviana, por esta razon elegimos: postgres:12-alpine

2. Uso

Este servicio puede ser usado para cualquier servicio de backend, puedes iniciar con un TODO. Creamos un volumen, porque los contenedores se reciclan y no queremos perder la data.

```
version: "3.7"
services:
  db:
    image: postgres:12-alpine
    environment:
      POSTGRES_PASSWORD: postgres
      POSTGRES_USER: postgres
      POSTGRES_DB: todo
    volumes:
      - ./pgdata:/var/lib/postgresql/data
    ports:
      - "5432:5432"
```

3. Minikube(Julio)
## MINIKUBE
1. Volúmenes persistentes

Los pods de Kubernetes están diseñados para tener almacenamiento efímero, lo que significa que una vez que un pod muere, se pierden todos los datos dentro del pod. Para mitigar esto, Kubernetes tiene el concepto de volúmenes.
Kubernetes se centra en el acoplamiento flexible, su objetivo es desacoplar cómo se proporciona el volumen en la plataforma frente a cómo lo consumen los pods.
Como resultado, Kubernetes proporciona dos recursos de API que son y .PersistentVolumePersistentVolumeClaim

PersistentVolume.yaml

```
kind: PersistentVolume
apiVersion: v1
metadata:
  name: postgres-pv-volume
  labels:
    type: local
    app: postgres
spec:
  storageClassName: manual
  capacity:
    storage: 5Gi
  accessModes:
    - ReadWriteMany
  hostPath:
    path: "/mnt/data"

```
2. Reclamación de volumen persistente

PVC es una solicitud de almacenamiento por parte del usuario y permite al usuario consumir recursos de almacenamiento abstractos en el clúster. Las notificaciones pueden solicitar tamaños y modos de acceso específicos en el clúster. En términos laicos, el PV dice: "Quiero 2 GB de memoria en algún lugar de la red, no sé dónde está o cómo está hecho, pero asumo que existe y quiero reclamarlo". De esta manera la definición del volumen y la consumición del volumen siguen siendo separadas. 

PersistentVolumeClaim.yaml

```
kind: PersistentVolumeClaim
apiVersion: v1
metadata:
  name: postgres-pv-claim
  labels:
    app: postgres
spec:
  storageClassName: manual
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 5Gi
```

3. Credenciales de Postgres

Para crear la base de datos Postgres, se requieren credenciales, así como al acceder a la base de datos en nuestra aplicación. Además, no queremos que las credenciales se almacenen en el control de versiones o directamente en la imagen. 

Credential.yaml

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: postgres-config
  labels:
    app: postgres
data:
  POSTGRES_DB: postgresdb
  POSTGRES_USER: postgres
  POSTGRES_PASSWORD: postgres
```

4. Implementación de Postgres

 Las credenciales de base de datos almacenadas en el objeto Config también se pasan y la notificación de volumen se monta en el pod. 

Deployment.yaml

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: postgres
spec:
  selector:
   matchLabels:
    app: postgres
  replicas: 1
  template:
    metadata:
      labels:
        app: postgres
    spec:
      containers:
        - name: postgres
          image: postgres:12-alpine
          imagePullPolicy: "IfNotPresent"
          envFrom:
            - configMapRef:
                name: postgres-config
          volumeMounts:
            - mountPath: /var/lib/postgresql/data
              name: postgredb
      volumes:
        - name: postgredb
          persistentVolumeClaim:
            claimName: postgres-pv-claim
```

5. Servicio Postgres
El servicio postgres es para que los pods tengan comunicación con el exterior, asignándole un puerto.

Service.yaml

```
kind: Service
apiVersion: v1
metadata:
  name: postgres
spec:
  selector:
    app: postgres
  type: NodePort
  ports:
    - protocol: TCP
      port: 5432
      targetPort: 5432
      nodePort: 30432
```

Referencia: https://markgituma.medium.com/kubernetes-local-to-production-with-django-3-postgres-with-migrations-on-minikube-31f2baa8926e

6. Utilidades

Estas credenciales te permitiran conectarte a tu servicio de base de datos

- user: postgres
- password: postgres
- database: postgres
- port: xxxxxxxx
- host: xxxxxxxx

Comandos en caso de usar el objeto Secret de Kubernetes debes encrytar la información sensible.

- echo -n "cm9vdA==" | base64 --decode
- echo -n "cm9vdA==" | base64

Tips: Puedes usar el archivo makefile, ya tiene los comandos para iniciar el servicio