###Анализируй это

В топике кафки RAW_PAYMENTS находятся данные по платежам пользователей. 

Брокер развернут локально в docker-контейнере и доступен по порту 29092.

Формат одной записи представлен ниже:

```
key1:{"ref":"U030306190000188", "categoryId":1, "userId":"XAABAA", "recipientId":"XA3SZV", "desc":"Платеж за услуги", "amount":10.0}
```

где key1 - ключ записи.
Все остальное после двоеточия является данными по платежу.

Необходимо реализовать следующую логику:
- Вычитать все данные из топика Кафки
- Выполнить агрегацию данных для возможности отображения аналитики по платежам пользователей
- Реализовать Rest интерфейс для доступа к аналитике по пользователям. Приложение должно быть доступно по порту 8081

### Уточнения
Данные уже будут присутствовать в топике RAW_PAYMENTS. Дополнительно никаких данных во время тестирования поступать не будет.

Анализ должен производится на тех данных, которые будут вычитаны из топика.

Следует учесть, что данные из Кафки могут приходить битыми.

Дополнительное использование различных хранилищ и подходы к реализации аналитики данных остаются на усмотрение разработчика.

## Аналитика

- Необходимо выполнить разбиение данных платежей по категориям (поле categoryId).
По каждой категории платежа пользователя необходимо подсчитать минимальную сумму, максимальную сумму и общую сумму в категории.
Кроме того требуется отобразить общую сумму платежей пользователя по всем категориям

- Требуется уметь отображать статистику категорий по каждому пользователю. Статистика: самая частая категория трат,
самая редкая категория трат, категория с наибольшей суммой, категория с наименьшей суммой

- Задание со звездочкой

Требуется реализовать логику выделения шаблонов платежей из потока данных. 
Платеж считается шаблонным по следующим критериям:

- У платежей совпадают величина, категория, от кого и кому платеж был проведен (recipientId и userId соответсвенно)
- Такие платежи повторяется три и более раза

Реализовать интерфейс, по которому можно будет получать шаблоны платежей пользователя. 

### Ожидаемый интерфейс

Хелсчек

GET /admin/health

Ожидаем получить 200 ответ

```json
{"status":"UP"}
```

Пример данных в топике:

```
key1:{"ref":"ref1", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_1", "amount":10.0}
key2:{"ref":"ref2", "categoryId":2, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_2", "amount":350.56}
key3:{"ref":"ref3", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_3", "amount":700.0}
key4:{"ref":"ref4", "categoryId":3, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_4", "amount":5.99}
key5:{"ref":"ref5", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_5", "amount":10.0}
key6:{"ref":"ref6", "categoryId":2, "userId":"User_2", "recipientId":"User_3", "desc":"Тестовый платеж_6", "amount":350.56}
key7:{"ref":"ref7", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_7", "amount":890.0}
key8:{"ref":"ref8", "categoryId":3, "userId":"User_3", "recipientId":"User_2", "desc":"Тестовый платеж_8", "amount":35.99}
key9:{"ref":"ref9", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_9", "amount":890.0}
key10:{"ref":"ref10", "categoryId":3, "userId":"User_3", "recipientId":"User_2", "desc":"Тестовый платеж_10", "amount":35.9910}
key11:{"ref":"ref11", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_11", "amount":10.0}
key12:{"ref":"ref12", "categoryId":2, "userId":"User_2", "recipientId":"User_3", "desc":"Тестовый платеж_12", "amount":350.56}
key13:{"ref":"ref13", "categoryId":1, "userId":"User_1", "recipientId":"User_2", "desc":"Тестовый платеж_13", "amount":10.0}
key14:{"ref":"ref14", "categoryId":2, "userId":"User_2", "recipientId":"User_3", "desc":"Тестовый платеж_14", "amount":350.56}
key15:{"ref":"ref15", "categoryId":4, "userId":"User_1", "recipientId":"User_4", "desc":"Тестовый платеж_15", "amount":15.00}
```

- GET /analytic

Возвращает аналитику платежей по всем пользователям

