<p align="center"> <img src="src/udistrital/avanzada/duelosmagicos/recursos/LogoUD.png" width="20%">  </p>
<h1 align="center">Taller #3</h1>
<p align="center">
  <b>Diego Alejandro Alfonso Lopez<br>
  Sebastian David Trujillo Vargas<br>
  Yhoan Mauricio Bermudez Tique<br>
  31/10/2025<br>
  Programación Avanzada
  </b></p><hr>
<h2>🧙‍♂️ Duelos mágicos</h2>
<p>Duelos Mágicos es una aplicación desarrollada en Java que simula un torneo de magos, donde se realiza un torneo en el que de a dos hechiceros se enfrentan lanzando conjuros en un campo de batalla animado.<br>
El sistema gestiona magos, hechizos, y duelos concurrentes utilizando hilos, además de ofrecer una interfaz gráfica creada con Swing.</p>
<h3>🎯 Objetivo del taller</h3>
<p>El propósito del proyecto es aplicar conceptos de Programación Avanzada en Java, como:<br>
  <li>Concurrencia (Threads)</li>
  <li>Interfaces y Controladores</li>
  <li>Manejo de eventos</li>
  <li>Lectura de archivos de propiedades</li>
  <li>Diseño MVC (Modelo - Vista - Controlador)</li>
</p><hr>
<h3>⚙️ Características principales</h3>

<li><b>Lectura de configuración dinámica:</b> Los datos de magos y hechizos se cargan desde un archivo <code>properties</code>, lo que permite modificar fácilmente los valores sin alterar el código fuente.</li> 
<li><b>Simulación de duelos:</b> Cada mago lanza hechizos por turnos, obteniendo puntajes y efectos según sus conjuros.</li>  
<li><b>Concurrencia con hilos:</b> Los magos son ejecutados en paralelo, haciendo que los enfrentamientos sean más realistas.</li>  
<li><b>Interfaz gráfica visual:</b> El campo de batalla se muestra en pantalla, con indicadores y animaciones que representan cada acción.  </li>  
<li><b>Control centralizado:</b> El controlador principal coordina las interacciones entre modelo, vista y lógica de juego. </li>  
<li><b>Reportes automáticos:</b> El sistema registra los resultados de cada duelo, incluyendo puntajes y tiempos de ejecución.</li> 
 <br><p>Cada paquete cumple una función específica:<br></p>
<li><b>Modelo:</b> Define las clases base <code>Mago</code> y <code>Hechizo</code>, donde se almacena la información y comportamiento principal de los personajes y conjuros.</li>  
<li><b>Vista:</b> Contiene las clases responsables de la interfaz gráfica, representando los duelos en pantalla.  </li>  
<li><b>Control:</b> Implementa la lógica del juego, maneja los hilos de ejecución, los eventos del usuario y la comunicación entre modelo y vista.</li>  
<hr>
<h3>💡 Clases Destacadas</h3>
<li><b>ControlPrincipal:</b> Es el punto central del sistema; inicializa los componentes y coordina los duelos entre magos.</li>  
<li><b>CampoDuelo:</b> Administra el flujo del combate, sincroniza los hilos y gestiona los puntajes.</li>  
<li><b>MagoHilo:</b> Representa el hilo de ejecución de cada mago, permitiendo que ambos lancen hechizos de forma paralela.</li>  
<li><b>PanelDuelo:</b> Es el panel visual donde se representan los ataques y efectos de los magos.    </li>  
<li><b>GestorArchivoPropiedades:</b> Se encarga de leer los datos de magos y hechizos desde el archivo de configuración <code>.properties</code>.</li>
<hr>

<h3>🧠 Conceptos de Programación Aplicados</h3>

<p>El desarrollo de Duelos Mágicos permitió aplicar numerosos conceptos vistos en clase:</p><br>
<li><b>Programación Orientada a Objetos:</b> Con clases, herencia, encapsulamiento y polimorfismo.</li>  
<li><b>Interfaces y Listeners:</b> Para la gestión de eventos durante los duelos.</li>  
<li><b>Manejo de Hilos:</b> uso de <code>Thread</code>, sincronización con <code>synchronized</code> y control de ejecución paralela.</li>  
<li><b>Lectura de Archivos:</b> Con la clase <code>Properties</code> para la carga dinámica de configuraciones.</li>  
<li><b>Eventos de Swing:</b> Respuesta a la interacción del usuario mediante botones y acciones visuales.</li>  
<li><b>Diseño MVC:</b> Separación entre la lógica del programa y la interfaz, facilitando la mantenibilidad del código.</li> 
<hr>

<h3>🚀 Cómo Ejecutar el Proyecto</h3>
<ol><li>Clona este repositorio en tu equipo:</li>
<pre><code>https://github.com/DiegoA923/DuelosMagicos.git</code></pre>
<li>Abre el proyecto en tu IDE preferido.</li>
<li>Ejecuta la clase principal:</li>
<code> udistrital.avanzada.duelosmagicos.Control.Launcher</code>
<li>Selecciona el archivo de configuración cuando el programa lo solicite.</li></ol>
<p>Ejemplo del archivo propiedades:</p>
<pre><code>
#magos
nMagos=10  
mago1.nombre=Alaric
mago1.casa=Grifos
mago2.nombre=Selene
mago2.casa=Lunas
#Hechizos
nHechizos=10
hechizo1.nombre=Bola de Fuego
hechizo1.puntos=20
hechizo2.nombre=Escudo Mágico
hechizo2.puntos=15
</code></pre>
