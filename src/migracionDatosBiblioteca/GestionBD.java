package migracionDatosBiblioteca;

import java.util.ArrayList;
import java.util.Scanner;

public class GestionBD 
{
	public ArrayList<Libro> AnalizarCSV()
	{
		ArrayList<Libro> librosEnCSV = new ArrayList<Libro>();
		
		// extraer datos de csv
		// transformar datos a libros
		// guardar libros en arraylist librosEnCSV	
		
		return librosEnCSV;
	}
	
	public void MigracionABDMySQL( ArrayList<Libro> almacenLibros )
	{
		// try catch
		// leer la lista de libros y parsear datos de cada uno de los libros
	}
	
	public void ConsultarBD( Scanner sc )
	{
		// try catch
		System.out.println( ">> Introduce la consulta a realizar : " );
		System.out.print( "> " );
		sc.next();
	}
}
