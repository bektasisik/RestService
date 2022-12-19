Feature: Student-Attendance listeleme işlemleri

  Background:
    Given Aşağıdaki talebeler listeye eklenmiş olsun
      | index | adi         | soyadi |
      | t1    | Veli        | Cam    |
      | t2    | Abdurrahman | Kutlu  |
      | t3    | Emre        | Yavuz  |
      | t4    | Bektas      | Isik   |

    Given Aşağıdaki yoklamalar eklenmiş olsun
      | index | vakit  |
      | y1    | Sabah  |
      | y2    | Ogle   |
      | y3    | Ikindi |

    Given "Sabah" vakti için mevcut talebelerin yoklamaları alındığı zaman
      | index | durum |
      | t1    | var   |
      | t2    | yok   |
      | t3    | var   |
      | t4    | yok   |

  Scenario: Yoklaması alınan talebelerin yoklama sonucu seçilen yoklamaya göre listelenmesi
    When "y1" yoklaması görüntülenmek istendiğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir

 Scenario: Yoklaması alınan talebelerin yoklama sonucu seçilen talebeye göre listelenmesi
    When "t1" numaralı talebenin yoklamaları görüntülenmek istendiğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir
