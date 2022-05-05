Приложение, отображающее список сообщений

На сервере есть json файлы. До них можно добраться по ссылке: https://<server_name>/endpoint/{PAGE}.json, где PAGE - номер файла от 0 до какого-то произвольного числа, например, 15.
Каждый файл - это json массив из 50 сообщений. Сообщение содержит поля id, time и text.

Пример файла:  
[  
  {
    "id": "c4ca4238a0b923820dcc509a6f75849b",
    "time": 1463451777180,
    "text": "Message 1"
  },  
  {
    "id": "c81e728d9d4c2f636f067f89cc14862c",
    "time": 1463454782276,
    "text": "Message 2"
  },  
  {
    "id": "eccbc87e4b5ce2fe28308fd9f2a7baf3",
    "time": 1463457782276,
    "text": "http://audiobooks3.ga/im/00019e08ea_200crop.jpg"
  },  
…
]  

Приложение должно загрузить сообщения с сервера и показать их в списке. 
Юзер может удалить сообщение. Предпочтительно - свайпом, но можно и долгим нажатием.
Плюсом будет:

- постраничная подгрузка при скролле
- кэширование, чтобы приложение при старте показывало ранее загруженные сообщения
- некоторые сообщения в поле text вместо текста содержат ссылку на картинку, хорошо бы подгрузить и показать эту картинку