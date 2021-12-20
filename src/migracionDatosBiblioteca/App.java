package migracionDatosBiblioteca;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {

	// Metodo : main
	// Parametros : String[] 
	// Funcionalidad : Consultas en bucle para ejecutar funcionalidades
	// Return : void
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException
	{
		Scanner sc = new Scanner( System.in );
		
		Biblioteca biblioteca = new Biblioteca();
		GestionBD gestorBD = new GestionBD();
		
		boolean continuidadAPP = true;
		
		while( continuidadAPP )
		{
			System.out.println( "\nQue desea hacer? : " );
			System.out.println( "Consultar base de datos (consulta manual) --> 1" );
			System.out.println( "Mostrar un libro --> 2" );
			System.out.println( "Anyadir libro a biblioteca --> 3" );
			System.out.println( "Modificar atributos de un libro --> 4" );
			System.out.println( "Borrar un libro --> 5" );
			System.out.println( "Mostrar todos los libros --> 6" );
			System.out.println( "Cerrar APP --> 7" );
			System.out.print( "> " );
			String respuesta = sc.next();
			System.out.print( "\n" );
			
			switch( respuesta ) 
			{
				case "1" :
					String consulta = gestorBD.GenerarConsulta( sc );
					
					if(consulta.equals("No hay consulta"))
					{
						break;
					}
					
					gestorBD.ConsultarBD( consulta );
					break;
					
				case "2" :					
					try 
					{
						System.out.println( "\nIntroduce el ID del libro a mostrar : " );
						int id = sc.nextInt();
						gestorBD.MostrarInformacionLibro( id );
					}
					catch( Exception e )
					{
						e.printStackTrace();
					}
					
					break;
					
				case "3" :					
					try {
						System.out.println( "\nIntroduce el titulo del libro : " );
						String titulo = sc.next();
						
						System.out.println( "\nIntroduce el autor del libro : " );
						String autor = sc.next();
						
						System.out.println( "\nIntroduce el anyo de nacimiento del autor del libro : " );
						String anyoNacimiento = sc.next();
						
						System.out.println( "\nIntroduce el anyo de publicacion del libro : " );
						int anyoPublicacion = sc.nextInt();
						
						System.out.println( "\nIntroduce la editorial del libro : " );
						String editorial = sc.next();
						
						System.out.println( "\nIntroduce el numero de paginas del libro : " );
						int numPaginas = sc.nextInt();
						
						gestorBD.AnyadirLibroBD( titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
					
				case "4" :
					try {
						System.out.println( "\nIntroduce el id del libro a modificar : " );
						int id = sc.nextInt();
						
						gestorBD.MostrarInformacionLibro( id );
						
						System.out.println( "\nIntroduce el nuevo titulo del libro : " );
						String titulo = sc.next();
						
						System.out.println( "\nIntroduce el nuevo autor del libro : " );
						String autor = sc.next();
						
						System.out.println( "\nIntroduce el nuevo anyo de nacimiento del autor del libro : " );
						String anyoNacimiento = sc.next();
						
						System.out.println( "\nIntroduce el nuevo anyo de publicacion del libro : " );
						int anyoPublicacion = sc.nextInt();
						
						System.out.println( "\nIntroduce la nueva editorial del libro : " );
						String editorial = sc.next();
						
						System.out.println( "\nIntroduce el nuevo numero de paginas del libro : " );
						int numPaginas = sc.nextInt();
						
						gestorBD.ModificarLibroBD( titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, id );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;			
				
				case "5" :
					try 
					{
						System.out.println( "\nIntroduce el id del libro a borrar : " );
						int id  = sc.nextInt();
						
						gestorBD.BorrarLibroBD( id );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
				
				case "6" :
					try 
					{
						gestorBD.MostrarInformacionTodosLosLibros();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					break;
					
				case "7" :
					System.out.println( ">> Nos vemos pronto ;)" );
					continuidadAPP = false;
					break;						

				default :
					System.out.println( ">> Valor introducido no valido." );
					break;				
			}
		}
	}
}
