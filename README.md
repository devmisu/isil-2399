# isil-2399

![ISIL-2021-2](https://img.shields.io/badge/-ISIL--2021--2-blue)
![.NODEJS](https://img.shields.io/badge/-NodeJS-green)
![MySQL](https://img.shields.io/badge/-MySQL-white)

## Diagram

![Solera Jobs V2 Diagram](/docs/diagram.png)

## Postman

V2:
- [Documentation](https://documenter.getpostman.com/view/4095139/UUy65PgQ)
- [Collection](https://www.getpostman.com/collections/5f03db9b4c7fda99da79)

## Commands

SSH connection:
```
ssh -i "solera-jobs-server.pem" ec2-user@ec2-34-239-115-169.compute-1.amazonaws.com
```

PM2:
```
pm2 status
```

```
pm2 monit
```

```
pm2 logs
```

Deploy:
```
./deploy.sh
```