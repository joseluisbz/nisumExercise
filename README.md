# nisumExercise
Ejercicio técnico Java - Nisum Chile

**NOTA:**
Este proyecto fue creado con **Spring Tool Suite 4** versión *4.13.0*
Se incluye el script autogenerado en el archivo *nisumExercise/CreateSchema(CodeFromConsole).SQL* para crear las tablas de la base de datos en H2.


**¿Cómo Probarlo?**

Usando **Git**, se puede clonar este repositorio:
```
git clone https://github.com/joseluisbz/nisumExercise.git
cd nisumExercise\joseluisbz-ms-usuarios
mvn spring-boot:run
```

Para probarlo se ha usado **Postman**.
En la carpeta *nisumExercise* encontrará el archivo *NisumExercise.postman_collection.json* que es una Collection de Postman, la cual se debe importar.

Aquí dos enlaces para saber como realizar una importación en Postman.
- https://kb.datamotion.com/?ht_kb=postman-instructions-for-exporting-and-importing
- https://learning.postman.com/docs/getting-started/importing-and-exporting-data/

**Pruebas en Postman**
* Probando Autenticación

![Fill Body for Auth](./Postman-Images/Auth-Post%20(GetToken).png)
En la parte izquierda seleccione *Collections*, luego la colección *NisumExercise*. Se elige el *Request* *auth*. Inmediatamente se puede hacer consumo de la API. En la parte inferior se puede ver el *Response*. Al consumir el servicio se obtiene el token, su valor, inmediatamente se almacena en una variable temporal llamada *tokenNisum*, para no requerir escribirla cuando se consuman otras API's.

![Fill Body for Auth](./Postman-Images/Auth-Post%20(FillBody).png)
Según la imagen anterior se pueden ingresar las variables requeridas por el controller de Java, para el ejemplo: *user* y *password*.

![Reviewing Generated Token](./Postman-Images/ReviewingGeneratedToken.png)
La imagen anterior muestra el *token* generado y almacenado en la variable (*Environments*, *Globals*) *tokenNisum*.

* Listando Usuarios

![Listing Users](./Postman-Images/ListingUsers_Get.png)
Dentro de todos los Requests de la colección *NisumExercise* para la api *users* en la respectiva pestaña *Headers*, encontrará una variable llamada *Authorization* cuyo valor será tomado de la variable global (o de colección) *tokenNisum* que previamente había sido asignada.

* Creando un usuario

![Wrong Pattern Post](./Postman-Images/WrongPattern_Post.png)
La imágen anterior muestra que se ha tratado de crear un Usuario con un password que no cumple con el patrón asignado (variable *format.password* localizada en el archivo *joseluisbz-ms-usuarios/src/main/resources/application.properties*)

![Successful Post](./Postman-Images/Successful_Post.png)
Probando la creación de usuario con los valores requeridos, se puede ver que se le asigna un nuevo *id* en la parte inferior del *Response*.

* Editando un Usuario
![Not Found Put](./Postman-Images/NotFound_Put.png)
Al tratar de un Usuario con *id* inexistente se puede ver que retorna el código *404 Not Found*.

![Successful Put](./Postman-Images/Successful_Put.png)
Si se el *id* de Usuario existe, y los campos en formato *json* son satisfechos se puede realizar la edición.

* Viendo un Usuario
![Not Valid Token View](./Postman-Images/NotValidToken_View.png)
Se hizo la prueba con la expiración del *token* generado por *auth*!

![Showing Token View](./Postman-Images/ShowingToken_View.png)
También se puede ver que el *token* generado se almacena a nivel de Usuario como se requirió.

