# Docker in Oracle (Ubuntu)
------------
A continuación se muestran los pasos, que he seguido para ayudarme a ejecutar Minikube en la instancia EC2.

## Pre-Requisitos

| Ami  |  Ubuntu Server 18.04 LTS (HVM), tipo de volumen SSD |
| ------------ | ------------ |
|  Tipo de instancia | t2.xlarge(4 VCPU, 16 GB RAM) Cost/ Per Hour: 0,1856 USD   |
|  Almacenamiento | 20GB  |
|Grupo de seguridad | Habilitar puertos requeridos |
| Par de claves | Cree su propio par de claves. |

Ir al readme de docker y docker-compose
Nota: Docker requiere un mínimo de 2 vCPU y 4GB RAM. Instalar Metamask in browser


## 1. Instalar JQ
Is a lightweight and flexible command-line JSON processor.
```
sudo apt-get install jq
```
## 3. Implementar contenedores de Lacchain
```
https://github.com/lacchain/lacchain-docker
```
## 4. Necesitas recursos
Puedes revisar las ramas y existen implmentaciones de como interactuar con el storage de Oracle.
