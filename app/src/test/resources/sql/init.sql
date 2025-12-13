INSERT INTO failed_situation_reason (id,description_ru,description_en,code,is_active,is_default,request_default_failed_flight,is_flight_cancelled) VALUES
    (1,'Задержка прибытия','Flight arrival delay','FLIGHT_DELAY_ARR',true,false,false,false),
    (2,'Задержка вылета','Flight departure delay','FLIGHT_DELAY_DEP',true,false,false,false),
    (3,'Отмена рейса','Flight сancel','FLIGHT_CANCEL',true,false,false,true),
    (4,'Вынужденное изменение маршрута','Flight alternate airport','FLIGHT_ALTERNATE_AIRPORT',true,false,false,false),
    (5,'Срыв стыковки','Flight connection failure','FLIGHT_CONNECTION_FAILURE',true,true,true,false),
    (6,'Непредоставление места','Failure to provide a place','PLACE_NOT_PROVIDED',true,false,false,false),
    (7,'Ограничения ВС','Aircraft operational limitations','AIRCRAFT_RESTRICTIONS',true,false,false,false),
    (8,'Оперативная замена ВС','Operational replacement of aircraft','AIRCRAFT_REPLACEMENT',true,false,false,false),
    (9,'Распоряжение руководителя','Other','OTHER',true,false,false,false),
    (10,'Неприбытие багажа','Baggage has not arrived','BAGGAGE_NOT_ARR',true,false,false,false);
