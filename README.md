# Calculo_distribuido

Implementación de una aplicación de cálculo distribuido usando sockets como medio de comunicación.

La arquitectura básica del sistema consta de los servidores de operación 1 y 2 que realizan los cálculos, el servidor principal o “servidor de cálculo” que
coordina toda la operación y un cliente. 

El cálculo realizado corresponde a el caldulo de la Varianza de un set de datos recibiendo como entrada el rango de los datos y su función de probabilidad.

Para correr la aplicación es necesario correr cada clase por aparte, modificar las direcciones IP correspondientes y tener en cuenta que el servidor de operación se debe ejecutar dos veces; en cada una pasando como argumento el numero de servidor correspondiente (0-1). Es importante tener esto en cuenta ya que a partir de este argumento se define el puerto desde el que trabajará el servidor.
