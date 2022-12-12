Feature: Student Api İsteklerine olumlu yanıt verme
  Scenario: Talebeler listesinini talep ederken olumlu cevap dönmesi
    Given Talebe Listesi mevcut
    And Servis Ayakta
    When Kullanıcı istek yaptığında
    Then Geriye 200 http durum kodu döner
    And Talebe Listesini Döner