Пример ответа

```json
[
  {
    "userId": "User_3",
    "totalSum": 71.981,
    "analyticInfo": {
      "3": {
        "min": 35.99,
        "max": 35.991,
        "sum": 71.981
      }
    }
  },
  {
    "userId": "User_2",
    "totalSum": 1051.68,
    "analyticInfo": {
      "2": {
        "min": 350.56,
        "max": 350.56,
        "sum": 1051.68
      }
    }
  },
  {
    "userId": "User_1",
    "totalSum": 2891.55,
    "analyticInfo": {
      "1": {
        "min": 10,
        "max": 890,
        "sum": 2520
      },
      "2": {
        "min": 350.56,
        "max": 350.56,
        "sum": 350.56
      },
      "3": {
        "min": 5.99,
        "max": 5.99,
        "sum": 5.99
      },
      "4": {
        "min": 15,
        "max": 15,
        "sum": 15
      }
    }
  }
]
```

- Get /analytic/{userId}

Получение аналитики по конкретному пользователю

Запрос

/analytic/User_1

Ответ

```json
{
  "userId": "User_1",
  "totalSum": 2891.55,
  "analyticInfo": {
    "1": {
      "min": 10,
      "max": 890,
      "sum": 2520
    },
    "2": {
      "min": 350.56,
      "max": 350.56,
      "sum": 350.56
    },
    "3": {
      "min": 5.99,
      "max": 5.99,
      "sum": 5.99
    },
    "4": {
      "min": 15,
      "max": 15,
      "sum": 15
    }
  }
}
```

Если такого пользователя нет - вернуть 404 ошибку.


- Get /analytic/{userId}/stats

Получение статистики категорий по пользователю

Запрос 

/analytic/User_1/stats

Ответ

```json
{
  "oftenCategoryId": 1,
  "rareCategoryId": 2,
  "maxAmountCategoryId": 1,
  "minAmountCategoryId": 3
}
```

Если такого пользователя нет - вернуть 404 ошибку.


- Get /analytic/{userId}/templates

Получение информации по платежам, которые были выделены как шаблонные для данного пользователя.

Запрос

/analytic/User_1/templates

Ответ

```json
[
  {
    "recipientId": "User_2",
    "categoryId": 1,
    "amount": 10
  }
]
```

Если такого пользователя нет - вернуть 404 ошибку.

## Дополнительная информация

Т.к. данные для анализа уже находятся в топике, необходимо лишь развернуть приложение, которое вычитает их и произведет анализ.
Возможны случаи, когда потребуется перечитать данные заново (приложение не проходит автотесты, либо участник что-то не учел).

Возможны два способа:

- Запускать приложение каждый раз с новым уникальным consumer-group-id. В этом случае, при правильной конфигурации консьюмера,
приложение будет перечитывать топик с данными каждый раз с начала.

- Вручную скинуть оффсеты для топика для заданной consumer-group. Либо, для простоты, возможно скинуть все оффсеты для всех групп, воспользовавшись
следующей командой

```
docker exec -i broker kafka-consumer-groups --bootstrap-server broker:9092 --all-groups --all-topics --reset-offsets --to-earliest --execute
```


