package migracionDatosBiblioteca;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
	// Metodo : ConsultarBD
	// Parametros : String
	// Funcionalidad : Consultar una base de datos pasandole un String con la consulta
	// Return : void
	public void ConsultarBD( String consulta ) throws ClassNotFoundException
	{
		Class.forName( "com.mysql.cj.jdbc.Driver" );
		
		try 
		{
			System.out.println( ">> Conectando a la base de datos. " );
			Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/biblioteca","root","");
			System.out.println( ">> Conexion correcta.\n" );
			
			Statement stmt = con.createStatement();
			
			//EJECUTA LA CONSULTA Y DEVUELVE TRUE SI LA CONSULTA ES DE TIPO SELECT
			//boolean tipoConsulta = stmt.execute( consulta );
			
			String tipoConsulta[] = consulta.split( " " );
			
			if( tipoConsulta[0].equals( "SELECT" ) || tipoConsulta[0].equals( "select" ) )
			{
				// SELECT
				ResultSet rs = stmt.executeQuery( consulta );
				MostrarConsulta( rs );
				rs.close();
			}
			else
			{
				// INSERT UPDATE DELETE CREATE ALTER
				int filasAfectadas = stmt.executeUpdate( consulta );
				System.out.println( ">> " + filasAfectadas + " filas afectadas." );
			}
			
			System.out.println( ">> Consulta realizada correctamente." );
			
			con.close();
		}
		catch( SQLException e )
		{
			System.out.println( ">> Error en la conexion. " );
			e.printStackTrace();			
		}
	}
	
	
	
	public void MostrarInformacionLibro(int id) throws ClassNotFoundException
	{
		String consultaInfoLibrosConID = "SELECT * FROM libro WHERE identificador = " + id;
		
		try 
		{
			ConsultarBD( consultaInfoLibrosConID );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	
	public void AnyadirLibroBD( String titulo, String autor, String anyoNacimiento, int anyoPublicacion, String editorial, int numPaginas ) throws ClassNotFoundException
	{
		String consultaAnyadirLibro = "INSERT INTO libro VALUES ('" + titulo + "','" + autor + "','" + anyoNacimiento + "'," + anyoPublicacion + ",'" + editorial + "'," + numPaginas + ", NULL);";
		
		try 
		{
			ConsultarBD( consultaAnyadirLibro );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void BorrarLibroBD( int id )
	{
		String consultaBorrarLibroConID = "DELETE from libro WHERE identificador = " + id;
		
		try 
		{
			ConsultarBD( consultaBorrarLibroConID );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void ModificarLibroBD( String titulo, String autor, String anyoNacimiento, int anyoPublicacion, String editorial, int numPaginas, int id )
	{
		String consultaModificarLibroConID = "UPDATE libro SET titulo = '" + titulo + "'," + "autor = '" + autor + "', anyo_nacimiento = '" + anyoNacimiento + "', anyo_publicacion = " + anyoPublicacion + ", editorial = '" + editorial + "', numero_paginas = " + numPaginas + " WHERE identificador = " + id;
		
		try 
		{
			ConsultarBD( consultaModificarLibroConID );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void MostrarInformacionTodosLosLibros()
	{
		String consultaMostrarInformacionTodosLosLibros = "SELECT * FROM libro";
		
		try 
		{
			ConsultarBD( consultaMostrarInformacionTodosLosLibros );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	//private void
	
	
	// Metodo : ConsultasRequeridas()
	// Parametros : 
	// Funcionalidad : Realiza las consultas propuestas en el enunciado del ejercicio, ademas muestra los resultados con formato
	// Return : void
	
	// ESTE METODO LO HE HECHO ASI PORQUE QUERIA PROBAR " System.out.format() "
	public void ConsultasRequeridas() throws ClassNotFoundException
	{
		String infoGeneralLibros = "SELECT titulo, autor, anyo_publicacion FROM libro GROUP BY autor";
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
	
	

	// Metodo : GenerarConsulta()
	// Parametros : Scanner
	// Funcionalidad : Anyade las palabras de una consulta individualmente a un String con la consulta completa y pregunta al usuario si es correcta
	// Return : String
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
					//Evita que si la primera palabra de la consulta ya es FIN el programa explote
					if( consulta.length() > 1 ) 
					{
						consulta = consulta.substring(0, consulta.length() -1 );
					}
					else
					{
						consulta = "No hay consulta";
					}
					
				}
			}		
			
			if(consulta.equals("No hay consulta")) {
				System.out.println( "---\n");
				break;
			}
			
			System.out.println( "---\n");
			System.out.println( consulta );
			System.out.println( "\n---");
			System.out.println( "> Es correcta la consulta? : S/N" );
			comprobarConsulta = sc.next();
			
		}while( comprobarConsulta.equals( "N" ) || comprobarConsulta.equals( "n" ) );

		return consulta;
	}
	
	
	
	// Metodo : MostrarConsulta()
	// Parametros : ResultSet
	// Funcionalidad : Muestra los resultados de la consulta realizada que pasamos por parametro
	// Return : void
	public void MostrarConsulta( ResultSet rs ) throws ClassNotFoundException, SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		
		while( rs.next() )
		{
			String resultadoConsulta = "";
			
			for( int i = 1; i <= rsmd.getColumnCount(); i++ )
			{	
				resultadoConsulta += rs.getString( i ) + "\t";
			}
			
			System.out.println( resultadoConsulta );
		}
	}
}
