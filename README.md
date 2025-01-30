Разработан сервис для работы с клиентами, который предоставляет следующие возможности:  
1) Рест-Контроллеры, выполняющие основные функции:  
   Добавление нового клиента, Добавление нового контакта клиента, Получение списка клиентов, Получение информации по заданному клиенту,  
   Получение списка контактов заданного клиента, Получение списка контактов заданного типа заданного клиента  
2) Сервисный слой, обрабатывающий запросы контроллера:  
   * При добавлении клиента идет проверка на null и на валидность внесенных данных, и после этого клиент сохраняется в базу  
   * При добавлении нового контакта клиента идет проверка на существование этого клиента в базе и на валидность данных, после обновление данных  
   * Получение списка клинтов  
   * При попытке получения данных определенного клиента проходит проверка на его существование, после возвращается информация о нем  
   * Получение списка контактов клиента проходит после проверки его существования  
   * Получение списка определенного типа контактов проходит, после проверки существования. Здесь вторым аргументом нужно передать либо "email", либо "phone" без учета регистра  
   * Также в сервисном слое есть два приватных метода для проверки валидности вносимых данных  
   * В каждом методе добавлены логи и в случае ошибков пробрасываются исключения  
