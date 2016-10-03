# Java Banregio SDK

El SDK está constituído de tres archivos: 

* BanregioTokenHelper: encapsula el proceso de acceder a un token usando OAuth2
* BanregioApiHelper: una interfaz para utilizar los servicios expuestos por Banregio API
  * Ligar Cuentas
  * Listar Cuentas
  * Listas Transacciones
  * Desligar Cuentas
* Main.java: una CLI mínima para familiarizarse con Banregio API sin necesidad de un setup complicado

Para comenzar a utilizar este proyecto es importante seguir los siguientes pasos:

1. Instalar Java SDK y gradle en su máquina: [Documentación Gradle](https://gradle.org/getting-started-gradle/)
2. Clonar el proyecto de Github
3. Ejecutar **gradle build** para resolver las dependencias
4. Ejecutar **gradle build -PArgs="['-c', 'suclientid', '-s', 'suclientsecret', '-b', 'subaseuri', '-r', 'suredirecturi']" -q**

Usted recibirá una pantalla de bienvenida en la línea de comandos con un menú para usar el API de Banregio interactivamente. 
