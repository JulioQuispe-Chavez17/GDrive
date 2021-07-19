DOCUMENTO
- https://docs.google.com/document/d/11jzRQKuI2Ee4mlJ_5foWe-3kiqsxf0obek9yz6J7AVY/edit#heading=h.w1vby14vmahl

docker run -p 20000:8080 -p 50000:50000 -v /home/ubuntu/volumen:/var/jenkins --name jenkinsSonarQubePruebas jenkins/jenkins:jdk11

docker run -d --name sonarQubePruebas -p 9000:9000 sonarqube

 Creamos la red interna:
    docker network create red_jenkins_sonarqube

  · Añadimos a nuestra red interna Jenkins y SonarQube:
    docker network connect red_jenkins_sonarqube jenkinsSonarQubePruebas
    docker network connect red_jenkins_sonarqube sonarQubePruebas

  · Borrar red interna:
   docker network rm red_jenkins_sonarqube
