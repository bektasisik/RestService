Feature: Attendance Servis işlemlerine olumlu yanıt verme

  Scenario: Yoklamalar listesinini talep ederken olumlu cevap dönmesi
    Given X,Y,Z yoklamaları alınmış olsun
    When Yoklama listesi görüntülenmek istendiğinde
    Then İşlem başarılı bir şekilde tamamlanmalı
    And X,Y,Z yoklamaları listede görüntülenmeli

  Scenario: Yoklama Alma talebinin başarılı olması ve yoklamanın listeye eklenmesi
    Given X,Y,Z talebeleri talebe listesine eklenmiş olsun
    And A Vakti seçilmiş olsun
    And X,Y,Z talebelerinin yoklamaları alınmış olsun
    When Yoklama al tuşuna basıldığında
    Then Talebelerin yoklaması alınmış olmalı
    And Yoklamada yok yazılan talebelerin toplam devamsızlığı 1 artmalı
    And Alınan yoklamanın yoklama listesine eklenmiş olmalı

  Scenario: Yoklama Silme işlemi yapıldığında Yoklama kaydının silinmesi ve sislinen yoklamada yok yazılan talebelerin toplam devamsızlığı bir azaltılmalı
    Given X,Y,Z talebeleri eklenmiş olsun
    And A,B,C yoklamaları alınmış olsun
    When Kullanıcı seçilen A yoklamasını silmek istediğinde
    Then Talebe Listesinden A yoklaması silinniş olmalı
    And Yoklama Listesi A yoklaması olmadan listelenmeli
    And Sislinen yoklamada yok yazılan talebelerin toplam devamsızlığı bir azaltılmalı

  Scenario: Yoklama Güncelleme işlemi yapıldığında güncellemenin başarılı olması ve listede güncellenen vakitle listelenmesi
    Given X,Y,Z talebeleri eklenmiş olsun
    And A,B,C yoklamaları alınmış olsun
    When Kullanıcı A adlı talebeyi güncellemek istediğinde
