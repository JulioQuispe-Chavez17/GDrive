# Ejecute este comando para descargar la versión estable actual de Docker Compose:
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
# Aplicar permisos ejecutables al binario:
sudo chmod +x /usr/local/bin/docker-compose
# Verificamos la version
docker-compose --version
