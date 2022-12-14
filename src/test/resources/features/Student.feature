Feature: Student CRUD işlemleri

  Scenario: Talebe Ekleme isteğinin başarılı olması ve talebenin listeye eklenmesi
    When Kullanıcı talebenin Adını "Bektaş" Soyadını "Işık" olarak kaydetmek istediğinde
    Then Geriye başarılı status kodu dönmesi

#  Scenario: Talebeler listesini talep ederken olumlu cevap dönmesi
#    Given X,Y,Z talebeleri eklenmiş olsun
#    When Talebe listesi görüntülenmek istendiğinde
#    Then Geriye 200 http durum kodu dönmesi
#
#  Scenario: Talebe Silme işlemi yapıldığında talebenim kaydının silinmesi
#    Given X,Y,Z talebeleri eklenmiş olsun
#    When Kullanıcı X adlı talebeyi silmek istediğinde
#    And Talebe listesinde bulunan Silme tuşuna bastığında
#    Then Talebe Listesinden X adlı talebe silinniş olmalı
#    And Talebe Listesi X kişisi olmadan listelenmeli
#
#  Scenario: Talebe Güncelleme işlemi yapıldığında güncellemenin başarılı olması ve listede yeni isim soyisimle beklenmesi
#    Given X,Y,Z talebeleri eklenmiş olsun
#    When Kullanıcı Y adlı talebeyi güncellemek istediğinde
#    And Talebe listesinde bulunan güncelleme butonuna basar
#    And Güncellenen yeni Ad Soyad kıssımlarını girer
#    And Güncelle tuşuna basar
#    Then Talebenin yeni Ad ve Soyadı listeye aynı okul numrası ile güncellenir
#    And Talebe listesi Y adlı talebenin yeni ad soyadı ile listenmeli
#
#  Scenario Outline: talebe eklemesindeki hatalı senaryolar
#    When "<adi>" adı "<soyadi>" soyadı ile talebe eklenmek istendiğinde
#    Examples:
#      | adi | soyadi |
#      | as  | 23     |
#      |     | asd    |
#      | asd |        |
#      |     |        |
#    Then "417" hatası vermeli
#
#  Scenario: Talebeler listesinini talep ederken olumlu cevap dönmesi
#    Given X,Y,Z talebeleri eklenmiş olsun
#    When Talebe listesi görüntülenmek istendiğinde
#    Then İşlem başarılı bir şekilde tamamlanmalı
#    And Geriye 200 http durum kodu dönmesi
#    And Talebe Listesini Dönmesi
#
#  Scenario: Talebe listesine ekleme yapıldığı zaman olumlu cevap dönmesi
#    Given Talebenin adı soyadı girilmiş olmalı
#    When Ekle tuşuna basıldğı zaman
#    Then İşlem başarılı bir şekilde tamamlanmalı
#    And Geriye 200 http durum kodu dönmesi
#    And Talebe Listesini Dönmesi
#
#  Scenario: Talebe silme işlemi yapıldığı zaman olumlu cevap vermesi
#    Given X,Y,Z talebeleri eklenmiş olsun
#    When X adlı talebe silinmek istendiği zaman
#    Then İşlem başarılı bir şekilde tamamlanmalı
#    And Geriye 200 http durum kodu dönmesi
#    And Talebe Listesini Dönmesi
#
#  Scenario: Talebeler listesinini talep ederken olumlu cevap dönmesi
#    Given X,Y,Z talebeleri eklenmiş olsun
#    When Y adlı talebe güncellenmek istendiği zaman
#    Then İşlem başarılı bir şekilde tamamlanmalı
#    And Geriye 200 http durum kodu dönmesi
#    And Talebe Listesini Dönmesi
