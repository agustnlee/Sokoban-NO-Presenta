package modelo.persistencia;

import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.nomovible.EntidadNoMovible;
import modelo.factory.nomovible.*;
import modelo.factory.movible.*;
import modelo.entidades.Tablero;
import modelo.entidades.Coordenada;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorArchivo {
    private static GestorArchivo instancia;

    private Map<String, CreadorNoMovible> creadoresNoMovibles;
    private Map<String, CreadorMovible> creadoresMovibles;

    private GestorArchivo() {
        creadoresNoMovibles = new HashMap<>();
        creadoresMovibles = new HashMap<>();
    }

    public static GestorArchivo getInstancia() {
        if (instancia == null) {
            instancia = new GestorArchivo();
        }
        return instancia;
    }
    // --- INICIALIZACIÓN DE FÁBRICAS ---
    public void inicializarCreadores() {
        // Mapeo del texto del .txt directo a su fábrica creadora correspondiente
        
        // Terrenos (No Movibles)
        this.creadoresNoMovibles.put("P", (CreadorNoMovible) new CreadorPared());
        this.creadoresNoMovibles.put("CV", (CreadorNoMovible) new CreadorCasillaVacia());
        this.creadoresNoMovibles.put("CD", (CreadorNoMovible) new CreadorCasillaDestino());
        this.creadoresNoMovibles.put("CR", (CreadorNoMovible) new CreadorCasillaResbaladiza());
        this.creadoresNoMovibles.put("M1", (CreadorNoMovible) new CreadorMuro()); 
        this.creadoresNoMovibles.put("CL1", (CreadorNoMovible) new CreadorCasillaCandado()); 
        
        // Entidades (Movibles) - Se agregarán cuando desarrollen esa rama
        // this.creadoresMovibles.put("J", new ConcreteCreadorJugador());
        // this.creadoresMovibles.put("CN", new ConcreteCreadorCajaNormal());
        // ...
        
    }
    public Tablero cargarMapa(int nivel){
        // Aquí se implementaría la lógica para cargar el mapa desde un archivo o recurso.
        // El nivel corresponde al número de nivel que queremos cargar.
        
        // 1. Definir la ruta del archivo (ajusta la carpeta según tu proyecto)
        String rutaArchivo = "src/main/resources/niveles/nivel_" + nivel + ".txt";
        
        Tablero  tablero = new Tablero(); 

        // 2. Lectura segura del archivo (el try-with-resources cierra el archivo automáticamente)
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo))) {
            
            List<String> lineas = br.lines().toList();
            construirNoMovibles(lineas, tablero);
            construirMovibles(lineas, tablero);

        } catch (java.io.IOException e) {
            System.err.println("Error al cargar el archivo del nivel " + nivel + ": " + e.getMessage());
        }

        // 3. Retornamos el Tablero (o la clase Partida) inicializado con la grilla ya construida
        return tablero;


    }

    private void construirNoMovibles(List<String> lineas, Tablero tablero) {
        // Este método se encargaría de recorrer las líneas leídas del archivo y construir
        // las entidades no movibles en el tablero usando las fábricas correspondientes.
            String linea;
            int fila = 0;
            // Leemos línea por línea hasta completar las 5 filas
            while ((linea = lineas.get(fila)) != null && fila < 5) {
                
                // Separamos los caracteres de la línea (asumiendo que en el txt usas comas, ej: "P,CV,M1,CV...")
                String[] simbolos = linea.split(",");

                for (int col = 0; col < 10 && col < simbolos.length; col++) {
                    // Limpiamos los espacios en blanco del string leído
                    String simboloLeido = simbolos[col].trim();
                    Coordenada coordenada = new Coordenada(fila, col);

                    // --- FACTORY METHOD ---
                    // Buscamos la fábrica correspondiente en el diccionario
                    var fabricaNoMovible = this.creadoresNoMovibles.get(simboloLeido);

                    if (fabricaNoMovible != null) {
                        // Le pedimos a la fábrica que instancie el objeto y lo guardamos en la coordenada
                        tablero.colocarNoMovible((EntidadNoMovible) fabricaNoMovible.crear(), coordenada);
                    } else {
                        // Manejo de errores en caso de leer un símbolo inválido o vacío en el txt
                        // Por defecto, si hay error, podemos poner una casilla vacía para que no explote el juego
                        tablero.colocarNoMovible((EntidadNoMovible) this.creadoresNoMovibles.get("CV").crear(), coordenada);
                    }
                }
                fila++; 
            }
    }

    private void construirMovibles(List<String> lineas, Tablero tablero) {
        // Similar a construirNoMovibles, pero para la capa de entidades movibles.
            String linea;
            int fila = 0;
            // Leemos línea por línea hasta completar las 5 filas
            while ((linea = lineas.get(fila)) != null && fila < 5) {
                
                // Separamos los caracteres de la línea (asumiendo que en el txt usas comas, ej: "P,CV,M1,CV...")
                String[] simbolos = linea.split(",");

                for (int col = 0; col < 10 && col < simbolos.length; col++) {
                    // Limpiamos los espacios en blanco del string leído
                    String simboloLeido = simbolos[col].trim();
                    Coordenada coordenada = new Coordenada(fila, col);

                    // --- FACTORY METHOD ---
                    // Buscamos la fábrica correspondiente en el diccionario
                    var fabricaMovible = this.creadoresMovibles.get(simboloLeido);
                    if (fabricaMovible != null) {
                        tablero.colocarMovible((EntidadMovible) fabricaMovible.crear(), coordenada);
                    } else {
                        tablero.colocarMovible(null, coordenada); // No hay entidad movible en esta posición
                    }
                }
                fila++; 
            }        
    }
}