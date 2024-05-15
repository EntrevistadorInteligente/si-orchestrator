# orquestador

## Entorno local

Para desarrollo en entorno local el perfil por defecto es "local" o también se puede aputar a un perfil especifico desde el application.properties en 'spring.profiles.active', reemplazando el valor por el perfil especifico "local", "dev", "prod". También se debe ejecutar el siguiente docker-compose "docker-compose-local.yml"

```docker compose -f docker-compose-local.yml up -d```

Se debe contar también, con un contenedor con mongodb en su puerto por defecto (27017)


```docker network create mongoCluster```

```docker run -d --rm -p 27017:27017 --name mongo1 --network mongoCluster mongo:6 mongod --replSet myReplicaSet --bind_ip localhost,mongo1```

