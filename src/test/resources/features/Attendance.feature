Feature: Attendance Servis işlemlerine olumlu yanıt verme

  Background:
    Given Aşağıdaki talebeler listeye eklenmiş olsun
      | index | adi         | soyadi |
      | t1    | Veli        | Cam    |
      | t2    | Abdurrahman | Kutlu  |
      | t3    | Emre        | Yavuz  |
      | t4    | Bektas      | Isik   |

    Given Aşağıdaki yoklamalar eklenmiş olsun
      | index | zaman               | vakit  |
      | y1    | 2019-01-01 10:00:00 | Sabah  |
      | y2    | 2019-01-01 12:00:00 | Ogle   |
      | y3    | 2019-01-01 14:00:00 | Ikindi |

  Scenario: Yoklamalar listesinini talep ederken olumlu cevap dönmesi
    When Yoklama listesi görüntülenmek istendiğinde
    Then İşlem başarılı bir şekilde tamamlanmalıdır

  Scenario: Yoklama Alma talebinin başarılı olması ve yoklamanın listeye eklenmesi
    When "Sabah" vakti için mevcut talebelerin yoklamaları alındığı zaman
      | index | durum |
      | t1    | var   |
      | t2    | yok   |
      | t3    | var   |
      | t4    | yok   |
    Then İşlem başarılı bir şekilde tamamlanmalıdır

  Scenario: Yoklama Silme işlemi yapıldığında Yoklama kaydının silinmesi ve sislinen yoklamada yok yazılan talebelerin toplam devamsızlığı bir azaltılmalı
    When Kullanıcı seçilen "y2" indexli yoklamayı silmek istediğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir

#  Scenario: Yoklama Güncelleme işlemi yapıldığında güncellemenin başarılı olması ve listede güncellenen vakitle listelenmesi
#    When Kullanıcı "y1" idlı yoklamayı güncellemek istediğinde
#      | index | zaman               | vakit  |
#      | y1    | 2019-01-01 10:00:00 | Ikindi |
#    Then İşlem başarılı bir şekilde tamamlanmalıdır
#    Then "y1" idli yoklamayı güncelleyince "Ikindi" vakitli olarak listelenmelidir
