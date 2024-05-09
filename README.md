run below command to start the database:
```
docker run --name pg1 -p 5432:5432 -e POSTGRES_USER=datvu -e POSTGRES_PASSWORD=asdasd -e POSTGRES_DB=mydb -d postgres:15-alpine

```

if you are facing below error: 
```
docker: Error response from daemon: Conflict. The container name "/pg1" is already in use by container "XXXXXXXXX". You have to remove (or rename) that container to be able to reuse that name.
```
try to run this command: 
```
docker rm 488d88f2dcab417200ac5081883a0cf623e587e67e744621d3d583b96da8e6ef```
the re-run the first command.
