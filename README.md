Léeme profe, pidió que le diéramos pasos para ejecutar así que aquí va:
1.- Abrir docker desktop: Es un poco redundante pero a veces me olvido de hacerlo, y cuando sale error, me siento tonto.
2.- Arrancar la base de datos: Hacer docker-compose up -d en la terminal de la ubicación del pom.xml del proyecto.
3.- Verificamos que esté corriendo: con docker-compose ps  debe aparecer el contenedor con status up
4.- Arrancamos la aplicacion con: mvn sping-boot:run    va a iniciar toooodo y tenemos que ver hasta que diga Started CruddemoApplication in X seconds (mucas veces son 2.4444)
5.- Abrir el navegador: Dejamos la terminal abierta y vamos a:
http://localhost:8080/web    Interfac personas
http://localhost:8080/web/restaurantes    Interfaz restaurantes
http://localhost:8080/api/restaurante   API REST restaurantes JSON

6.- Finalizar: Después de haber toqueteado por todos lados el programa, volvemos a la terminal y hacemos Ctrl+c, aceptamos todo. Si queremos parar la base de datos hacemos:
docker-compose down

Fin JEAN FRANCO JUAREZ LOZANO .