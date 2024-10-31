# Zero-GrayLog
Zero-GrayLog
# 1. 概述
## 1.1. 简介
GrayLog是一个轻量型的分布式日志管理平台，一个开源的日志聚合、分析、审计、展示和预警工具。 在功能上来说，和ELK类似，但又比ELK要简单轻量许多。 依靠着更加简洁，高效，部署使用简单的优势很快受到许多公司的青睐。 GrayLog包含Elasticsearch、MongoDb 和Graylog三个模块

# 2. 功能

# 3. 使用
## 3.1. 安装
### 3.1.1. docker-compose-graylog.yml
```yml
# docker-compose配置参考：https://docs.graylog.org/en/3.3/pages/installation/docker.html
version: '3'

services:

  mongo:
    image: mongo:3
    container_name: graylog_demo_mongo
    restart: unless-stopped
    #    volumes:
    #      - ./graylog/mongo_data:/data/db
    networks:
      - graylog

  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10
    container_name: graylog_demo_elasticsearch
    restart: unless-stopped
    #    volumes:
    #      - ./graylog/es_data:/usr/share/elasticsearch/data
    environment:
      - http.host=0.0.0.0
      - transport.host=localhost
      - network.host=0.0.0.0
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    deploy:
      resources:
        limits:
          memory: 1g
    networks:
      - graylog

  graylog:
    image: graylog/graylog:3.3
    container_name: graylog_demo_graylog
    restart: unless-stopped
    #    volumes:
    #      - ./graylog/graylog_data_journal:/usr/share/graylog/data/journal
    environment:
      - GRAYLOG_PASSWORD_SECRET=somepasswordpepper # CHANGE ME (must be at least 16 characters)!
      - GRAYLOG_ROOT_PASSWORD_SHA2=8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918 # Password: admin
      - GRAYLOG_HTTP_EXTERNAL_URI=http://127.0.0.1:9000/ # TODO 这里填写自己的ip地址
    ports:
      # Graylog web interface and REST API
      - 9000:9000
      # Syslog TCP
      - 1514:1514
      # Syslog UDP
      - 1514:1514/udp
      # GELF TCP
      - 12201:12201
      # GELF UDP
      - 12201:12201/udp
    networks:
      - graylog
    depends_on:
      - mongo
      - elasticsearch

# 网桥graylog -> 方便相互通讯
networks:
  graylog:
    driver: bridge
```
### 3.1.2. docker-compose-up.sh
```bash
docker-compose -f docker-compose-graylog.yml -p graylog_demo up -d
```
### 3.1.3. 访问
- 访问地址：http://localhost:9001 默认登录账号密码：admin/admin

# 4. 其他

# 5. 参考