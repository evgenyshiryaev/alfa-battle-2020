GET /analytic

```json
[
  {
    "userId": "XA1VWF",
    "totalSum": 1053422.89,
    "analyticInfo": {
      "1": {
        "min": 34.65,
        "max": 9858.56,
        "sum": 327288.15
      },
      "2": {
        "min": 56.21,
        "max": 9879.46,
        "sum": 329637.89
      },
      "3": {
        "min": 43.01,
        "max": 9969.21,
        "sum": 396496.85
      }
    }
  },
  {
    "userId": "XA1VWB",
    "totalSum": 1013696.72,
    "analyticInfo": {
      "1": {
        "min": 211.15,
        "max": 9859.94,
        "sum": 325144.55
      },
      "2": {
        "min": 291.22,
        "max": 9808.98,
        "sum": 337780.62
      },
      "3": {
        "min": 196.04,
        "max": 9967.08,
        "sum": 350771.55
      }
    }
  },
  {
    "userId": "XAABAA",
    "totalSum": 1002161.49,
    "analyticInfo": {
      "1": {
        "min": 63.17,
        "max": 9996.61,
        "sum": 358920.72
      },
      "2": {
        "min": 66.27,
        "max": 9871.6,
        "sum": 311109.6
      },
      "3": {
        "min": 91.35,
        "max": 9925.74,
        "sum": 332131.17
      }
    }
  },
  {
    "userId": "XA1VWJ",
    "totalSum": 836019.57,
    "analyticInfo": {
      "1": {
        "min": 234.43,
        "max": 9681.96,
        "sum": 264960.89
      },
      "2": {
        "min": 230.56,
        "max": 9803.16,
        "sum": 295912.92
      },
      "3": {
        "min": 217.08,
        "max": 9731.27,
        "sum": 275145.76
      }
    }
  },
  {
    "userId": "XA3SZV",
    "totalSum": 1109068.94,
    "analyticInfo": {
      "1": {
        "min": 198.09,
        "max": 9630.8,
        "sum": 375451.49
      },
      "2": {
        "min": 116.1,
        "max": 9982.74,
        "sum": 340111.08
      },
      "3": {
        "min": 794.54,
        "max": 9604.29,
        "sum": 393506.37
      }
    }
  }
]
```


GET /analytic/XAABAA

```json
{
  "userId": "XAABAA",
  "totalSum": 1002161.49,
  "analyticInfo": {
    "1": {
      "min": 63.17,
      "max": 9996.61,
      "sum": 358920.72
    },
    "2": {
      "min": 66.27,
      "max": 9871.6,
      "sum": 311109.6
    },
    "3": {
      "min": 91.35,
      "max": 9925.74,
      "sum": 332131.17
    }
  }
}
```

GET /analytic/XAABAA/stats 
(в тесте проверить только на rareCategoryId?)
в oftenCategoryId может быть у них и 3
```json
{
  "oftenCategoryId": 1,
  "rareCategoryId": 2, 
  "maxAmountCategoryId": 1,
  "minAmountCategoryId": 2
}
```

GET /analytic/XA1VWF/templates

```json
[
  {
    "recipientId": "XA6BFO",
    "categoryId": 1,
    "amount": 9306.45
  },
  {
    "recipientId": "XA6F9K",
    "categoryId": 3,
    "amount": 1984.97
  }
]
``` 