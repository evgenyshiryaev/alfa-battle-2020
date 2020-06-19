###Анализируй это

Api должен предоставлять следующий Rest интерфейс.

В топике кафки RAW_PAYMENTS находятся данные по платежам пользователей.

Формат одной записи представлен ниже:

```
payment1:{"ref":"U030306190000188", "categoryId":1, "userId":"XAABAA", "recipientId":"XA3SZV", "desc":"Платеж за услуги", "amount":10.0}
```

где payment1 - ключ записи.
Все остальное после двоеточия является данными по платежу.

Необходимо реализовать следующую логику:
- Вычитать все данные из топика Кафки
- Выполнить агрегацию данных для возможности отображения аналитики по платежам пользователей
- Реализовать Rest интерфейс для доступа к аналитике по пользователям

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

- Платеж повторяется три и более раза
- У платежей совпадают велечина, категория, от кого и кому платеж был проведен

Реализовать интерфейс, по которому можно будет получать шаблоны платежей пользователя. 

### Ожидаемый интерфейс

- GET /analytic

Возвращает аналитику платежей по всем пользователям

Пример ответа

```json
[
  {
    "userId": "User_1",
    "totalSum": 10,
    "analyticInfo": {
      "1": {
        "min": 10,
        "max": 10,
        "sum": 10
      }
    }
  }
]
```

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
                        "$ref":"#/definitions/PaymentAnalyticsResult"
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
                     "$ref":"#/definitions/PaymentAnalyticsResult"
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
      "PaymentAnalyticsResult":{
         "type":"object",
         "properties":{
            "analyticInfo":{
               "type":"object",
               "additionalProperties":{
                  "$ref":"#/definitions/PaymentGroupInfo"
               }
            },
            "totalSum":{
               "type":"number"
            },
            "userId":{
               "type":"string"
            }
         },
         "title":"PaymentAnalyticsResult"
      },
      "PaymentGroupInfo":{
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
         "title":"PaymentGroupInfo"
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