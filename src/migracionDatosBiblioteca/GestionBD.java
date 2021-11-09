package migracionDatosBiblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionBD 
{
	public ArrayList<Libro> AnalizarCSV()
	{
		ArrayList<Libro> librosEnCSV = new ArrayList<Libro>();

		try
		{
			FileReader fr = new FileReader( "/home/jordi/proyectosJavaEclipse/jordi_estelles_navarro_AE4_ADD/datos/AE04_T1_4_JDBC_Datos.csv" );

			BufferedReader br = new BufferedReader( fr );
			String linea = br.readLine();
			
			int evitarTitulos = 0;
			
			while( linea != null )
			{
				evitarTitulos ++; // Evita que genere un libro con los titulos del atributo ( 1ra linea )
				
				if( evitarTitulos > 1 )
				{				
					String[] datosLibro = linea.split( ";" );
					
					Libro nuevoLibro = new Libro( datosLibro[0], datosLibro[1], datosLibro[2], Integer.valueOf( datosLibro[3]), datosLibro[4], Integer.valueOf(datosLibro[5]) );
					librosEnCSV.add( nuevoLibro );
				}
				
				linea = br.readLine();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();			
		}
		
		return librosEnCSV;
	}
	
	public void MigracionABDMySQL( ArrayList<Libro> almacenLibros ) throws ClassNotFoundException
	{
		// try catch
		// leer la lista de libros y parsear datos de cada uno de los libros
		
		Class.forName( "com.mysql.cj.jdbc.Driver" ); // Click derecho en proyecto > build path > configure build path > libraries > module path > anyadir libreria externa > archivo .jar
		
		try 
		{
			System.out.println( ">> Conectando a la base de datos. " );
			Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/biblioteca","root",""); // "", "usuario". "contrasenya"
			System.out.println( ">> Conexion correcta. " );
			
			PreparedStatement psInsertar = con.prepareStatement( "INSERT INTO libro (titulo, autor, anyo_nacimiento, anyo_publicacion, editorial, numero_paginas, identificador) VALUES (?, ?, ?, ?, ?, ?, NULL)" );
			int elementosImportados = 0;
			
			for( Libro libro : almacenLibros ) 
			{
				psInsertar.setString( 1, libro.getTitulo() );
				psInsertar.setString( 2, libro.getAutor() );
				psInsertar.setString( 3, libro.getAnyoNacimiento() );
				psInsertar.setInt( 4, libro.getAnyoPublicacion() );
				psInsertar.setString( 5, libro.getEditorial() );
				psInsertar.setInt( 6, libro.getNumeroPaginas() );
				psInsertar.executeUpdate();
					
				elementosImportados ++;
			}
			
			System.out.println( ">> Insercion correcta. Numero de elementos importados a la BD : " + elementosImportados );
			
			psInsertar.close();
			con.close();
			
		}
		catch( SQLException e )
		{
			System.out.println( ">> Error en la conexion. " );
			e.printStackTrace();			
		}
	}
	
	public void ConsultarBD( Scanner sc )
	{
		// try catch
		System.out.println( ">> Introduce la consulta a realizar : " );
		System.out.print( "> " );
		sc.next();
	}
}
