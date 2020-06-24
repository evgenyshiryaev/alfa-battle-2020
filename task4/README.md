###"Эластичные кредиты"

Есть 2 входных файла JSON:

person.json - данные о клиентах, формат одной записи представлен ниже:

```json
{
      "ID":"29",
      "DocId":"702821510",
      "FIO":"Phoebe Whitehouse",
      "Birthday":"7/12/1971",
      "Salary":"722.44",
      "Gender":"F"
}
```

где:

- ID - уникальный номер клиента,

- DocId - документ (формат 9 цифр)

- FIO - ФИО,

- Birthday - дата рождения в формате MM/dd/yyyy,

- Salary - средний заработок клиента, который записан в сотых, т.е. 201.02 означает 20102 руб.,

- Gender - пол

loans.json - данные о клиентах, формат одной записи представлен ниже:

```json
{
      "Loan":"631553",
      "PersonId":"68",
      "Amount":"4030",
      "StartDate":"6/1/2019",
      "Period":"1"
}
```

где:

- Loan - номер договора,

- PersonId  - номер клиента

- Amount  - сумма кредита, которая записана в сотых, т.е. 201.02 означает 20 102

- StartDate - - дата начала кредта в формате MM/dd/yyyy,

- Period  - срок кредита в годах

###Необходимо реализовать следующее:
- Из указанных выше файлов прочитать информацию, преобразрать ее и положить в ElasticSearch в разные индексы
- Реализовать Rest интерфейс для загрузки данных в ElasticSearch, а так же для извлечения данных. Приложение должно быть доступно по порту ????

### Уточнения
При загрузке данных в ElasticSearch должны быть произведены следующие изменения:

   - persons 
   
        - Birthday - должна храниться в виде 1945-05-03
        
        - Salary - должен храниться в рублях, а не в сотых
   
   - loans 
           
        - PersonId - вместо это поля, должно появиться поле Document, которое будет хранить DocId из Persons. 
        
        - Amount - сумма кредита доолжна быть в рублях, а не в сотых.
        
        - StartDate - дата начала кредта, должна юыть в рублях, а не сотых
        
        - Period - срок кредита, должден бть в месяцах
    


### Ожидаемый интерфейс

####Хелсчек

- GET /admin/health

Ожидаем получить 200 ответ

```json
{"status":"UP"}
```

####Загрузка клиентов

- POST /loadPersons

Загрузка преобразованных данных клиентов в ElasicSearch

Ожидаем получить 200 ответ

```json
{"status":"OK"}
```

###Загрузка договоров

- POST /loadLoans

Загрузка преобразованных данных клиентов в ElasicSearch, при этом, для того, что бы на договоре проставить Document, 
необходимо сделать запрос в ElasticSearch, что бы получить номсер документа клиента.

Ожидаем получить 200 ответ

```json
{"status":"OK"}
```

###Запрос на кредитную историю клиента

- GET /creditHistory/{document}/

Вывод информации о всех кредитных договорах клиента, с указанным номером документа - Document

Ответ

```json
{
    "countLoan": 4,
    "sumAmountLoans": 1058400.0,
    "loans": [
        {
            "loan": "434224",
            "amount": 7100,
            "document": "737767072",
            "startdate": "2019-09-18",
            "period": 12
        },
        {
            "loan": "917105",
            "amount": 283600,
            "document": "737767072",
            "startdate": "2019-12-22",
            "period": 12
        },
        {
            "loan": "692147",
            "amount": 300800,
            "document": "737767072",
            "startdate": "2016-08-01",
            "period": 24
        },
        {
            "loan": "145020",
            "amount": 466900,
            "document": "737767072",
            "startdate": "2017-01-16",
            "period": 36
        }
    ]
}
```

где:

   - countLoan - количество договоров,

   - sumAmountLoans - сумма всех договоров,
      
   - loans - массив договоров 

Если такого клиента нет - вернуть 404 ошибку.

###Получение списка кредитных договоров, которые закрыты на певрое число текущего месяца

- GET /creditClosed

Ответ

```json
[
    {
        "loan": "222398",
        "amount": 265400,
        "document": "074658188",
        "startdate": "2017-09-22",
        "period": 12
    },
    
        "loan": "826942",
        "amount": 329400,
        "document": "788117788",
        "startdate": "2016-01-29",
        "period": 48
    }
```


 ###Получение информации по клиентам и договорам. 
 
 Ответ доложен быть остортирован до дате рождения, по убыванию(сортировку производить при запросе из ElasticSearch).

-GET /LoansSortByPersonBirthday

Ответ

```json
[
    {
        "id": null,
        "docid": "840704451",
        "fio": "John Isaac",
        "birthday": "19.08.1989",
        "salary": 58295.0,
        "gender": "M",
        "loans": [
            {
                "loan": "771916",
                "amount": 337600,
                "document": "840704451",
                "startdate": "2019-11-09",
                "period": 48
            },
            {
                "loan": "504544",
                "amount": 358900,
                "document": "840704451",
                "startdate": "2018-06-10",
                "period": 36
            },
            {
                "loan": "699247",
                "amount": 464400,
                "document": "840704451",
                "startdate": "2018-10-30",
                "period": 36
            },
            {
                "loan": "783101",
                "amount": 139300,
                "document": "840704451",
                "startdate": "2017-02-19",
                "period": 36
            }
        ]
    },
    {
        "id": null,
        "docid": "023665566",
        "fio": "Denny Tanner",
        "birthday": "25.03.1989",
        "salary": 80713.0,
        "gender": "M",
        "loans": [
            {
                "loan": "631553",
                "amount": 403000,
                "document": "023665566",
                "startdate": "2019-06-01",
                "period": 12
            },
            {
                "loan": "598452",
                "amount": 198500,
                "document": "023665566",
                "startdate": "2015-09-28",
                "period": 36
            },
            {
                "loan": "151915",
                "amount": 13600,
                "document": "023665566",
                "startdate": "2019-06-15",
                "period": 12
            },
            {
                "loan": "368342",
                "amount": 350500,
                "document": "023665566",
                "startdate": "2017-02-06",
                "period": 48
            },
            {
                "loan": "633056",
                "amount": 482900,
                "document": "023665566",
                "startdate": "2016-07-01",
                "period": 12
            }
        ]
    }
]
```