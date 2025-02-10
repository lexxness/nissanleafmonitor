# Nissan Leaf OBD-II App

Это приложение для Android, которое подключается к OBD-II адаптеру и отображает данные о Nissan Leaf ZE0, включая общее напряжение, напряжение по ячейкам, процент заряда и настройки климата.

## Установка

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/ваш_репозиторий/nissan_leaf_obd.git
   ```

2. Откройте проект в Android Studio.

3. Убедитесь, что все зависимости загружены в `build.gradle`:
   - Откройте файл `build.gradle` вашего приложения (обычно находится в папке `app`).
   - Убедитесь, что в разделе `dependencies` указаны следующие строки:
     ```groovy
     implementation 'com.github.pires:obd-java-api:1.0.0' // Библиотека для работы с OBD-II
     implementation 'com.github.ihsanbal:LoggingInterceptor:3.0.0' // Логирование
     implementation 'androidx.appcompat:appcompat:1.3.1' // Поддержка старых версий Android
     implementation 'androidx.constraintlayout:constraintlayout:2.0.4' // ConstraintLayout
     ```
   - Если этих строк нет, добавьте их в раздел `dependencies`.

4. Убедитесь, что у вас правильно настроены файлы Gradle:
   - В `build.gradle` (Project Level) добавьте:
     ```groovy
     buildscript {
         repositories {
             google()
             jcenter()
         }
         dependencies {
             classpath 'com.android.tools.build:gradle:4.1.0' // Замените на актуальную версию
         }
     }

     allprojects {
         repositories {
             google()
             jcenter()
         }
     }
     ```

5. Убедитесь, что у вас установлен плагин Android Gradle:
   - В файле `build.gradle` (Project Level) должно быть следующее:
     ```groovy
     buildscript {
         repositories {
             google()
             jcenter()
         }
         dependencies {
             classpath 'com.android.tools.build:gradle:4.1.0' // Замените на актуальную версию
         }
     }
     ```

6. Убедитесь, что у вас есть файл `settings.gradle` в корне проекта:
   ```groovy
   include ':app'
   ```

7. Убедитесь, что у вас установлены все необходимые компоненты Android SDK:
   - Откройте `SDK Manager` в Android Studio (можно найти в меню `Tools` -> `SDK Manager`).
   - Убедитесь, что у вас установлены Android SDK Platform, Android SDK Build-Tools и Android Emulator (если вы планируете тестировать на эмуляторе).

8. Подключите ваше устройство Android и включите отладку по USB.

9. Соберите APK:
   - Перейдите в меню `Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`.
   - Если у вас нет этого пункта, убедитесь, что ваш проект настроен правильно, как указано выше.

10. Установите APK на ваше устройство.

## Использование

1. Запустите приложение.
2. Нажмите кнопку "Настройки", чтобы выбрать OBD-II адаптер.
3. Приложение начнет отображать данные о вашем автомобиле.

## Лицензия

Этот проект лицензирован под лицензией GPL-3.0. 

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:4.1.0' // Замените на актуальную версию
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
} 