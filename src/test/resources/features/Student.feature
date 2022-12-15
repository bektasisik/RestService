Feature: Student CRUD işlemleri

  Scenario: Talebe Ekleme isteğinin başarılı olması ve talebenin listeye eklenmesi
    When Kullanıcı talebenin Adını "Bektaş" Soyadını "Işık" olarak kaydetmek istediğinde
    Then Geriye başarılı status kodu dönmesi
    Then Talebe listesi toplam 1 adet talebe içermelidir

  Scenario Outline: Talebeler listesini talep ederken olumlu cevap dönmesi
    Given "<adi>" adi "<soyadi>" soyadi ile talebeler eklenmiş olsun
    When Talebe listesi görüntülenmek istediğinde
    Then Geriye başarılı status kodu dönmesi
    Examples:
      | adi         | soyadi |
      | Veli        | Cam    |
      | Abdurrahman | Kutlu  |
      | Emre        | Yavuz  |
      | Bektas      | Isik   |

  Scenario: Talebe Güncelleme işlemi yapıldığında güncellemenin başarılı olması
    Given Aşağıdaki talebeler eklenmiş olsun
      | id | adi         | soyadi |
      | 1  | Veli        | Cam    |
      | 2  | Abdurrahman | Kutlu  |
      | 3  | Emre        | Yavuz  |
      | 4  | Bektas      | Isik   |
    When Kullanıcı 3 idli talebeyi "Emre" "Yavuzz" olarak güncellemek istediğinde
    Then Geriye başarılı status kodu dönmesi
    Then Talebe listesi toplam 9 adet talebe içermelidir
    Then 3 idli talebenin adı "Emre" soyadı "Yavuzz" olmalıdır

  Scenario: Talebe Güncelleme işlemi yapıldığında seçilen talebenin güncellemesinin başarısız olması
    Given Aşağıdaki talebeler eklenmiş olsun
      | id | adi         | soyadi |
      | 1  | Veli        | Cam    |
      | 2  | Abdurrahman | Kutlu  |
      | 3  | Emre        | Yavuz  |
      | 4  | Bektas      | Isik   |
    When Kullanıcı 1 idli talebeyi hatalı şekilde güncellemek istediğinde
    Then Geriye başarısız status kodu dönmesi
    Then 1 idli talebenin adı "Veli" soyadı "Cam" olmalıdır

  Scenario: Talebe Silme işlemi yapıldığında silmenin başarılı olması ve listede silinen talebenin olmaması
    Given Aşağıdaki talebeler eklenmiş olsun
      | id | adi         | soyadi |
      | 1  | Veli        | Cam    |
      | 2  | Abdurrahman | Kutlu  |
      | 3  | Emre        | Yavuz  |
      | 4  | Bektas      | Isik   |
    When Kullanıcı 3 idli talebeyi silmek istediğinde
    Then Geriye başarılı status kodu dönmesi


#  Scenario Outline: talebe eklemesindeki hatalı kayıtların kontrolü
#    When Kullanıcı talebenin Adını "<adi>" Soyadını "<soyadi>" olarak hatalı şekilde kaydetmek istediğinde
#    Then Kullanıcı talebe kayıt işleminin başarısız olduğunu görür
#    Examples:
#      | adi | soyadi |
#      | as  | as     |
#      |     | asd    |
#      | asd |        |
#      |     |        |
#      | "   | .      |
#