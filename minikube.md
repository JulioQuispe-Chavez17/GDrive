# Minikube in AWS EC2 (Ubuntu)
------------
A continuación se muestran los pasos, que he seguido para ayudarme a ejecutar Minikube en la instancia EC2.

## Pre-Requisitos

| Ami  |  Ubuntu Server 18.04 LTS (HVM), tipo de volumen SSD |
| ------------ | ------------ |
|  Tipo de instancia | t2.xlarge(4 VCPU, 16 GB RAM) Cost/ Per Hour: 0,1856 USD   |
|  Almacenamiento | 20GB  |
|Grupo de seguridad | Habilitar puertos requeridos |
| Par de claves | Cree su propio par de claves. |

Nota: Minikube requiere un mínimo de 2 vCPU y 4GB RAM. 


## 1. Instalar kubectl

`curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl`

`chmod +x ./kubectl`

`sudo mv ./kubectl /usr/local/bin/kubectl`

`sudo apt-get install conntrack`

## 2. Instalar Docker

```sudo apt-get update && \
    sudo apt-get install docker.io -y```
	 Minikube requiere Docker.

## 3. Instalar Minikube

`curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/`

## 4. Comprobar versión de Minikube

`minikube version`

¡Ahora hemos instalado con éxito Minikube!

# Ejecución de Minikube en EC2 Ubuntu

------------

Debes convertirte en usuario root para administrar minikube.

## 1. Inicia Minikube

`minikube start --vm-driver=none`

## 2. Comprobar el estado de Minikube

`minikube status`

