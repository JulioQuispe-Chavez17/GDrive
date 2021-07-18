# Primero, instala los requisitos iniciales corriendo estos comandos.
sudo apt-get update

sudo apt-get install \
 apt-transport-https \
 ca-certificates \
 curl \
 gnupg-agent \
 software-properties-common
 # Ahora instala la llave
 curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
 # Agregaremos en este paso el repositorio de Docker
 sudo add-apt-repository \
 "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
 $(lsb_release -cs) \
 stable"
 # Instala los paquetes.
 sudo apt-get update
 sudo apt-get install docker-ce docker-ce-cli containerd.io
 # Prueba la instalación usando comando de "hola mundo".
 sudo docker run hello-world
 # Si todo salió bien veremos el siguiente mensaje
 latest: Pulling from library/hello-world
 0e03bdcc26d7: Pull complete
 Digest: sha256:49a1c8800c94df04e9658809b006fd8a686cab8028d33cfba2cc049724254202
 Status Downloaded newer image for hello-world:latest
 Hello from Docker!
 This message shows that your installation appears to be working correctly.
 To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
 (amd64)
 3. The Docker daemon created a new container from that image which runs the
 executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
 to your terminal.
 To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash
 Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/
 For more examples and ideas, visit:
 https://docs.docker.com/get-started/
