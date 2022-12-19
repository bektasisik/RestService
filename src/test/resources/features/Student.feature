Feature: Student CRUD işlemleri

  Scenario: Talebe Ekleme işleminin başarılı olması ve talebenin listeye eklenmesi
    When Kullanıcı talebenin Adını "Bektaş" Soyadını "Işık" olarak kaydetmek istediğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir

  Scenario Outline: Talebeler listesini talep ederken talebelerin tamamının gözükmesi
    Given "<adi>" adi "<soyadi>" soyadi ile talebeler eklenmiş olsun
    When Kullanıcı talebeler listesini talep ettiğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir
    Examples:
      | adi         | soyadi |
      | Veli        | Cam    |
      | Abdurrahman | Kutlu  |
      | Emre        | Yavuz  |
      | Bektas      | Isik   |

  Scenario: Talebe güncelleme işlemi yapıldığında güncellemenin başarılı olması
    Given Aşağıdaki talebeler eklenmiş olsun
      | id | adi         | soyadi |
      | 1  | Veli        | Cam    |
      | 2  | Abdurrahman | Kutlu  |
      | 3  | Emre        | Yavuz  |
      | 4  | Bektas      | Isik   |
    When Kullanıcı 3 idli talebeyi "Emre" adi "Yavuzz" soyadi olarak güncellemek istediğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir
    Then Talebe listesi toplam 4 adet talebe içermelidir
    Then 3 idli talebenin adı "Emre" soyadı "Yavuzz" olmalıdır

  Scenario: Talebe Güncelleme işlemi yapıldığında seçilen talebenin güncellemesinin başarısız olması
    Given Aşağıdaki talebeler eklenmiş olsun
      | id | adi  | soyadi |
      | 1  | Veli | Cam    |
    When Kullanıcı 1 idli talebeyi hatalı şekilde güncellemek istediğinde
    Then Geriye başarısız status kodu dönmesi

  Scenario: Talebe Silme işlemi yapıldığında silmenin başarılı olması
    Given Aşağıdaki talebeler eklenmiş olsun
      | id | adi  | soyadi |
      | 1  | Veli | Cam    |
    When Kullanıcı 1 idli talebeyi silmek istediğinde
    Then İşlemin başarılı olarak gerçekleşmesi beklenir

  Scenario Outline: talebe eklemesindeki hatalı kayıtların kontrolü
    When Kullanıcı talebenin Adını "<adi>" Soyadını "<soyadi>" olarak hatalı şekilde kaydetmek istediğinde
    Then Geriye başarısız status kodu dönmesi
    Examples:
      | adi                   | soyadi                |
      | as                    | as                    |
      |                       | asd                   |
      | asd                   |                       |
      |                       |                       |
      | aaaaaaaaaaaaaaaaaaaaa | aaaaaaaaaaaaaaaaaaaaa |

