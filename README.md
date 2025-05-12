# Nexign Telecom

Проект состоит из набора микросервисов для эмуляции и тарификации звонков мобильного оператора «Ромашка».
## Статус
* Незавершенно

## Структура проекта

* **commutator** – эмулятор коммутатора:

    * генерирует записи звонков абонентов;
    * хранит список абонентов и логи звонков в H2 (in-memory);
    * собирает CDR-файлы по 10 записей и отправляет в BRT через RabbitMQ.
* **BRT** – сервис реального времени биллинга:

    * сохраняет данные звонков и абонентов в PostgreSQL;
    * пересылает данные в HRS для расчёта списаний;
    * обновляет балансы абонентов;
    * предоставляет информацию для CRM по REST.
* **HRS** – сервис расчёта тарифов:

    * хранит информацию о тарифах в PostgreSQL;
    * рассчитывает списания по классическим и помесячным тарифам;
    * возвращает результаты расчётов в BRT.
* **CRM** – сервис управления клиентами:
    * не реализовано
* **docker-compose.yml** – организация контейнеров (RabbitMQ, PostgreSQL, микросервисы).
* **init.sql** – скрипт инициализации схем PostgreSQL для BRT и HRS.

## Схема взаимодействия сервисов

```text
+---------------+       +-----------+       +-------+
| commutator    |--(AMQP)-->| rabbitmq  |--(AMQP)-->| BRT   |
| (генерация    |          |           |           |       |
|  CDR-файлов)  |          |           |           |       |
+---------------+          +-----------+          +-------+
                                                     |
                          REST (Не реализован)       |
                                                     v
                         +-----------+          +-------+
                         |   HRS     |<--(REST)-|       |
                         | (расчёт)  |          |       |
                         +-----------+          +-------+
                                                    |
                         REST (Не реализован)       v
                                               +-----------+
                                               |   CRM     |
                                               | (API)     |
                                               +-----------+
```

## Основные решения и нюансы

* **commutator** использует H2 (in-memory) для быстрого старта и демонстрации; консоль H2 доступна по `/h2-console`.
* **BRT** и **HRS** хранят данные в PostgreSQL с автоматическим созданием/удалением схемы (`ddl-auto: create-drop`) для тестирования.
* Актуализацию схемы PostgreSQL можно выполнять через `init.sql`.
* Асинхронная передача CDR через RabbitMQ обеспечивает масштабируемость и надёжность.
* **CRM** настроен на H2, но легко переключается на PostgreSQL через изменение `application.yml`.
* В проектах используются Spring Boot (Web, Data, Security, AMQP), JPA, Maven, Docker.

## Схема баз данных

### commutator (H2)

* `subscribers` (id, name, msisdn, tariff\_id, registration\_date, balance)
* `calls` (id, type, subscriber\_id, peer\_number, start\_time, end\_time)

### BRT (PostgreSQL)

* `subscribers` (id, name, msisdn, balance, registration\_date, tariff\_id)
* `calls` (id, type, msisdn, peer\_number, start\_time, end\_time)

### HRS (PostgreSQL)

* `tariffs` (id, name, inbound\_rate, outbound\_rate, monthly\_fee, minutes\_included)
* Дополнительные таблицы историй расчётов при необходимости.

### CRM (H2)
* не реализовано

## Запуск без Docker Compose

1. Поднять RabbitMQ (порт 5672, консоль 15672).
2. Поднять PostgreSQL с БД `brt` и `hrs` (user=admin, pass=admin).
3. Выполнить `init.sql` для создания необходимых таблиц.
4. В IDE или терминале выполнить для каждого модуля:

   ```bash
   mvn spring-boot:run -pl commutator
   mvn spring-boot:run -pl BRT
   mvn spring-boot:run -pl HRS
   mvn spring-boot:run -pl CRM
   ```

## Данные для авторизации

* **RabbitMQ**: `guest` / `guest`
* **PostgreSQL**: `admin` / `admin`

## Параметры подключения к базам данных

* **commutator** (H2): `jdbc:h2:mem:testdb`, user `admin`, pass `admin`
* **BRT** (PostgreSQL): `jdbc:postgresql://localhost:5432/brt`, user `admin`, pass `admin`
* **HRS** (PostgreSQL): `jdbc:postgresql://localhost:5432/hrs`, user `admin`, pass `admin`