##Swagger описание ожидаемого интерфейса
```json
{
   "swagger":"2.0",
   "info":{
      "description":"Api Documentation",
      "version":"1.0",
      "title":"Api Documentation",
      "termsOfService":"urn:tos",
      "contact":{

      },
      "license":{
         "name":"Apache 2.0",
         "url":"http://www.apache.org/licenses/LICENSE-2.0"
      }
   },
   "host":"localhost:8080",
   "basePath":"/",
   "tags":[
      {
         "name":"payment-analytic-controller",
         "description":"Payment Analytic Controller"
      }
   ],
   "paths":{
      "/analytic":{
         "get":{
            "tags":[
               "payment-analytic-controller"
            ],
            "summary":"getAllAnalytic",
            "operationId":"getAllAnalyticUsingGET",
            "produces":[
               "application/json"
            ],
            "responses":{
               "200":{
                  "description":"OK",
                  "schema":{
                     "type":"array",
                     "items":{
                        "$ref":"#/definitions/UserPaymentAnalytic"
                     }
                  }
               },
               "401":{
                  "description":"Unauthorized"
               },
               "403":{
                  "description":"Forbidden"
               },
               "404":{
                  "description":"Not Found"
               }
            },
            "deprecated":false
         }
      },
      "/analytic/{userId}":{
         "get":{
            "tags":[
               "payment-analytic-controller"
            ],
            "summary":"getUserAnalytic",
            "operationId":"getUserAnalyticUsingGET",
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "name":"userId",
                  "in":"path",
                  "description":"userId",
                  "required":true,
                  "type":"string"
               }
            ],
            "responses":{
               "200":{
                  "description":"OK",
                  "schema":{
                     "$ref":"#/definitions/UserPaymentAnalytic"
                  }
               },
               "401":{
                  "description":"Unauthorized"
               },
               "403":{
                  "description":"Forbidden"
               },
               "404":{
                  "description":"Not Found"
               }
            },
            "deprecated":false
         }
      },
      "/analytic/{userId}/stats":{
         "get":{
            "tags":[
               "payment-analytic-controller"
            ],
            "summary":"getPaymentStatsByUser",
            "operationId":"getPaymentStatsByUserUsingGET",
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "name":"userId",
                  "in":"path",
                  "description":"userId",
                  "required":true,
                  "type":"string"
               }
            ],
            "responses":{
               "200":{
                  "description":"OK",
                  "schema":{
                     "$ref":"#/definitions/UserPaymentStats"
                  }
               },
               "401":{
                  "description":"Unauthorized"
               },
               "403":{
                  "description":"Forbidden"
               },
               "404":{
                  "description":"Not Found"
               }
            },
            "deprecated":false
         }
      },
      "/analytic/{userId}/templates":{
         "get":{
            "tags":[
               "payment-analytic-controller"
            ],
            "summary":"getUserTemplates",
            "operationId":"getUserTemplatesUsingGET",
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "name":"userId",
                  "in":"path",
                  "description":"userId",
                  "required":true,
                  "type":"string"
               }
            ],
            "responses":{
               "200":{
                  "description":"OK",
                  "schema":{
                     "type":"array",
                     "items":{
                        "$ref":"#/definitions/UserTemplate"
                     }
                  }
               },
               "401":{
                  "description":"Unauthorized"
               },
               "403":{
                  "description":"Forbidden"
               },
               "404":{
                  "description":"Not Found"
               }
            },
            "deprecated":false
         }
      }
   },
   "definitions":{
      "PaymentCategoryInfo":{
         "type":"object",
         "properties":{
            "max":{
               "type":"number"
            },
            "min":{
               "type":"number"
            },
            "sum":{
               "type":"number"
            }
         },
         "title":"PaymentCategoryInfo"
      },
      "UserPaymentAnalytic":{
         "type":"object",
         "properties":{
            "analyticInfo":{
               "type":"object",
               "additionalProperties":{
                  "$ref":"#/definitions/PaymentCategoryInfo"
               }
            },
            "totalSum":{
               "type":"number"
            },
            "userId":{
               "type":"string"
            }
         },
         "title":"UserPaymentAnalytic"
      },
      "UserPaymentStats":{
         "type":"object",
         "properties":{
            "maxAmountCategoryId":{
               "type":"integer",
               "format":"int32"
            },
            "minAmountCategoryId":{
               "type":"integer",
               "format":"int32"
            },
            "oftenCategoryId":{
               "type":"integer",
               "format":"int32"
            },
            "rareCategoryId":{
               "type":"integer",
               "format":"int32"
            }
         },
         "title":"UserPaymentStats"
      },
      "UserTemplate":{
         "type":"object",
         "properties":{
            "amount":{
               "type":"number"
            },
            "categoryId":{
               "type":"integer",
               "format":"int32"
            },
            "recipientId":{
               "type":"string"
            }
         },
         "title":"UserTemplate"
      }
   }
}
```