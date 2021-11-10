package migracionDatosBiblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;



public class GestionBD 
{
	public ArrayList<Libro> AnalizarCSV( String rutaFichero )
	{
		ArrayList<Libro> librosEnCSV = new ArrayList<Libro>();

		try
		{
			// /home/jordi/proyectosJavaEclipse/jordi_estelles_navarro_AE4_ADD/datos/AE04_T1_4_JDBC_Datos.csv			
			FileReader fr = new FileReader( "rutaFichero" );
			
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
	
	
	
	public void ConsultarBD( String consulta ) throws ClassNotFoundException
	{
		Class.forName( "com.mysql.cj.jdbc.Driver" );
		
		try 
		{
			System.out.println( ">> Conectando a la base de datos. " );
			Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/biblioteca","root","");
			System.out.println( ">> Conexion correcta. " );
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery( consulta );
			ResultSetMetaData rsmd = rs.getMetaData();
			
			System.out.println( rsmd.getColumnCount() );
			
			System.out.println( ">> Consulta realizada correctamente." );
			
			rs.close();
			con.close();
		}
		catch( SQLException e )
		{
			System.out.println( ">> Error en la conexion. " );
			e.printStackTrace();			
		}
	}
	
	
	
	public void ConsultasRequeridas() throws ClassNotFoundException
	{
		String infoGeneralLibros = "SELECT titulo, autor, anyo_publicacion FROM libro GROUP BY autor"; //MODIFICAR
		String editorialesSiglo21 = "SELECT anyo_publicacion, editorial, COUNT(editorial) AS libros_siglo21 FROM libro WHERE anyo_publicacion>2000 GROUP BY editorial";
		
		try 
		{
			System.out.println( ">> Conectando a la base de datos. " );
			Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/biblioteca","root","");
			System.out.println( ">> Conexion correcta. " );
			
			Statement stmt = con.createStatement();
			
			
			// PRIMERA CONSULTA
			
			System.out.println( "Libros (título, autor y año de publicación) de los autores nacidos antes de 1950. : " );
			
			ResultSet rs = stmt.executeQuery( infoGeneralLibros );
			
			System.out.format( "%30s%30s%20s%20s", "Titulo", "Autor", "Anyo Publicacion", "\n" );
			System.out.format( "%30s%30s%20s%20s", "------", "-----", "----------------", "\n" );
			
			while( rs.next() )
			{
				System.out.format( "%30s%30s%20s%20s", rs.getString(1), rs.getString(2), rs.getInt(3), "\n" );
			}			
			
			System.out.println( ">> Primera consulta realizada correctamente.\n" );
			
			
			// SEGUNDA CONSULTA			
			
			System.out.println( "Editoriales que publicaron libros despues del 2000 : " );
			
			rs = stmt.executeQuery( editorialesSiglo21 );
			
			System.out.format( "%30s%30s%20s%20s", "Anyo Publicacion", "Editorial", "libros_siglo21", "\n" );
			System.out.format( "%30s%30s%20s%20s", "----------------", "---------", "--------------", "\n" );
			
			while( rs.next() )
			{
				System.out.format( "%30s%30s%20s%20s", rs.getString(1), rs.getString(2), rs.getInt(3), "\n" );
			}			
			
			System.out.println( ">> Segunda consulta realizada correctamente." );
			
			rs.close();
			con.close();
		}
		catch( SQLException e )
		{
			System.out.println( ">> Error en la conexion. " );
			e.printStackTrace();	
		}
	}
	
	

	public String GenerarConsulta( Scanner sc )
	{
		String comprobarConsulta;
		String consulta;
		
		do
		{
			consulta = "";
			
			System.out.println( "Introduce la consulta palabra por palabra incluyendo signos : palabra + tecla ENTER" );
			System.out.println( "Cuando termine la sentencia escribir : FIN");
			System.out.println( "---");

			String comodinConsulta = "";
			
			while( !comodinConsulta.equals( "FIN" ) && !comodinConsulta.equals( "fin" ) )
			{
				comodinConsulta = sc.next();
				
				if(  !comodinConsulta.equals( "FIN" ) && !comodinConsulta.equals( "fin" )  ) 
				{
					consulta += comodinConsulta;
					consulta += " ";
				}
				else
				{
					consulta = consulta.substring(0, consulta.length() -1 );
				}
			}		
			
			System.out.println( "---");
			System.out.println( consulta );
			System.out.println( "> Es correcta la consulta? : S/N" );
			comprobarConsulta = sc.next();
			
		}while( comprobarConsulta.equals( "N" ) || comprobarConsulta.equals( "n" ) );
		
		return consulta;
	}
}